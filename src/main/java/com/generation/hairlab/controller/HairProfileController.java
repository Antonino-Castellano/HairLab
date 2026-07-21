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

import com.generation.hairlab.dto.HairProfileDto;
import com.generation.hairlab.service.HairProfileService;
import com.generation.hairlab.service.ServiceException;

import lombok.RequiredArgsConstructor;

/**
 * Controller REST dedicato alle schede tecniche HairProfile.
 *
 * Permette di gestire il CRUD delle schede e di recuperare direttamente
 * la HairProfile associata a uno specifico cliente.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/hair-profile")
@CrossOrigin(origins = "http://localhost:4200")
public class HairProfileController {

    /** Service dedicato alla gestione delle HairProfile. */
    private final HairProfileService hairProfileService;

    /** Restituisce tutte le schede tecniche. */
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(hairProfileService.findAll());
    }

    /**
     * Cerca una HairProfile tramite ID.
     *
     * @param id identificativo della scheda
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(hairProfileService.findById(id));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Ricerca HairProfile fallita"));
        }
    }

    /**
     * Cerca la HairProfile appartenente a un cliente.
     *
     * GET /hairlab/api/hair-profile/customer/{customerId}
     *
     * @param customerId identificativo del cliente
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> findByCustomerId(
            @PathVariable Integer customerId) {

        try {
            return ResponseEntity.ok(
                    hairProfileService.findByCustomerId(customerId));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Ricerca HairProfile del cliente fallita"));
        }
    }

    /** Inserisce una nuova HairProfile. */
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody HairProfileDto dto) {
        try {
            return ResponseEntity.ok(hairProfileService.insert(dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Inserimento HairProfile fallito"));
        }
    }

    /** Aggiorna una HairProfile esistente. */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @RequestBody HairProfileDto dto) {

        try {
            return ResponseEntity.ok(hairProfileService.update(id, dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Aggiornamento HairProfile fallito"));
        }
    }

    /** Elimina una HairProfile tramite ID. */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            hairProfileService.delete(id);
            return ResponseEntity.ok(
                    Map.of("message", "HairProfile eliminata correttamente"));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Eliminazione HairProfile fallita"));
        }
    }
}
