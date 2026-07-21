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

import com.generation.hairlab.dto.ColorFormulaDto;
import com.generation.hairlab.service.ColorFormulaService;
import com.generation.hairlab.service.ServiceException;

import lombok.RequiredArgsConstructor;

/**
 * Controller REST dedicato alle formule colore.
 *
 * Permette di gestire il CRUD e di recuperare le formule associate
 * a una specifica consulenza.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/color-formula")
@CrossOrigin(origins = "http://localhost:4200")
public class ColorFormulaController {

    /** Service dedicato alle formule colore. */
    private final ColorFormulaService colorFormulaService;

    /** Restituisce tutte le formule colore. */
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(colorFormulaService.findAll());
    }

    /** Cerca una formula tramite ID. */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(colorFormulaService.findById(id));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Ricerca formula colore fallita"));
        }
    }

    /**
     * Restituisce le formule associate a una consulenza.
     *
     * @param consultationId identificativo della consulenza
     */
    @GetMapping("/consultation/{consultationId}")
    public ResponseEntity<?> findByConsultation(
            @PathVariable Integer consultationId) {

        return ResponseEntity.ok(
                colorFormulaService.findByConsultation(consultationId));
    }

    /** Inserisce una nuova formula colore. */
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody ColorFormulaDto dto) {
        try {
            return ResponseEntity.ok(colorFormulaService.insert(dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Inserimento formula colore fallito"));
        }
    }

    /** Aggiorna una formula colore esistente. */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @RequestBody ColorFormulaDto dto) {

        try {
            return ResponseEntity.ok(colorFormulaService.update(id, dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Aggiornamento formula colore fallito"));
        }
    }

    /** Elimina una formula colore tramite ID. */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            colorFormulaService.delete(id);
            return ResponseEntity.ok(
                    Map.of("message", "Formula colore eliminata correttamente"));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Eliminazione formula colore fallita"));
        }
    }
}
