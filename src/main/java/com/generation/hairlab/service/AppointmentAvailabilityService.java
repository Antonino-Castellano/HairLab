package com.generation.hairlab.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.hairlab.dto.AppointmentAvailabilityRequestDto;
import com.generation.hairlab.dto.EmployeeAvailabilityDto;
import com.generation.hairlab.enums.AppointmentStatus;
import com.generation.hairlab.model.Appointment;
import com.generation.hairlab.model.AppointmentItem;
import com.generation.hairlab.model.Employee;
import com.generation.hairlab.repository.AppointmentItemRepository;
import com.generation.hairlab.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

/**
 * Unico punto di verità per la disponibilità
 * degli operatori HairLab.
 *
 * Viene utilizzato sia:
 *
 * - dal frontend, per mostrare preventivamente
 *   chi è libero o occupato;
 *
 * - da AppointmentItemService, per impedire
 *   realmente il salvataggio di sovrapposizioni.
 *
 * Regola di overlap:
 *
 * nuovoInizio < fineEsistente
 * &&
 * nuovaFine > inizioEsistente
 *
 * Gli appuntamenti terminali:
 *
 * COMPLETED
 * CANCELLED
 * NO_SHOW
 *
 * non bloccano una nuova prenotazione.
 */
@Service
@RequiredArgsConstructor
public class AppointmentAvailabilityService {

    private final AppointmentItemRepository
        appointmentItemRepository;

    private final EmployeeRepository
        employeeRepository;

    /**
     * Restituisce lo stato di disponibilità
     * di tutti gli operatori attivi.
     */
    @Transactional(readOnly = true)
    public List<EmployeeAvailabilityDto>
        findEmployeeAvailability(
            AppointmentAvailabilityRequestDto request
        )
        throws ServiceException {

        validateInterval(
            request.getStartDateTime(),
            request.getDuration()
        );

        return employeeRepository
            .findByActiveTrue()
            .stream()
            .sorted(
                Comparator
                    .comparing(
                        Employee::getLastName,
                        String.CASE_INSENSITIVE_ORDER
                    )
                    .thenComparing(
                        Employee::getFirstName,
                        String.CASE_INSENSITIVE_ORDER
                    )
            )
            .map(
                employee ->
                    buildAvailability(
                        employee,
                        request.getStartDateTime(),
                        request.getDuration(),
                        request.getExcludeAppointmentId(),
                        null
                    )
            )
            .toList();
    }


    /**
     * Restituisce la disponibilità
     * di un singolo operatore.
     *
     * Questo metodo viene riutilizzato
     * dalla ricerca automatica degli slot.
     */
    @Transactional(readOnly = true)
    public EmployeeAvailabilityDto
        findEmployeeAvailabilityForEmployee(
            Integer employeeId,
            LocalDateTime startDateTime,
            int duration,
            Integer excludeAppointmentId
        )
        throws ServiceException {

        validateInterval(
            startDateTime,
            duration
        );

        Employee employee =
            employeeRepository
                .findById(
                    employeeId
                )
                .orElseThrow(
                    () ->
                        new ServiceException(
                            "Dipendente non trovato con id: " +
                                employeeId,
                            HttpStatus.NOT_FOUND
                        )
                );

        if (
            !employee.isActive()
        ) {

            throw new ServiceException(
                "Il dipendente selezionato non è attivo",
                HttpStatus.CONFLICT
            );
        }

        return buildAvailability(
            employee,
            startDateTime,
            duration,
            excludeAppointmentId,
            null
        );
    }

    /**
     * Validazione autoritativa usata durante
     * insert/update di AppointmentItem.
     *
     * Il controllo frontend migliora l'esperienza,
     * ma la regola di business resta sempre
     * garantita dal backend.
     */
    @Transactional(readOnly = true)
    public void validateEmployeeAvailability(
            Integer employeeId,
            LocalDateTime startDateTime,
            int duration,
            Integer currentItemId)
            throws ServiceException {

        validateInterval(
            startDateTime,
            duration
        );

        Employee employee =
            employeeRepository
                .findById(
                    employeeId
                )
                .orElseThrow(
                    () ->
                        new ServiceException(
                            "Dipendente non trovato con id: " +
                                employeeId,
                            HttpStatus.NOT_FOUND
                        )
                );

        EmployeeAvailabilityDto result =
            buildAvailability(
                employee,
                startDateTime,
                duration,
                null,
                currentItemId
            );

        if (
            !result.isAvailable()
        ) {

            throw new ServiceException(
                result.getMessage(),
                HttpStatus.CONFLICT
            );
        }
    }

