package com.generation.hairlab.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.hairlab.dto.AppointmentSlotItemRequestDto;
import com.generation.hairlab.dto.AppointmentSlotSearchRequestDto;
import com.generation.hairlab.dto.AppointmentSlotSuggestionDto;
import com.generation.hairlab.dto.EmployeeAvailabilityDto;

import lombok.RequiredArgsConstructor;

/**
 * Cerca automaticamente i primi slot
 * disponibili per l'intero appuntamento.
 *
 * La sequenza dei servizi è considerata
 * nello stesso ordine del form.
 *
 * Esempio:
 *
 * 09:00 -> 10:30 Anna
 * 10:30 -> 11:15 Luca
 *
 * Lo slot 09:00 è valido solo se
 * entrambi gli operatori sono liberi
 * nei rispettivi intervalli.
 */
@Service
@RequiredArgsConstructor
public class AppointmentSlotSearchService {

    private final AppointmentAvailabilityService
        appointmentAvailabilityService;

    /**
     * Trova i primi slot liberi
     * nella finestra richiesta.
     */
    @Transactional(readOnly = true)
    public List<AppointmentSlotSuggestionDto>
        findAvailableSlots(
            AppointmentSlotSearchRequestDto request
        )
        throws ServiceException {

        validateRequest(
            request
        );

        int stepMinutes =
            request.getStepMinutes() != null
                ? request.getStepMinutes()
                : 15;

        int maxResults =
            request.getMaxResults() != null
                ? request.getMaxResults()
                : 8;

        int totalDuration =
            request.getItems()
                .stream()
                .mapToInt(
                    AppointmentSlotItemRequestDto::getDuration
                )
                .sum();

        LocalDateTime cursor =
            request.getDate()
                .atTime(
                    request.getWindowStart()
                );

        LocalDateTime windowEnd =
            request.getDate()
                .atTime(
                    request.getWindowEnd()
                );

        List<AppointmentSlotSuggestionDto> result =
            new ArrayList<>();

        /*
         * Continuiamo finché l'intera durata
         * dell'appuntamento entra nella finestra.
         */
        while (
            !cursor
                .plusMinutes(
                    totalDuration
                )
                .isAfter(
                    windowEnd
                )
            &&
            result.size() <
                maxResults
        ) {

            if (
                isWholeScheduleAvailable(
                    cursor,
                    request
                )
            ) {

                AppointmentSlotSuggestionDto suggestion =
                    new AppointmentSlotSuggestionDto();

                suggestion.setStartDateTime(
                    cursor
                );

                suggestion.setEndDateTime(
                    cursor.plusMinutes(
                        totalDuration
                    )
                );

                suggestion.setTotalDuration(
                    totalDuration
                );

                result.add(
                    suggestion
                );
            }

            cursor =
                cursor.plusMinutes(
                    stepMinutes
                );
        }

        return result;
    }

    /**
     * Verifica tutta la sequenza,
     * spostando il cursore dopo ogni servizio.
     */
    private boolean isWholeScheduleAvailable(
            LocalDateTime appointmentStart,
            AppointmentSlotSearchRequestDto request)
            throws ServiceException {

        LocalDateTime itemStart =
            appointmentStart;

        for (
            AppointmentSlotItemRequestDto item :
            request.getItems()
        ) {

            EmployeeAvailabilityDto availability =
                appointmentAvailabilityService
                    .findEmployeeAvailabilityForEmployee(
                        item.getEmployeeId(),
                        itemStart,
                        item.getDuration(),
                        request.getExcludeAppointmentId()
                    );

            if (
                !availability.isAvailable()
            ) {

                return false;
            }

            itemStart =
                itemStart.plusMinutes(
                    item.getDuration()
                );
        }

        return true;
    }

    private void validateRequest(
            AppointmentSlotSearchRequestDto request)
            throws ServiceException {

        if (
            request == null
        ) {

            throw new ServiceException(
                "I dati della ricerca slot sono obbligatori",
                HttpStatus.BAD_REQUEST
            );
        }

        if (
            request.getDate() == null ||
            request.getWindowStart() == null ||
            request.getWindowEnd() == null
        ) {

            throw new ServiceException(
                "Data e finestra oraria sono obbligatorie",
                HttpStatus.BAD_REQUEST
            );
        }

        if (
            !request.getWindowStart()
                .isBefore(
                    request.getWindowEnd()
                )
        ) {

            throw new ServiceException(
                "L'inizio della finestra deve precedere la fine",
                HttpStatus.BAD_REQUEST
            );
        }

        if (
            request.getItems() == null ||
            request.getItems().isEmpty()
        ) {

            throw new ServiceException(
                "Inserisci almeno un servizio da pianificare",
                HttpStatus.BAD_REQUEST
            );
        }

        int totalDuration =
            request.getItems()
                .stream()
                .mapToInt(
                    AppointmentSlotItemRequestDto::getDuration
                )
                .sum();

        long availableMinutes =
            java.time.Duration
                .between(
                    request.getWindowStart(),
                    request.getWindowEnd()
                )
                .toMinutes();

        if (
            totalDuration >
            availableMinutes
        ) {

            throw new ServiceException(
                "La durata totale dell'appuntamento supera la finestra di ricerca",
                HttpStatus.BAD_REQUEST
            );
        }
    }
}
