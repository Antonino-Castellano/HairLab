package com.generation.hairlab.controller;

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

import com.generation.hairlab.dto.ColorAnalysisDto;
import com.generation.hairlab.service.ColorAnalysisService;
import com.generation.hairlab.service.ServiceException;

import lombok.RequiredArgsConstructor;

/**
 * Controller REST per ColorAnalysis.
 */
@RestController
@RequestMapping("/hairlab/api/color-analysis")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ColorAnalysisController {

    private final ColorAnalysisService colorAnalysisService;

    @GetMapping
    public ResponseEntity<?> findAll() {

        return ResponseEntity.ok(
            colorAnalysisService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(
            @PathVariable Integer id) {

        try {

            return ResponseEntity.ok(
                colorAnalysisService.findById(id)
            );

        } catch (ServiceException e) {

            return ResponseEntity
                .badRequest()
                .body(
                    e.toMap(
                        "cannot find color analysis"
                    )
                );
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> findByCustomerId(
            @PathVariable Integer customerId) {

        try {

            return ResponseEntity.ok(
                colorAnalysisService
                    .findByCustomerId(
                        customerId
                    )
            );

        } catch (ServiceException e) {

            return ResponseEntity
                .badRequest()
                .body(
                    e.toMap(
                        "cannot find customer color analysis"
                    )
                );
        }
    }

    @PostMapping
    public ResponseEntity<?> insert(
            @RequestBody ColorAnalysisDto dto) {

        try {

            return ResponseEntity.ok(
                colorAnalysisService.insert(dto)
            );

        } catch (ServiceException e) {

            return ResponseEntity
                .badRequest()
                .body(
                    e.toMap(
                        "cannot save color analysis"
                    )
                );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @RequestBody ColorAnalysisDto dto) {

        try {

            return ResponseEntity.ok(
                colorAnalysisService.update(
                    id,
                    dto
                )
            );

        } catch (ServiceException e) {

            return ResponseEntity
                .badRequest()
                .body(
                    e.toMap(
                        "cannot update color analysis"
                    )
                );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Integer id) {

        try {

            colorAnalysisService.delete(id);

            return ResponseEntity
                .noContent()
                .build();

        } catch (ServiceException e) {

            return ResponseEntity
                .badRequest()
                .body(
                    e.toMap(
                        "cannot delete color analysis"
                    )
                );
        }
    }
}