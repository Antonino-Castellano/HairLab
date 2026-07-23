package com.generation.hairlab.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.hairlab.dto.AppointmentDetailDto;
import com.generation.hairlab.dto.AppointmentManagementRequestDto;
import com.generation.hairlab.service.AppointmentManagementService;
import com.generation.hairlab.service.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller aggregato per la gestione completa
 * di Appointment + AppointmentItem.
 *
 * Manteniamo separato questo endpoint
 * dal CRUD base già esistente.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(
    "/hairlab/api/appointment-management"
)
public class AppointmentManagementController {

    private final AppointmentManagementService
        appointmentManagementService;

    /**
     * Restituisce appuntamento + servizi.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDetailDto>
        findById(
            @PathVariable Integer id
        )
        throws ServiceException {

        return ResponseEntity.ok(
            appointmentManagementService
                .findById(id)
        );
    }

    /**
     * Crea appuntamento + servizi
     * in una sola transazione.
     */
    @PostMapping
    public ResponseEntity<AppointmentDetailDto>
        insert(
            @Valid
            @RequestBody
            AppointmentManagementRequestDto request
        )
        throws ServiceException {

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                appointmentManagementService
                    .insert(request)
            );
    }

    /**
     * Modifica appuntamento + servizi
     * in una sola transazione.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDetailDto>
        update(
            @PathVariable Integer id,
            @Valid
            @RequestBody
            AppointmentManagementRequestDto request
        )
        throws ServiceException {

        return ResponseEntity.ok(
            appointmentManagementService
                .update(
                    id,
                    request
                )
        );
    }
}
