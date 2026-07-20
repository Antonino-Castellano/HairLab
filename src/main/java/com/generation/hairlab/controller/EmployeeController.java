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

import com.generation.hairlab.dto.EmployeeDto;
import com.generation.hairlab.service.EmployeeService;
import com.generation.hairlab.service.ServiceException;

import lombok.RequiredArgsConstructor;

/**
 * Controller REST dedicato alla gestione dei dipendenti del salone.
 *
 * Delega tutte le regole applicative all'EmployeeService e si occupa
 * esclusivamente della comunicazione HTTP con il client.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/employee")
public class EmployeeController {

    /** Service dedicato alla gestione dei dipendenti. */
    private EmployeeService employeeService;

    /** Restituisce tutti i dipendenti. */
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    /** Restituisce solamente i dipendenti attivi. */
    @GetMapping("/active")
    public ResponseEntity<?> findActive() {
        return ResponseEntity.ok(employeeService.findActive());
    }

    /**
     * Cerca un dipendente tramite ID.
     *
     * @param id identificativo del dipendente
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(employeeService.findById(id));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Ricerca dipendente fallita"));
        }
    }

    /**
     * Inserisce un nuovo dipendente.
     *
     * @param dto dati ricevuti dal client
     */
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody EmployeeDto dto) {
        try {
            return ResponseEntity.ok(employeeService.insert(dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Inserimento dipendente fallito"));
        }
    }

    /**
     * Aggiorna un dipendente esistente.
     *
     * @param id identificativo del dipendente
     * @param dto nuovi dati
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @RequestBody EmployeeDto dto) {

        try {
            return ResponseEntity.ok(employeeService.update(id, dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Aggiornamento dipendente fallito"));
        }
    }

    /**
     * Elimina un dipendente tramite ID.
     *
     * @param id identificativo del dipendente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            employeeService.delete(id);
            return ResponseEntity.ok(
                    Map.of("message", "Dipendente eliminato correttamente"));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Eliminazione dipendente fallita"));
        }
    }
}
