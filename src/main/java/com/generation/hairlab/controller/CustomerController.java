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

import com.generation.hairlab.dto.CustomerDto;
import com.generation.hairlab.service.CustomerService;
import com.generation.hairlab.service.ServiceException;

import lombok.RequiredArgsConstructor;

/**
 * Controller REST dedicato alla gestione dei clienti.
 *
 * Espone gli endpoint HTTP necessari per leggere, creare, modificare
 * ed eliminare i clienti.
 *
 * Il Controller non contiene logica applicativa:
 * riceve le richieste HTTP, delega il lavoro al CustomerService
 * e trasforma il risultato in una ResponseEntity.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/customer")
public class CustomerController {

    /** Service contenente la logica applicativa relativa ai clienti. */
    private CustomerService customerService;

    /**
     * Restituisce tutti i clienti presenti nel database.
     *
     * GET /hairlab/api/customer
     *
     * @return lista completa dei clienti
     */
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    /**
     * Restituisce solamente i clienti attivi.
     *
     * GET /hairlab/api/customer/active
     *
     * @return lista dei clienti attivi
     */
    @GetMapping("/active")
    public ResponseEntity<?> findActive() {
        return ResponseEntity.ok(customerService.findActive());
    }

    /**
     * Cerca un cliente tramite identificativo.
     *
     * GET /hairlab/api/customer/{id}
     *
     * @param id identificativo del cliente
     * @return cliente trovato oppure descrizione dell'errore
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(customerService.findById(id));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Ricerca cliente fallita"));
        }
    }

    /**
     * Inserisce un nuovo cliente.
     *
     * POST /hairlab/api/customer
     *
     * @param dto dati del cliente ricevuti nel body JSON
     * @return cliente salvato oppure descrizione dell'errore
     */
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody CustomerDto dto) {
        try {
            return ResponseEntity.ok(customerService.insert(dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Inserimento cliente fallito"));
        }
    }

    /**
     * Aggiorna un cliente esistente.
     *
     * PUT /hairlab/api/customer/{id}
     *
     * @param id identificativo del cliente da modificare
     * @param dto nuovi dati del cliente
     * @return cliente aggiornato oppure descrizione dell'errore
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @RequestBody CustomerDto dto) {

        try {
            return ResponseEntity.ok(customerService.update(id, dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Aggiornamento cliente fallito"));
        }
    }

    /**
     * Elimina un cliente tramite identificativo.
     *
     * DELETE /hairlab/api/customer/{id}
     *
     * @param id identificativo del cliente
     * @return messaggio di conferma oppure descrizione dell'errore
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            customerService.delete(id);
            return ResponseEntity.ok(
                    Map.of("message", "Cliente eliminato correttamente"));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Eliminazione cliente fallita"));
        }
    }
}
