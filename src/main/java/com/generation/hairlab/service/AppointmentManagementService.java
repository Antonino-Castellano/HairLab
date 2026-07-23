package com.generation.hairlab.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.hairlab.dto.AppointmentDetailDto;
import com.generation.hairlab.dto.AppointmentDto;
import com.generation.hairlab.dto.AppointmentItemDto;
import com.generation.hairlab.dto.AppointmentItemRequestDto;
import com.generation.hairlab.dto.AppointmentManagementRequestDto;
import com.generation.hairlab.enums.AppointmentStatus;

import lombok.RequiredArgsConstructor;

/**
 * Orchestratore atomico:
 *
 * Appointment + AppointmentItem.
 *
 * Lo stato dell'appuntamento non viene più
 * ricevuto dalla request di modifica.
 *
 * È gestito esclusivamente
 * da AppointmentWorkflowService.
 */
@Service
@RequiredArgsConstructor
public class AppointmentManagementService {

    private final AppointmentService
        appointmentService;

    private final AppointmentItemService
        appointmentItemService;

    @Transactional(readOnly = true)
    public AppointmentDetailDto findById(
            Integer id)
            throws ServiceException {

        AppointmentDetailDto detail =
            new AppointmentDetailDto();

        detail.setAppointment(
            appointmentService.findById(
                id
            )
        );

        detail.setItems(
            appointmentItemService
                .findByAppointment(
                    id
                )
        );

        return detail;
    }

    /**
     * Nuovo appuntamento:
     *
     * nasce sempre BOOKED.
     */
    @Transactional(
        rollbackFor = ServiceException.class
    )
    public AppointmentDetailDto insert(
            AppointmentManagementRequestDto request)
            throws ServiceException {

        AppointmentDto appointmentDto =
            new AppointmentDto();

        appointmentDto.setCustomerId(
            request.getCustomerId()
        );

        appointmentDto.setStartDateTime(
            request.getStartDateTime()
        );

        appointmentDto.setStatus(
            AppointmentStatus.BOOKED
        );

        appointmentDto.setNotes(
            normalizeNullable(
                request.getNotes()
            )
        );

        AppointmentDto savedAppointment =
            appointmentService.insert(
                appointmentDto
            );

        List<AppointmentItemDto> savedItems =
            createItems(
                savedAppointment.getId(),
                request.getStartDateTime(),
                request.getItems()
            );

        return buildDetail(
            savedAppointment,
            savedItems
        );
    }

    /**
     * Modifica solamente appuntamenti:
     *
     * BOOKED
     * CONFIRMED
     *
     * Un appuntamento IN_PROGRESS
     * non è più modificabile strutturalmente.
     */
    @Transactional(
        rollbackFor = ServiceException.class
    )
    public AppointmentDetailDto update(
            Integer id,
            AppointmentManagementRequestDto request)
            throws ServiceException {

        AppointmentDto currentAppointment =
            appointmentService.findById(
                id
            );

        if (
            currentAppointment.getStatus() !=
                AppointmentStatus.BOOKED &&
            currentAppointment.getStatus() !=
                AppointmentStatus.CONFIRMED
        ) {

            throw new ServiceException(
                "Solo gli appuntamenti prenotati o confermati possono essere modificati",
                HttpStatus.CONFLICT
            );
        }

        List<AppointmentItemDto> existingItems =
            appointmentItemService
                .findByAppointment(
                    id
                );

        boolean hasCompletedItem =
            existingItems
                .stream()
                .anyMatch(
                    item ->
                        item.getCompletedAt() !=
                            null
                );

        if (
            hasCompletedItem
        ) {

            throw new ServiceException(
                "L'appuntamento contiene servizi già completati e non può essere modificato",
                HttpStatus.CONFLICT
            );
        }

        /*
         * Ricostruiamo gli item all'interno
         * della stessa transazione.
         */
        for (
            AppointmentItemDto existingItem :
            existingItems
        ) {

            appointmentItemService.delete(
                existingItem.getId()
            );
        }

        AppointmentDto appointmentDto =
            new AppointmentDto();

        appointmentDto.setCustomerId(
            request.getCustomerId()
        );

        appointmentDto.setStartDateTime(
            request.getStartDateTime()
        );

        /*
         * AppointmentService.update()
         * preserva comunque lo stato,
         * ma valorizziamo il DTO
         * per chiarezza.
         */
        appointmentDto.setStatus(
            currentAppointment.getStatus()
        );

        appointmentDto.setNotes(
            normalizeNullable(
                request.getNotes()
            )
        );

        AppointmentDto savedAppointment =
            appointmentService.update(
                id,
                appointmentDto
            );

        List<AppointmentItemDto> savedItems =
            createItems(
                id,
                request.getStartDateTime(),
                request.getItems()
            );

        return buildDetail(
            savedAppointment,
            savedItems
        );
    }

    private List<AppointmentItemDto> createItems(
            Integer appointmentId,
            LocalDateTime appointmentStart,
            List<AppointmentItemRequestDto> requests)
            throws ServiceException {

        if (
            requests == null ||
            requests.isEmpty()
        ) {

            throw new ServiceException(
                "Inserisci almeno un servizio",
                HttpStatus.BAD_REQUEST
            );
        }

        List<AppointmentItemDto> result =
            new ArrayList<>();

        LocalDateTime cursor =
            appointmentStart;

        for (
            AppointmentItemRequestDto request :
            requests
        ) {

            AppointmentItemDto itemDto =
                new AppointmentItemDto();

            itemDto.setAppointmentId(
                appointmentId
            );

            itemDto.setSalonProductId(
                request.getSalonProductId()
            );

            itemDto.setEmployeeId(
                request.getEmployeeId()
            );

            itemDto.setScheduledTime(
                cursor
            );

            itemDto.setDuration(
                request.getDuration()
            );

            itemDto.setAgreedPrice(
                request.getAgreedPrice()
            );

            itemDto.setResultNotes(
                normalizeNullable(
                    request.getResultNotes()
                )
            );

            AppointmentItemDto saved =
                appointmentItemService.insert(
                    itemDto
                );

            result.add(
                saved
            );

            cursor =
                cursor.plusMinutes(
                    request.getDuration()
                );
        }

        return result;
    }

    private AppointmentDetailDto buildDetail(
            AppointmentDto appointment,
            List<AppointmentItemDto> items) {

        AppointmentDetailDto detail =
            new AppointmentDetailDto();

        detail.setAppointment(
            appointment
        );

        detail.setItems(
            items
        );

        return detail;
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
