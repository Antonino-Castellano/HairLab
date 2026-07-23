package com.generation.hairlab.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.hairlab.dto.HairDyeInventoryDto;
import com.generation.hairlab.service.HairDyeInventoryService;
import com.generation.hairlab.service.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * API del magazzino Color Lab.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(
    "/hairlab/api/hair-dye-inventory"
)
public class HairDyeInventoryController {

    private final HairDyeInventoryService
        inventoryService;

    @GetMapping
    public ResponseEntity<List<HairDyeInventoryDto>>
        findAll() {

        return ResponseEntity.ok(
            inventoryService.findAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<HairDyeInventoryDto>
        findById(
            @PathVariable
            Integer id)
            throws ServiceException {

        return ResponseEntity.ok(
            inventoryService.findById(
                id
            )
        );
    }

    @GetMapping("/hair-dye/{hairDyeId}")
    public ResponseEntity<HairDyeInventoryDto>
        findByHairDyeId(
            @PathVariable
            Integer hairDyeId)
            throws ServiceException {

        return ResponseEntity.ok(
            inventoryService.findByHairDyeId(
                hairDyeId
            )
        );
    }

    @PostMapping
    public ResponseEntity<HairDyeInventoryDto>
        insert(
            @Valid
            @RequestBody
            HairDyeInventoryDto dto)
            throws ServiceException {

        return ResponseEntity
            .status(
                HttpStatus.CREATED
            )
            .body(
                inventoryService.insert(
                    dto
                )
            );
    }

    @PutMapping("/{id}")
    public ResponseEntity<HairDyeInventoryDto>
        update(
            @PathVariable
            Integer id,
            @Valid
            @RequestBody
            HairDyeInventoryDto dto)
            throws ServiceException {

        return ResponseEntity.ok(
            inventoryService.update(
                id,
                dto
            )
        );
    }
}
