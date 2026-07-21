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

import com.generation.hairlab.dto.ColorFormulaItemDto;
import com.generation.hairlab.service.ColorFormulaItemService;
import com.generation.hairlab.service.ServiceException;

import lombok.RequiredArgsConstructor;

/**
 * Controller REST dedicato agli elementi che compongono le formule colore.
 *
 * Le operazioni relative alla risoluzione di ColorFormula e HairDye
 * vengono delegate completamente al ColorFormulaItemService.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/color-formula-item")
@CrossOrigin(origins = "http://localhost:4200") 
public class ColorFormulaItemController {

    /** Service dedicato agli elementi delle formule. */
    private final ColorFormulaItemService colorFormulaItemService;

    /** Restituisce tutti gli elementi delle formule. */
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(colorFormulaItemService.findAll());
    }

    /** Cerca un ColorFormulaItem tramite ID. */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(colorFormulaItemService.findById(id));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Ricerca elemento formula fallita"));
        }
    }

    /**
     * Restituisce tutti gli elementi appartenenti a una formula.
     *
     * @param colorFormulaId identificativo della formula
     */
    @GetMapping("/formula/{colorFormulaId}")
    public ResponseEntity<?> findByFormula(
            @PathVariable Integer colorFormulaId) {

        return ResponseEntity.ok(
                colorFormulaItemService.findByFormula(colorFormulaId));
    }

    /** Inserisce un nuovo elemento della formula. */
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody ColorFormulaItemDto dto) {
        try {
            return ResponseEntity.ok(colorFormulaItemService.insert(dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Inserimento elemento formula fallito"));
        }
    }

    /** Aggiorna un elemento della formula esistente. */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @RequestBody ColorFormulaItemDto dto) {

        try {
            return ResponseEntity.ok(
                    colorFormulaItemService.update(id, dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Aggiornamento elemento formula fallito"));
        }
    }

    /** Elimina un elemento della formula tramite ID. */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            colorFormulaItemService.delete(id);
            return ResponseEntity.ok(
                    Map.of("message",
                           "Elemento della formula eliminato correttamente"));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Eliminazione elemento formula fallita"));
        }
    }
}
