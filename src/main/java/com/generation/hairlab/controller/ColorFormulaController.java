package com.generation.hairlab.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.hairlab.dto.ColorFormulaDto;
import com.generation.hairlab.service.ColorFormulaService;
import com.generation.hairlab.service.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/** Controller REST dedicato alle formule colore. */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/color-formula")
public class ColorFormulaController {

    private final ColorFormulaService colorFormulaService;

    @GetMapping
    public ResponseEntity<List<ColorFormulaDto>> findAll() {
        return ResponseEntity.ok(colorFormulaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColorFormulaDto> findById(
            @PathVariable Integer id) throws ServiceException {
        return ResponseEntity.ok(colorFormulaService.findById(id));
    }

    @GetMapping("/consultation/{consultationId}")
    public ResponseEntity<List<ColorFormulaDto>> findByConsultation(
            @PathVariable Integer consultationId) {
        return ResponseEntity.ok(
                colorFormulaService.findByConsultation(consultationId));
    }

    @PostMapping
    public ResponseEntity<ColorFormulaDto> insert(
            @Valid @RequestBody ColorFormulaDto dto) throws ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(colorFormulaService.insert(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColorFormulaDto> update(
            @PathVariable Integer id,
            @Valid @RequestBody ColorFormulaDto dto) throws ServiceException {
        return ResponseEntity.ok(colorFormulaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable Integer id) throws ServiceException {
        colorFormulaService.delete(id);
        return ResponseEntity.ok(
                Map.of("message", "Formula colore eliminata correttamente"));
    }
}
