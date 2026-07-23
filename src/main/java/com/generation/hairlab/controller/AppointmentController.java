package com.generation.hairlab.controller;

import java.time.LocalDateTime;
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
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok(appointmentService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Errore durante il recupero degli appuntamenti: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(appointmentService.findById(id));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatus())
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Errore del server: " + e.getMessage()));
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> findByCustomer(@PathVariable Integer customerId) {
        try {
            return ResponseEntity.ok(appointmentService.findByCustomer(customerId));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatus())
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Errore durante la ricerca per cliente: " + e.getMessage()));
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> findByStatus(@PathVariable AppointmentStatus status) {
        try {
            return ResponseEntity.ok(appointmentService.findByStatus(status));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatus())
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Errore durante la ricerca per stato: " + e.getMessage()));
        }
    }

    @GetMapping("/between")
    public ResponseEntity<?> findBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        try {
            return ResponseEntity.ok(appointmentService.findBetween(start, end));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatus())
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Errore durante la ricerca per intervallo di date: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> insert(@Valid @RequestBody AppointmentDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(appointmentService.insert(dto));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatus())
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Errore durante l'inserimento dell'appuntamento: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @Valid @RequestBody AppointmentDto dto) {
        try {
            return ResponseEntity.ok(appointmentService.update(id, dto));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatus())
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Errore durante l'aggiornamento dell'appuntamento: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            appointmentService.delete(id);
            return ResponseEntity.ok(Map.of("message", "Appuntamento eliminato correttamente"));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatus())
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Errore durante l'eliminazione dell'appuntamento: " + e.getMessage()));
        }
    }
}