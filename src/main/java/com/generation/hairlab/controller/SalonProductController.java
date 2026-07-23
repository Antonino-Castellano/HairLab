package com.generation.hairlab.controller;

import java.util.List;

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

import com.generation.hairlab.dto.SalonProductDto;
import com.generation.hairlab.service.SalonProductService;
import com.generation.hairlab.service.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/** Controller REST dedicato ai servizi/prodotti del listino. */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/salon-product")
public class SalonProductController {

    private final SalonProductService salonProductService;

    @GetMapping
    public ResponseEntity<List<SalonProductDto>> findAll() {
        return ResponseEntity.ok(salonProductService.findAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<SalonProductDto>> findActive() {
        return ResponseEntity.ok(salonProductService.findActive());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<SalonProductDto>> findByCategory(
            @PathVariable Integer categoryId) throws ServiceException {
        return ResponseEntity.ok(salonProductService.findByCategory(categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalonProductDto> findById(
            @PathVariable Integer id) throws ServiceException {
        return ResponseEntity.ok(salonProductService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SalonProductDto> insert(
            @Valid @RequestBody SalonProductDto dto) throws ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(salonProductService.insert(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalonProductDto> update(
            @PathVariable Integer id,
            @Valid @RequestBody SalonProductDto dto) throws ServiceException {
        return ResponseEntity.ok(salonProductService.update(id, dto));
    }

    /** Eliminazione FISICA e permanente dal database. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id) throws ServiceException {
        salonProductService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /** Disattiva logicamente il servizio tramite PATCH. */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<SalonProductDto> deactivate(
            @PathVariable Integer id) throws ServiceException {
        return ResponseEntity.ok(salonProductService.deactivate(id));
    }

    /** Riattiva un servizio se la categoria è attiva. */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<SalonProductDto> activate(
            @PathVariable Integer id) throws ServiceException {
        return ResponseEntity.ok(salonProductService.activate(id));
    }
}