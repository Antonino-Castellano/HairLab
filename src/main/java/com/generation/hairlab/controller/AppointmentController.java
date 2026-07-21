package com.generation.hairlab.controller;

import java.time.LocalDateTime;
import java.util.Map;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import lombok.RequiredArgsConstructor;

/**
 * Controller REST dedicato alla gestione degli appuntamenti.
 *
 * Oltre al CRUD standard espone endpoint per filtrare gli appuntamenti
 * per cliente, stato e intervallo temporale, utili per agenda e storico.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/appointment")
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentController {

    /** Service dedicato agli appuntamenti. */
    private final AppointmentService appointmentService;

    /** Restituisce tutti gli appuntamenti. */
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(appointmentService.findAll());
    }

    /** Cerca un appuntamento tramite ID. */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(appointmentService.findById(id));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Ricerca appuntamento fallita"));
        }
    }

    /**
     * Restituisce lo storico degli appuntamenti di un cliente.
     *
     * GET /hairlab/api/appointment/customer/{customerId}
     *
     * @param customerId identificativo del cliente
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> findByCustomer(
            @PathVariable Integer customerId) {

        return ResponseEntity.ok(
                appointmentService.findByCustomer(customerId));
    }

    /**
     * Filtra gli appuntamenti in base allo stato.
     *
     * Esempio:
     * GET /hairlab/api/appointment/status/BOOKED
     *
     * @param status stato dell'appuntamento
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<?> findByStatus(
            @PathVariable AppointmentStatus status) {

        return ResponseEntity.ok(
                appointmentService.findByStatus(status));
    }

    /**
     * Restituisce gli appuntamenti compresi in un intervallo temporale.
     *
     * Le date devono essere inviate in formato ISO-8601, ad esempio:
     * 2026-07-20T00:00:00
     *
     * GET /hairlab/api/appointment/between?start=...&end=...
     *
     * @param start data/ora iniziale
     * @param end data/ora finale
     */
    @GetMapping("/between")
    public ResponseEntity<?> findBetween(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end) {

        return ResponseEntity.ok(
                appointmentService.findBetween(start, end));
    }

    /** Inserisce un nuovo appuntamento. */
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody AppointmentDto dto) {
        try {
            return ResponseEntity.ok(appointmentService.insert(dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Inserimento appuntamento fallito"));
        }
    }

    /** Aggiorna un appuntamento esistente. */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @RequestBody AppointmentDto dto) {

        try {
            return ResponseEntity.ok(appointmentService.update(id, dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Aggiornamento appuntamento fallito"));
        }
    }

    /** Elimina un appuntamento tramite ID. */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            appointmentService.delete(id);
            return ResponseEntity.ok(
                    Map.of("message", "Appuntamento eliminato correttamente"));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Eliminazione appuntamento fallita"));
        }
    }
}
