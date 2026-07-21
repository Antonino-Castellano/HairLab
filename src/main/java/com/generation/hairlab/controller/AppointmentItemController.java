package com.generation.hairlab.controller;

import java.util.Map;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import lombok.RequiredArgsConstructor;

/**
 * Controller REST dedicato ai singoli servizi contenuti negli appuntamenti.
 *
 * Le regole relative a dipendente, servizio, relazioni e sovrapposizioni
 * restano nel layer Service.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/appointment-item")
@CrossOrigin(origins = "http://localhost:4200") 
public class AppointmentItemController {

    /** Service dedicato agli AppointmentItem. */
    
    private final AppointmentItemService appointmentItemService;

    /** Restituisce tutti gli AppointmentItem. */
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(appointmentItemService.findAll());
    }

    /** Cerca un AppointmentItem tramite ID. */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(appointmentItemService.findById(id));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Ricerca servizio appuntamento fallita"));
        }
    }

    /**
     * Restituisce tutti gli item appartenenti a uno specifico appuntamento.
     *
     * @param appointmentId identificativo dell'appuntamento
     */
    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<?> findByAppointment(
            @PathVariable Integer appointmentId) {

        return ResponseEntity.ok(
                appointmentItemService.findByAppointment(appointmentId));
    }

    /** Inserisce un nuovo AppointmentItem. */
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody AppointmentItemDto dto) {
        try {
            return ResponseEntity.ok(appointmentItemService.insert(dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Inserimento servizio appuntamento fallito"));
        }
    }

    /** Aggiorna un AppointmentItem esistente. */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @RequestBody AppointmentItemDto dto) {

        try {
            return ResponseEntity.ok(
                    appointmentItemService.update(id, dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Aggiornamento servizio appuntamento fallito"));
        }
    }

    /** Elimina un AppointmentItem tramite ID. */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            appointmentItemService.delete(id);
            return ResponseEntity.ok(
                    Map.of("message",
                           "Servizio dell'appuntamento eliminato correttamente"));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Eliminazione servizio appuntamento fallita"));
        }
    }
}
