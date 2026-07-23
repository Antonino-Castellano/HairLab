package com.generation.hairlab.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.hairlab.dto.CustomerDto;
import com.generation.hairlab.service.CustomerService;
import com.generation.hairlab.service.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller REST dei clienti.
 *
 * Endpoint principali:
 * GET /customer -> tutti;
 * GET /customer/active -> solo attivi;
 * GET /customer/inactive -> solo disattivati;
 * PATCH /{id}/deactivate -> disattivazione;
 * PATCH /{id}/activate -> riattivazione;
 * DELETE /{id} -> eliminazione fisica definitiva.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    /** Tutti i clienti. */
    @GetMapping
    public ResponseEntity<List<CustomerDto>> findAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    /** Solo clienti attivi. */
    @GetMapping("/active")
    public ResponseEntity<List<CustomerDto>> findActive() {
        return ResponseEntity.ok(customerService.findActive());
    }

    /** Solo clienti disattivati. */
    @GetMapping("/inactive")
    public ResponseEntity<List<CustomerDto>> findInactive() {
        return ResponseEntity.ok(customerService.findInactive());
    }

    /** Cliente tramite ID. */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> findById(@PathVariable Integer id) throws ServiceException {
        return ResponseEntity.ok(customerService.findById(id));
    }

    /** Nuovo cliente. */
    @PostMapping
    public ResponseEntity<CustomerDto> insert(@Valid @RequestBody CustomerDto dto) throws ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.insert(dto));
    }

    /** Modifica cliente. */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> update(@PathVariable Integer id, @Valid @RequestBody CustomerDto dto) throws ServiceException {
        return ResponseEntity.ok(customerService.update(id, dto));
    }

    /** Disattiva il cliente. */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<CustomerDto> deactivate(@PathVariable Integer id) throws ServiceException {
        return ResponseEntity.ok(customerService.deactivate(id));
    }

    /** Riattiva il cliente. */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<CustomerDto> activate(@PathVariable Integer id) throws ServiceException {
        return ResponseEntity.ok(customerService.activate(id));
    }

    /**
     * Elimina realmente il cliente.
     * Se esiste storico, il Service restituisce 409 Conflict.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Integer id) throws ServiceException {
        customerService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Cliente eliminato definitivamente"));
    }
}