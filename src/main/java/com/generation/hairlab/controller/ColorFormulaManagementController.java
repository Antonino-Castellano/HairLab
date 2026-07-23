package com.generation.hairlab.controller;

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

import com.generation.hairlab.dto.ColorFormulaDetailDto;
import com.generation.hairlab.dto.ColorFormulaManagementRequestDto;
import com.generation.hairlab.service.ColorFormulaManagementService;
import com.generation.hairlab.service.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Endpoint aggregato del Formula Builder.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(
    "/hairlab/api/color-formula-management"
)
public class ColorFormulaManagementController {

    private final ColorFormulaManagementService
        managementService;

    @GetMapping("/{id}")
    public ResponseEntity<ColorFormulaDetailDto>
        findById(
            @PathVariable
            Integer id)
            throws ServiceException {

        return ResponseEntity.ok(
            managementService.findById(
                id
            )
        );
    }

    @PostMapping
    public ResponseEntity<ColorFormulaDetailDto>
        create(
            @Valid
            @RequestBody
            ColorFormulaManagementRequestDto request)
            throws ServiceException {

        return ResponseEntity
            .status(
                HttpStatus.CREATED
            )
            .body(
                managementService.create(
                    request
                )
            );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColorFormulaDetailDto>
        update(
            @PathVariable
            Integer id,
            @Valid
            @RequestBody
            ColorFormulaManagementRequestDto request)
            throws ServiceException {

        return ResponseEntity.ok(
            managementService.update(
                id,
                request
            )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>>
        delete(
            @PathVariable
            Integer id)
            throws ServiceException {

        managementService.delete(
            id
        );

        return ResponseEntity.ok(
            Map.of(
                "message",
                "Formula eliminata correttamente"
            )
        );
    }
}
