package com.generation.hairlab.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.generation.hairlab.dto.AppointmentDto;
import com.generation.hairlab.service.AppointmentQueryService;
import com.generation.hairlab.service.ServiceException;

import lombok.RequiredArgsConstructor;

/**
 * Controller REST dedicato ai filtri avanzati
 * dell'agenda.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(
    "/hairlab/api/appointment-query"
)
public class AppointmentQueryController {

    private final AppointmentQueryService
        appointmentQueryService;

    /**
     * Esempio:
     *
     * GET
     * /appointment-query/employee/2/between
     * ?start=2026-07-20T00:00:00
     * &end=2026-07-26T23:59:59
     */
    @GetMapping(
        "/employee/{employeeId}/between"
    )
    public ResponseEntity<List<AppointmentDto>>
        findByEmployeeBetween(
            @PathVariable
            Integer employeeId,

            @RequestParam
            @DateTimeFormat(
                iso =
                    DateTimeFormat.ISO.DATE_TIME
            )
            LocalDateTime start,

            @RequestParam
            @DateTimeFormat(
                iso =
                    DateTimeFormat.ISO.DATE_TIME
            )
            LocalDateTime end
        )
        throws ServiceException {

        return ResponseEntity.ok(
            appointmentQueryService
                .findByEmployeeBetween(
                    employeeId,
                    start,
                    end
                )
        );
    }
}
