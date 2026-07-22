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

import com.generation.hairlab.dto.ProductCategoryDto;
import com.generation.hairlab.service.ProductCategoryService;
import com.generation.hairlab.service.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/** Controller REST dedicato alle categorie del listino. */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/product-category")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @GetMapping
    public ResponseEntity<List<ProductCategoryDto>> findAll() {
        return ResponseEntity.ok(productCategoryService.findAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<ProductCategoryDto>> findActive() {
        return ResponseEntity.ok(productCategoryService.findActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryDto> findById(
            @PathVariable Integer id) throws ServiceException {
        return ResponseEntity.ok(productCategoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductCategoryDto> insert(
            @Valid @RequestBody ProductCategoryDto dto) throws ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(productCategoryService.insert(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryDto> update(
            @PathVariable Integer id,
            @Valid @RequestBody ProductCategoryDto dto) throws ServiceException {
        return ResponseEntity.ok(productCategoryService.update(id, dto));
    }

    /** Disattiva la categoria e i relativi servizi attivi. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable Integer id) throws ServiceException {
        productCategoryService.delete(id);
        return ResponseEntity.ok(
            Map.of("message", "Categoria disattivata correttamente")
        );
    }

    /** Riattiva solamente la categoria. */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ProductCategoryDto> activate(
            @PathVariable Integer id) throws ServiceException {
        return ResponseEntity.ok(productCategoryService.activate(id));
    }
}
