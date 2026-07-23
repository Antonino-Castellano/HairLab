package com.generation.hairlab.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.hairlab.dto.AppointmentAvailabilityRequestDto;
import com.generation.hairlab.dto.EmployeeAvailabilityDto;
import com.generation.hairlab.service.AppointmentAvailabilityService;
import com.generation.hairlab.service.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Endpoint dedicato alla verifica preventiva
 * della disponibilità operatori.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(
    "/hairlab/api/appointment-availability"
)
public class AppointmentAvailabilityController {

    private final AppointmentAvailabilityService
        appointmentAvailabilityService;

    /**
     * Restituisce tutti gli operatori attivi
     * indicando per ciascuno:
     *
     * - disponibile;
     * - occupato;
     * - eventuale appuntamento in conflitto.
     *
     * POST viene utilizzato perché la richiesta
     * contiene una struttura dati composta
     * da data/ora, durata ed eventuale
     * appointmentId da escludere.
     */
    @PostMapping("/employees")
    public ResponseEntity<
        List<EmployeeAvailabilityDto>
    > findEmployeeAvailability(
            @Valid
            @RequestBody
            AppointmentAvailabilityRequestDto request)
            throws ServiceException {

        return ResponseEntity.ok(
            appointmentAvailabilityService
                .findEmployeeAvailability(
                    request
                )
        );
    }
}
