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

import com.generation.hairlab.dto.ColorFormulaItemDto;
import com.generation.hairlab.service.ColorFormulaItemService;
import com.generation.hairlab.service.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/** Controller REST dedicato ai componenti delle formule colore. */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/color-formula-item")
public class ColorFormulaItemController {

    private final ColorFormulaItemService colorFormulaItemService;

    @GetMapping
    public ResponseEntity<List<ColorFormulaItemDto>> findAll() {
        return ResponseEntity.ok(colorFormulaItemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColorFormulaItemDto> findById(
            @PathVariable Integer id) throws ServiceException {
        return ResponseEntity.ok(colorFormulaItemService.findById(id));
    }

    @GetMapping("/formula/{colorFormulaId}")
    public ResponseEntity<List<ColorFormulaItemDto>> findByFormula(
            @PathVariable Integer colorFormulaId) {
        return ResponseEntity.ok(
                colorFormulaItemService.findByFormula(colorFormulaId));
    }

    @PostMapping
    public ResponseEntity<ColorFormulaItemDto> insert(
            @Valid @RequestBody ColorFormulaItemDto dto) throws ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(colorFormulaItemService.insert(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColorFormulaItemDto> update(
            @PathVariable Integer id,
            @Valid @RequestBody ColorFormulaItemDto dto) throws ServiceException {
        return ResponseEntity.ok(colorFormulaItemService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable Integer id) throws ServiceException {
        colorFormulaItemService.delete(id);
        return ResponseEntity.ok(
                Map.of("message", "Elemento formula eliminato correttamente"));
    }
}
