package com.generation.hairlab.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.generation.hairlab.dto.AppointmentDto;
import com.generation.hairlab.enums.AppointmentStatus;
import com.generation.hairlab.service.AppointmentService;
import com.generation.hairlab.service.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/** Controller REST dedicato alla gestione degli appuntamenti. */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> findAll() {
        return ResponseEntity.ok(appointmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> findById(
            @PathVariable Integer id) throws ServiceException {
        return ResponseEntity.ok(appointmentService.findById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AppointmentDto>> findByCustomer(
            @PathVariable Integer customerId) {
        return ResponseEntity.ok(appointmentService.findByCustomer(customerId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AppointmentDto>> findByStatus(
            @PathVariable AppointmentStatus status) {
        return ResponseEntity.ok(appointmentService.findByStatus(status));
    }

    @GetMapping("/between")
    public ResponseEntity<List<AppointmentDto>> findBetween(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end) {
        return ResponseEntity.ok(appointmentService.findBetween(start, end));
    }

    @PostMapping
    public ResponseEntity<AppointmentDto> insert(
            @Valid @RequestBody AppointmentDto dto) throws ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(appointmentService.insert(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDto> update(
            @PathVariable Integer id,
            @Valid @RequestBody AppointmentDto dto) throws ServiceException {
        return ResponseEntity.ok(appointmentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable Integer id) throws ServiceException {
        appointmentService.delete(id);
        return ResponseEntity.ok(
                Map.of("message", "Appuntamento eliminato correttamente"));
    }
}
