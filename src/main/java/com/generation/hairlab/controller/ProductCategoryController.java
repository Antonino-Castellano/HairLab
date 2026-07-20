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

import com.generation.hairlab.dto.ProductCategoryDto;
import com.generation.hairlab.service.ProductCategoryService;
import com.generation.hairlab.service.ServiceException;

import lombok.RequiredArgsConstructor;

/**
 * Controller REST dedicato alle categorie utilizzate per organizzare
 * il listino del salone.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/product-category")
public class ProductCategoryController {

    /** Service dedicato alle categorie. */
    private ProductCategoryService productCategoryService;

    /** Restituisce tutte le categorie. */
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(productCategoryService.findAll());
    }

    /** Restituisce solamente le categorie attive. */
    @GetMapping("/active")
    public ResponseEntity<?> findActive() {
        return ResponseEntity.ok(productCategoryService.findActive());
    }

    /** Cerca una categoria tramite ID. */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(productCategoryService.findById(id));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Ricerca categoria fallita"));
        }
    }

    /** Inserisce una nuova categoria. */
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody ProductCategoryDto dto) {
        try {
            return ResponseEntity.ok(productCategoryService.insert(dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Inserimento categoria fallito"));
        }
    }

    /** Aggiorna una categoria esistente. */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @RequestBody ProductCategoryDto dto) {

        try {
            return ResponseEntity.ok(productCategoryService.update(id, dto));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Aggiornamento categoria fallito"));
        }
    }

    /** Elimina una categoria tramite ID. */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            productCategoryService.delete(id);
            return ResponseEntity.ok(
                    Map.of("message", "Categoria eliminata correttamente"));
        } catch (ServiceException e) {
            return ResponseEntity.badRequest()
                    .body(e.toMap("Eliminazione categoria fallita"));
        }
    }
}
