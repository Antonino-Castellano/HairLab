package com.generation.hairlab.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.hairlab.dto.AppointmentSlotSearchRequestDto;
import com.generation.hairlab.dto.AppointmentSlotSuggestionDto;
import com.generation.hairlab.service.AppointmentSlotSearchService;
import com.generation.hairlab.service.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Endpoint per la ricerca automatica
 * degli orari liberi.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(
    "/hairlab/api/appointment-slot-search"
)
public class AppointmentSlotSearchController {

    private final AppointmentSlotSearchService
        appointmentSlotSearchService;

    /**
     * Cerca i primi slot validi
     * per tutti i servizi e operatori
     * selezionati nel form.
     */
    @PostMapping
    public ResponseEntity<
        List<AppointmentSlotSuggestionDto>
    > findAvailableSlots(
            @Valid
            @RequestBody
            AppointmentSlotSearchRequestDto request)
            throws ServiceException {

        return ResponseEntity.ok(
            appointmentSlotSearchService
                .findAvailableSlots(
                    request
                )
        );
    }
}
