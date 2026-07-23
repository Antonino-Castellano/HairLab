package com.generation.hairlab.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.hairlab.dto.AppointmentDto;
import com.generation.hairlab.enums.AppointmentStatus;
import com.generation.hairlab.mapper.AppointmentMapper;
import com.generation.hairlab.model.Appointment;
import com.generation.hairlab.model.Customer;
import com.generation.hairlab.repository.AppointmentRepository;
import com.generation.hairlab.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service base degli appuntamenti.
 *
 * Da questo blocco lo stato dell'appuntamento
 * non viene più modificato tramite il normale update().
 *
 * Le transizioni di stato vengono gestite da:
 *
 * AppointmentWorkflowService
 *
 * Questo separa:
 *
 * - modifica dati appuntamento;
 * - avanzamento del ciclo di vita.
 */
@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository
        appointmentRepository;

    private final CustomerRepository
        customerRepository;

    private final AppointmentMapper
        appointmentMapper;

    @Transactional(readOnly = true)
    public List<AppointmentDto> findAll() {

        return appointmentMapper.toDtoList(
            appointmentRepository.findAll()
        );
    }

    @Transactional(readOnly = true)
    public AppointmentDto findById(
            Integer id)
            throws ServiceException {

        return appointmentMapper.toDto(
            getAppointmentById(id)
        );
    }

    @Transactional(readOnly = true)
    public List<AppointmentDto> findByCustomer(
            Integer customerId)
            throws ServiceException {

        ensureCustomerExists(
            customerId
        );

        return appointmentMapper.toDtoList(
            appointmentRepository
                .findByCustomer_IdOrderByStartDateTimeDesc(
                    customerId
                )
        );
    }

    @Transactional(readOnly = true)
    public List<AppointmentDto> findByStatus(
            AppointmentStatus status)
            throws ServiceException {

        if (status == null) {

            throw new ServiceException(
                "Lo stato dell'appuntamento è obbligatorio",
                HttpStatus.BAD_REQUEST
            );
        }

        return appointmentMapper.toDtoList(
            appointmentRepository.findByStatus(
                status
            )
        );
    }

    @Transactional(readOnly = true)
    public List<AppointmentDto> findBetween(
            LocalDateTime start,
            LocalDateTime end)
            throws ServiceException {

        validateInterval(
            start,
            end
        );

        return appointmentMapper.toDtoList(
            appointmentRepository
                .findByStartDateTimeBetweenOrderByStartDateTimeAsc(
                    start,
                    end
                )
        );
    }

    /**
     * Crea un appuntamento sempre nello stato BOOKED.
     *
     * Non accettiamo uno stato arbitrario dal client.
     */
    @Transactional
    public AppointmentDto insert(
            AppointmentDto dto)
            throws ServiceException {

        validateAppointmentDto(
            dto
        );

        Customer customer =
            getActiveCustomer(
                dto.getCustomerId()
            );

        Appointment appointment =
            appointmentMapper.toEntity(
                dto
            );

        appointment.setCustomer(
            customer
        );

        appointment.setStatus(
            AppointmentStatus.BOOKED
        );

        LocalDateTime now =
            LocalDateTime.now();

        appointment.setCreatedAt(
            now
        );

        appointment.setUpdatedAt(
            now
        );

        return appointmentMapper.toDto(
            appointmentRepository.save(
                appointment
            )
        );
    }

    /**
     * Modifica solamente:
     *
     * - cliente;
     * - data/ora;
     * - note.
     *
     * Lo stato corrente viene preservato.
     */
    @Transactional
    public AppointmentDto update(
            Integer id,
            AppointmentDto dto)
            throws ServiceException {

        validateAppointmentDto(
            dto
        );

        Appointment appointment =
            getAppointmentById(
                id
            );

        if (
            appointment.getStatus() !=
                AppointmentStatus.BOOKED &&
            appointment.getStatus() !=
                AppointmentStatus.CONFIRMED
        ) {

            throw new ServiceException(
                "Solo gli appuntamenti prenotati o confermati possono essere modificati",
                HttpStatus.CONFLICT
            );
        }

        Customer customer =
            getActiveCustomer(
                dto.getCustomerId()
            );

        appointment.setCustomer(
            customer
        );

        appointment.setStartDateTime(
            dto.getStartDateTime()
        );

        appointment.setNotes(
            normalizeNullable(
                dto.getNotes()
            )
        );

        appointment.setUpdatedAt(
            LocalDateTime.now()
        );

        return appointmentMapper.toDto(
            appointmentRepository.save(
                appointment
            )
        );
    }

    /**
     * DELETE è una cancellazione LOGICA.
     *
     * È consentita solamente quando
     * l'appuntamento non è ancora iniziato.
     *
     * BOOKED / CONFIRMED
     * -> CANCELLED
     */
    @Transactional
    public void delete(
            Integer id)
            throws ServiceException {

        Appointment appointment =
            getAppointmentById(
                id
            );

        if (
            appointment.getStatus() ==
                AppointmentStatus.CANCELLED
        ) {

            return;
        }

        if (
            appointment.getStatus() !=
                AppointmentStatus.BOOKED &&
            appointment.getStatus() !=
                AppointmentStatus.CONFIRMED
        ) {

            throw new ServiceException(
                "Solo un appuntamento prenotato o confermato può essere cancellato",
                HttpStatus.CONFLICT
            );
        }

        appointment.setStatus(
            AppointmentStatus.CANCELLED
        );

        appointment.setUpdatedAt(
            LocalDateTime.now()
        );

        appointmentRepository.save(
            appointment
        );
    }

    @Transactional(readOnly = true)
    public Appointment getAppointmentById(
            Integer id)
            throws ServiceException {

        return appointmentRepository
            .findById(
                id
            )
            .orElseThrow(
                () ->
                    new ServiceException(
                        "Appuntamento non trovato con id: " + id,
                        HttpStatus.NOT_FOUND
                    )
            );
    }

    private Customer getActiveCustomer(
            Integer customerId)
            throws ServiceException {

        Customer customer =
            customerRepository
                .findById(
                    customerId
                )
                .orElseThrow(
                    () ->
                        new ServiceException(
                            "Cliente non trovato con id: " + customerId,
                            HttpStatus.NOT_FOUND
                        )
                );

        if (
            !customer.isActive()
        ) {

            throw new ServiceException(
                "Non è possibile creare o modificare un appuntamento per un cliente disattivato",
                HttpStatus.CONFLICT
            );
        }

        return customer;
    }

    private void ensureCustomerExists(
            Integer customerId)
            throws ServiceException {

        if (
            customerId == null ||
            !customerRepository.existsById(
                customerId
            )
        ) {

            throw new ServiceException(
                "Cliente non trovato con id: " + customerId,
                HttpStatus.NOT_FOUND
            );
        }
    }

    private void validateAppointmentDto(
            AppointmentDto dto)
            throws ServiceException {

        if (dto == null) {

            throw new ServiceException(
                "I dati dell'appuntamento sono obbligatori",
                HttpStatus.BAD_REQUEST
            );
        }

        if (
            dto.getCustomerId() == null
        ) {

            throw new ServiceException(
                "L'id del cliente è obbligatorio",
                HttpStatus.BAD_REQUEST
            );
        }

        if (
            dto.getStartDateTime() == null
        ) {

            throw new ServiceException(
                "La data e ora dell'appuntamento sono obbligatorie",
                HttpStatus.BAD_REQUEST
            );
        }
    }

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

    private String normalizeNullable(
            String value) {

        if (
            value == null
        ) {

            return null;
        }

        String normalized =
            value.trim();

        return normalized.isEmpty()
            ? null
            : normalized;
    }
}
