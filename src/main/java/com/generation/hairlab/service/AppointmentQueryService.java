package com.generation.hairlab.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.hairlab.dto.AppointmentDto;
import com.generation.hairlab.mapper.AppointmentMapper;
import com.generation.hairlab.model.Appointment;
import com.generation.hairlab.model.AppointmentItem;
import com.generation.hairlab.repository.AppointmentItemRepository;
import com.generation.hairlab.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato alle query avanzate dell'agenda.
 *
 * Non modifica appuntamenti.
 *
 * Il primo filtro introdotto è:
 *
 * OPERATORE + INTERVALLO TEMPORALE
 *
 * Un Appointment non possiede direttamente Employee.
 *
 * La relazione è:
 *
 * Appointment
 *     ↓
 * AppointmentItem
 *     ↓
 * Employee
 *
 * Per questo recuperiamo gli AppointmentItem
 * assegnati all'operatore e ricostruiamo
 * la lista distinta degli Appointment.
 */
@Service
@RequiredArgsConstructor
public class AppointmentQueryService {

    private final AppointmentItemRepository
        appointmentItemRepository;

    private final EmployeeRepository
        employeeRepository;

    private final AppointmentMapper
        appointmentMapper;

    /**
     * Restituisce gli appuntamenti in cui
     * uno specifico operatore ha almeno
     * un servizio programmato nell'intervallo.
     */
    @Transactional(readOnly = true)
    public List<AppointmentDto>
        findByEmployeeBetween(
            Integer employeeId,
            LocalDateTime start,
            LocalDateTime end
        )
        throws ServiceException {

        validateEmployee(
            employeeId
        );

        validateInterval(
            start,
            end
        );

        List<AppointmentItem> items =
            appointmentItemRepository
                .findByEmployee_IdAndScheduledTimeBetweenOrderByScheduledTimeAsc(
                    employeeId,
                    start,
                    end
                );

        /*
         * Lo stesso appuntamento può contenere
         * più servizi assegnati allo stesso operatore.
         *
         * Usiamo LinkedHashMap per:
         *
         * - eliminare duplicati;
         * - preservare l'ordine cronologico.
         */
        Map<Integer, Appointment>
            appointmentsById =
                new LinkedHashMap<>();

        for (
            AppointmentItem item :
            items
        ) {

            Appointment appointment =
                item.getAppointment();

            if (
                appointment != null &&
                appointment.getId() != null
            ) {

                appointmentsById.putIfAbsent(
                    appointment.getId(),
                    appointment
                );
            }
        }

        return appointmentMapper.toDtoList(
            new ArrayList<>(
                appointmentsById.values()
            )
        );
    }

    /**
     * Verifica che l'operatore esista.
     *
     * Anche un dipendente disattivato
     * può essere usato come filtro storico.
     */
    private void validateEmployee(
            Integer employeeId)
            throws ServiceException {

        if (
            employeeId == null ||
            !employeeRepository.existsById(
                employeeId
            )
        ) {

            throw new ServiceException(
                "Dipendente non trovato con id: " +
                    employeeId,
                HttpStatus.NOT_FOUND
            );
        }
    }

    /**
     * Verifica l'intervallo temporale.
     */
    private void validateInterval(
            LocalDateTime start,
            LocalDateTime end)
            throws ServiceException {

        if (
            start == null ||
            end == null
        ) {

            throw new ServiceException(
                "Data iniziale e data finale sono obbligatorie",
                HttpStatus.BAD_REQUEST
            );
        }

        if (
            !start.isBefore(
                end
            )
        ) {

            throw new ServiceException(
                "La data iniziale deve precedere la data finale",
                HttpStatus.BAD_REQUEST
            );
        }
    }
}