    /**
     * Costruisce il risultato per un operatore.
     */
    private EmployeeAvailabilityDto buildAvailability(
            Employee employee,
            LocalDateTime requestedStart,
            int duration,
            Integer excludeAppointmentId,
            Integer currentItemId) {

        LocalDateTime requestedEnd =
            requestedStart.plusMinutes(
                duration
            );

        /*
         * Per il project work il numero di item
         * per singolo operatore è contenuto.
         *
         * Recuperiamo la sua agenda cronologica
         * e applichiamo in Java il confronto esatto
         * considerando la durata di ogni servizio.
         *
         * Questo evita query SQL dipendenti dal DB
         * per calcolare scheduledTime + duration.
         */
        List<AppointmentItem> items =
            appointmentItemRepository
                .findByEmployee_IdOrderByScheduledTimeAsc(
                    employee.getId()
                );

        for (
            AppointmentItem existing :
            items
        ) {

            if (
                currentItemId != null &&
                currentItemId.equals(
                    existing.getId()
                )
            ) {

                continue;
            }

            Appointment appointment =
                existing.getAppointment();

            if (
                appointment == null ||
                appointment.getId() == null
            ) {

                continue;
            }

            /*
             * In modifica ignoriamo tutti gli item
             * dell'appuntamento che stiamo editando.
             *
             * Evita falsi conflitti con sé stesso
             * durante il controllo preventivo.
             */
            if (
                excludeAppointmentId != null &&
                excludeAppointmentId.equals(
                    appointment.getId()
                )
            ) {

                continue;
            }

            if (
                !blocksAgenda(
                    appointment.getStatus()
                )
            ) {

                continue;
            }

            LocalDateTime existingStart =
                existing.getScheduledTime();

            if (
                existingStart == null
            ) {

                continue;
            }

            LocalDateTime existingEnd =
                existingStart.plusMinutes(
                    existing.getDuration()
                );

            boolean overlap =
                requestedStart.isBefore(
                    existingEnd
                )
                &&
                requestedEnd.isAfter(
                    existingStart
                );

            if (
                overlap
            ) {

                return unavailableResult(
                    employee,
                    appointment,
                    existingStart,
                    existingEnd
                );
            }
        }

        return availableResult(
            employee
        );
    }

    /**
     * Solo gli appuntamenti operativi
     * bloccano realmente l'agenda.
     */
    private boolean blocksAgenda(
            AppointmentStatus status) {

        return (
            status ==
                AppointmentStatus.BOOKED
            ||
            status ==
                AppointmentStatus.CONFIRMED
            ||
            status ==
                AppointmentStatus.IN_PROGRESS
        );
    }

    private EmployeeAvailabilityDto availableResult(
            Employee employee) {

        EmployeeAvailabilityDto dto =
            baseEmployee(
                employee
            );

        dto.setAvailable(
            true
        );

        dto.setMessage(
            "Operatore disponibile"
        );

        return dto;
    }

    private EmployeeAvailabilityDto unavailableResult(
            Employee employee,
            Appointment appointment,
            LocalDateTime conflictStart,
            LocalDateTime conflictEnd) {

        EmployeeAvailabilityDto dto =
            baseEmployee(
                employee
            );

        dto.setAvailable(
            false
        );

        dto.setConflictingAppointmentId(
            appointment.getId()
        );

        dto.setConflictStart(
            conflictStart
        );

        dto.setConflictEnd(
            conflictEnd
        );

        dto.setMessage(
            "Operatore occupato dalle " +
            formatTime(conflictStart) +
            " alle " +
            formatTime(conflictEnd) +
            " nell'appuntamento #" +
            appointment.getId()
        );

        return dto;
    }

    private EmployeeAvailabilityDto baseEmployee(
            Employee employee) {

        EmployeeAvailabilityDto dto =
            new EmployeeAvailabilityDto();

        dto.setEmployeeId(
            employee.getId()
        );

        dto.setFirstName(
            employee.getFirstName()
        );

        dto.setLastName(
            employee.getLastName()
        );

        return dto;
    }

    private void validateInterval(
            LocalDateTime startDateTime,
            int duration)
            throws ServiceException {

        if (
            startDateTime == null
        ) {

            throw new ServiceException(
                "Data e ora di inizio sono obbligatorie",
                HttpStatus.BAD_REQUEST
            );
        }

        if (
            duration <= 0
        ) {

            throw new ServiceException(
                "La durata deve essere maggiore di zero",
                HttpStatus.BAD_REQUEST
            );
        }
    }

    private String formatTime(
            LocalDateTime value) {

        return String.format(
            "%02d:%02d",
            value.getHour(),
            value.getMinute()
        );
    }
}
