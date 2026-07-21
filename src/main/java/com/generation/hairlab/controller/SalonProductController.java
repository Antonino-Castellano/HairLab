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

import com.generation.hairlab.dto.SalonProductDto;
import com.generation.hairlab.service.SalonProductService;
import com.generation.hairlab.service.ServiceException;

import lombok.RequiredArgsConstructor;

/**
 * Controller REST dedicato ai servizi/prodotti del listino HairLab.
 *
 * Oltre al CRUD permette di filtrare gli elementi attivi e quelli
 * appartenenti a una specifica categoria.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/salon-product")
@CrossOrigin(origins = "http://localhost:4200")
public class SalonProductController {

    /** Service dedicato ai SalonProduct. */
    private final SalonProductService salonProductService;

    /** Restituisce tutti i servizi/prodotti. */
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(salonProductService.findAll());
    }

    /** Restituisce solamente i servizi/prodotti attivi. */
    @GetMapping("/active")
    public ResponseEntity<?> findActive() {
        return ResponseEntity.ok(salonProductService.findActive());
    }

    /**
     * Restituisce gli elementi appartenenti a una categoria.
     *
     * @param categoryId identificativo della categoria
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> findByCategory(
            @PathVariable Integer categoryId) {

        return ResponseEntity.ok(
                salonProductService.findByCategory(categoryId));
    }

    /** Cerca un SalonProduct tramite ID. */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(salonProductService.findById(id));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Ricerca servizio fallita"));
        }
    }

    /** Inserisce un nuovo servizio/prodotto. */
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody SalonProductDto dto) {
        try {
            return ResponseEntity.ok(salonProductService.insert(dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Inserimento servizio fallito"));
        }
    }

    /** Aggiorna un SalonProduct esistente. */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @RequestBody SalonProductDto dto) {

        try {
            return ResponseEntity.ok(salonProductService.update(id, dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Aggiornamento servizio fallito"));
        }
    }

    /** Elimina un SalonProduct tramite ID. */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            salonProductService.delete(id);
            return ResponseEntity.ok(
                    Map.of("message", "Servizio eliminato correttamente"));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Eliminazione servizio fallita"));
        }
    }
}
