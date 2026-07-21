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

import com.generation.hairlab.dto.HairDyeDto;
import com.generation.hairlab.service.HairDyeService;
import com.generation.hairlab.service.ServiceException;

import lombok.RequiredArgsConstructor;

/**
 * Controller REST dedicato alle tinte e ai prodotti tecnici colore.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/hair-dye")
@CrossOrigin(origins = "http://localhost:4200")
public class HairDyeController {

    /** Service dedicato agli HairDye. */
   
    private final HairDyeService hairDyeService;

    /** Restituisce tutti i prodotti tecnici colore. */
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(hairDyeService.findAll());
    }

    /** Restituisce solamente i prodotti attivi. */
    @GetMapping("/active")
    public ResponseEntity<?> findActive() {
        return ResponseEntity.ok(hairDyeService.findActive());
    }

    /** Cerca un HairDye tramite ID. */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(hairDyeService.findById(id));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Ricerca prodotto colore fallita"));
        }
    }

    /** Inserisce un nuovo HairDye. */
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody HairDyeDto dto) {
        try {
            return ResponseEntity.ok(hairDyeService.insert(dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Inserimento prodotto colore fallito"));
        }
    }

    /** Aggiorna un HairDye esistente. */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @RequestBody HairDyeDto dto) {

        try {
            return ResponseEntity.ok(hairDyeService.update(id, dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Aggiornamento prodotto colore fallito"));
        }
    }

    /** Elimina un HairDye tramite ID. */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            hairDyeService.delete(id);
            return ResponseEntity.ok(
                    Map.of("message", "Prodotto colore eliminato correttamente"));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Eliminazione prodotto colore fallita"));
        }
    }
}
