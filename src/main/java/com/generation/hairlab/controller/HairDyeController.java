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

import com.generation.hairlab.dto.HairDyeDto;
import com.generation.hairlab.service.HairDyeService;
import com.generation.hairlab.service.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/** Controller REST dedicato ai prodotti colore. */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/hair-dye")
public class HairDyeController {

    private final HairDyeService hairDyeService;

    @GetMapping
    public ResponseEntity<List<HairDyeDto>> findAll() {
        return ResponseEntity.ok(hairDyeService.findAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<HairDyeDto>> findActive() {
        return ResponseEntity.ok(hairDyeService.findActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HairDyeDto> findById(
            @PathVariable Integer id) throws ServiceException {
        return ResponseEntity.ok(hairDyeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<HairDyeDto> insert(
            @Valid @RequestBody HairDyeDto dto) throws ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(hairDyeService.insert(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HairDyeDto> update(
            @PathVariable Integer id,
            @Valid @RequestBody HairDyeDto dto) throws ServiceException {
        return ResponseEntity.ok(hairDyeService.update(id, dto));
    }

    /** Disattiva logicamente il prodotto colore. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable Integer id) throws ServiceException {
        hairDyeService.delete(id);
        return ResponseEntity.ok(
            Map.of("message", "Prodotto colore disattivato correttamente")
        );
    }

    /** Riattiva un prodotto colore disattivato. */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<HairDyeDto> activate(
            @PathVariable Integer id) throws ServiceException {
        return ResponseEntity.ok(hairDyeService.activate(id));
    }
}
