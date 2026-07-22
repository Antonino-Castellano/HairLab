package com.generation.hairlab.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.hairlab.dto.AppointmentItemDto;
import com.generation.hairlab.service.AppointmentItemService;
import com.generation.hairlab.service.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/** Controller REST dedicato ai servizi contenuti negli appuntamenti. */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/appointment-item")
public class AppointmentItemController {

    private final AppointmentItemService appointmentItemService;

    @GetMapping
    public ResponseEntity<List<AppointmentItemDto>> findAll() {
        return ResponseEntity.ok(appointmentItemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentItemDto> findById(
            @PathVariable Integer id) throws ServiceException {
        return ResponseEntity.ok(appointmentItemService.findById(id));
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<AppointmentItemDto>> findByAppointment(
            @PathVariable Integer appointmentId) {
        return ResponseEntity.ok(
                appointmentItemService.findByAppointment(appointmentId));
    }

    @PostMapping
    public ResponseEntity<AppointmentItemDto> insert(
            @Valid @RequestBody AppointmentItemDto dto) throws ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(appointmentItemService.insert(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentItemDto> update(
            @PathVariable Integer id,
            @Valid @RequestBody AppointmentItemDto dto) throws ServiceException {
        return ResponseEntity.ok(appointmentItemService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable Integer id) throws ServiceException {
        appointmentItemService.delete(id);
        return ResponseEntity.ok(
                Map.of("message", "Servizio dell'appuntamento eliminato correttamente"));
    }
}
