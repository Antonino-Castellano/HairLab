package com.generation.hairlab.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.generation.hairlab.dto.AppointmentTimelineItemDto;
import com.generation.hairlab.service.AppointmentTimelineService;

import lombok.RequiredArgsConstructor;

/**
 * Endpoint aggregato per la Timeline operatori.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(
    "/hairlab/api/appointment-timeline"
)
public class AppointmentTimelineController {

    private final AppointmentTimelineService
        appointmentTimelineService;

    /**
     * Esempio:
     *
     * GET
     * /hairlab/api/appointment-timeline/day
     * ?date=2026-07-23
     */
    @GetMapping("/day")
    public ResponseEntity<
        List<AppointmentTimelineItemDto>
    > findDay(
            @RequestParam
            @DateTimeFormat(
                iso =
                    DateTimeFormat.ISO.DATE
            )
            LocalDate date) {

        return ResponseEntity.ok(
            appointmentTimelineService
                .findByDate(
                    date
                )
        );
    }
}
