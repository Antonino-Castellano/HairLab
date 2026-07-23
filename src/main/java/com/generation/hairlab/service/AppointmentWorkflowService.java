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
import com.generation.hairlab.model.AppointmentItem;
import com.generation.hairlab.repository.AppointmentItemRepository;
import com.generation.hairlab.repository.AppointmentRepository;

import lombok.RequiredArgsConstructor;

/**
 * Gestisce il ciclo di vita dell'appuntamento.
 *
 * Flusso principale:
 *
 * BOOKED
 *   |
 *   +-> CONFIRMED
 *   |      |
 *   |      +-> IN_PROGRESS
 *   |
 *   +------------> IN_PROGRESS
 *
 * IN_PROGRESS
 *   |
 *   +-> COMPLETED
 *
 * BOOKED / CONFIRMED
 *   |
 *   +-> NO_SHOW
 *
 * BOOKED / CONFIRMED
 *   |
 *   +-> CANCELLED
 *      tramite DELETE già esistente.
 *
 * Gli stati terminali sono:
 *
 * COMPLETED
 * CANCELLED
 * NO_SHOW
 */
@Service
@RequiredArgsConstructor
public class AppointmentWorkflowService {

    private final AppointmentRepository
        appointmentRepository;

    private final AppointmentItemRepository
        appointmentItemRepository;

    private final AppointmentMapper
        appointmentMapper;

    /**
     * BOOKED -> CONFIRMED
     */
    @Transactional
    public AppointmentDto confirm(
            Integer id)
            throws ServiceException {

        Appointment appointment =
            getAppointment(
                id
            );

        if (
            appointment.getStatus() ==
                AppointmentStatus.CONFIRMED
        ) {

            return appointmentMapper.toDto(
                appointment
            );
        }

        requireStatus(
            appointment,
            AppointmentStatus.BOOKED,
            "Solo un appuntamento prenotato può essere confermato"
        );

        return changeStatus(
            appointment,
            AppointmentStatus.CONFIRMED
        );
    }

    /**
     * BOOKED / CONFIRMED -> IN_PROGRESS
     */
    @Transactional
    public AppointmentDto start(
            Integer id)
            throws ServiceException {

        Appointment appointment =
            getAppointment(
                id
            );

        if (
            appointment.getStatus() ==
                AppointmentStatus.IN_PROGRESS
        ) {

            return appointmentMapper.toDto(
                appointment
            );
        }

        if (
            appointment.getStatus() !=
                AppointmentStatus.BOOKED &&
            appointment.getStatus() !=
                AppointmentStatus.CONFIRMED
        ) {

            throw new ServiceException(
                "Solo un appuntamento prenotato o confermato può essere avviato",
                HttpStatus.CONFLICT
            );
        }

        return changeStatus(
            appointment,
            AppointmentStatus.IN_PROGRESS
        );
    }

    /**
     * IN_PROGRESS -> COMPLETED
     *
     * Alla chiusura dell'appuntamento,
     * gli item ancora senza completedAt
     * vengono marcati come completati.
     */
    @Transactional
    public AppointmentDto complete(
            Integer id)
            throws ServiceException {

        Appointment appointment =
            getAppointment(
                id
            );

        if (
            appointment.getStatus() ==
                AppointmentStatus.COMPLETED
        ) {

            return appointmentMapper.toDto(
                appointment
            );
        }

        requireStatus(
            appointment,
            AppointmentStatus.IN_PROGRESS,
            "Solo un appuntamento in corso può essere completato"
        );

        List<AppointmentItem> items =
            appointmentItemRepository
                .findByAppointment_IdOrderByScheduledTimeAsc(
                    id
                );

        if (
            items.isEmpty()
        ) {

            throw new ServiceException(
                "L'appuntamento non contiene servizi e non può essere completato",
                HttpStatus.CONFLICT
            );
        }

        LocalDateTime now =
            LocalDateTime.now();

        for (
            AppointmentItem item :
            items
        ) {

            if (
                item.getCompletedAt() ==
                    null
            ) {

                item.setCompletedAt(
                    now
                );
            }
        }

        appointmentItemRepository.saveAll(
            items
        );

        return changeStatus(
            appointment,
            AppointmentStatus.COMPLETED
        );
    }

    /**
     * BOOKED / CONFIRMED -> NO_SHOW
     *
     * Non consentiamo di segnare come assente
     * un appuntamento futuro.
     */
    @Transactional
    public AppointmentDto markNoShow(
            Integer id)
            throws ServiceException {

        Appointment appointment =
            getAppointment(
                id
            );

        if (
            appointment.getStatus() ==
                AppointmentStatus.NO_SHOW
        ) {

            return appointmentMapper.toDto(
                appointment
            );
        }

        if (
            appointment.getStatus() !=
                AppointmentStatus.BOOKED &&
            appointment.getStatus() !=
                AppointmentStatus.CONFIRMED
        ) {

            throw new ServiceException(
                "Solo un appuntamento prenotato o confermato può essere segnato come non presentato",
                HttpStatus.CONFLICT
            );
        }

        if (
            appointment.getStartDateTime()
                .isAfter(
                    LocalDateTime.now()
                )
        ) {

            throw new ServiceException(
                "Non puoi segnare come non presentato un appuntamento che deve ancora iniziare",
                HttpStatus.CONFLICT
            );
        }

        return changeStatus(
            appointment,
            AppointmentStatus.NO_SHOW
        );
    }

    private AppointmentDto changeStatus(
            Appointment appointment,
            AppointmentStatus status) {

        appointment.setStatus(
            status
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

    private void requireStatus(
            Appointment appointment,
            AppointmentStatus required,
            String message)
            throws ServiceException {

        if (
            appointment.getStatus() !=
                required
        ) {

            throw new ServiceException(
                message,
                HttpStatus.CONFLICT
            );
        }
    }

    private Appointment getAppointment(
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
}
