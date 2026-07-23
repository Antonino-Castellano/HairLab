package com.generation.hairlab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.hairlab.dto.AppointmentDto;
import com.generation.hairlab.service.AppointmentWorkflowService;
import com.generation.hairlab.service.ServiceException;

import lombok.RequiredArgsConstructor;

/**
 * Controller dedicato alle transizioni
 * di stato dell'appuntamento.
 *
 * Separiamo le azioni di workflow
 * dal normale PUT di modifica.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(
    "/hairlab/api/appointment-workflow"
)
public class AppointmentWorkflowController {

    private final AppointmentWorkflowService
        appointmentWorkflowService;

    /**
     * BOOKED -> CONFIRMED
     */
    @PatchMapping("/{id}/confirm")
    public ResponseEntity<AppointmentDto>
        confirm(
            @PathVariable Integer id
        )
        throws ServiceException {

        return ResponseEntity.ok(
            appointmentWorkflowService
                .confirm(id)
        );
    }

    /**
     * BOOKED / CONFIRMED -> IN_PROGRESS
     */
    @PatchMapping("/{id}/start")
    public ResponseEntity<AppointmentDto>
        start(
            @PathVariable Integer id
        )
        throws ServiceException {

        return ResponseEntity.ok(
            appointmentWorkflowService
                .start(id)
        );
    }

    /**
     * IN_PROGRESS -> COMPLETED
     */
    @PatchMapping("/{id}/complete")
    public ResponseEntity<AppointmentDto>
        complete(
            @PathVariable Integer id
        )
        throws ServiceException {

        return ResponseEntity.ok(
            appointmentWorkflowService
                .complete(id)
        );
    }

    /**
     * BOOKED / CONFIRMED -> NO_SHOW
     */
    @PatchMapping("/{id}/no-show")
    public ResponseEntity<AppointmentDto>
        markNoShow(
            @PathVariable Integer id
        )
        throws ServiceException {

        return ResponseEntity.ok(
            appointmentWorkflowService
                .markNoShow(id)
        );
    }
}
