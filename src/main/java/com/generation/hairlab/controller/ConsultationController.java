package com.generation.hairlab.controller;

import java.util.Map;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.hairlab.dto.ConsultationDto;
import com.generation.hairlab.service.ConsultationService;
import com.generation.hairlab.service.ServiceException;

import lombok.RequiredArgsConstructor;

/**
 * Controller REST dedicato alle consulenze tecniche HairLab.
 *
 * Espone il CRUD e lo storico delle consulenze associate a un cliente.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/consultation")
public class ConsultationController {

    /** Service dedicato alle consulenze. */
   
    private final ConsultationService consultationService;

    /** Restituisce tutte le consulenze. */
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(consultationService.findAll());
    }

    /** Cerca una consulenza tramite ID. */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(consultationService.findById(id));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Ricerca consulenza fallita"));
        }
    }

    /**
     * Restituisce lo storico delle consulenze di un cliente.
     *
     * @param customerId identificativo del cliente
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> findByCustomer(
            @PathVariable Integer customerId) {

        return ResponseEntity.ok(
                consultationService.findByCustomer(customerId));
    }

    /** Inserisce una nuova consulenza. */
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody ConsultationDto dto) {
        try {
            return ResponseEntity.ok(consultationService.insert(dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Inserimento consulenza fallito"));
        }
    }

    /** Aggiorna una consulenza esistente. */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @RequestBody ConsultationDto dto) {

        try {
            return ResponseEntity.ok(consultationService.update(id, dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Aggiornamento consulenza fallito"));
        }
    }

    /** Elimina una consulenza tramite ID. */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            consultationService.delete(id);
            return ResponseEntity.ok(
                    Map.of("message", "Consulenza eliminata correttamente"));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Eliminazione consulenza fallita"));
        }
    }
}
