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

import com.generation.hairlab.dto.HairProfileDto;
import com.generation.hairlab.service.HairProfileService;
import com.generation.hairlab.service.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/** Controller REST dedicato al profilo tecnico dei capelli. */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/hair-profile")
public class HairProfileController {

    private final HairProfileService hairProfileService;

    @GetMapping
    public ResponseEntity<List<HairProfileDto>> findAll() {
        return ResponseEntity.ok(hairProfileService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HairProfileDto> findById(
            @PathVariable Integer id) throws ServiceException {
        return ResponseEntity.ok(hairProfileService.findById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<HairProfileDto> findByCustomerId(
            @PathVariable Integer customerId) throws ServiceException {
        return ResponseEntity.ok(hairProfileService.findByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<HairProfileDto> insert(
            @Valid @RequestBody HairProfileDto dto) throws ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hairProfileService.insert(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HairProfileDto> update(
            @PathVariable Integer id,
            @Valid @RequestBody HairProfileDto dto) throws ServiceException {
        return ResponseEntity.ok(hairProfileService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable Integer id) throws ServiceException {
        hairProfileService.delete(id);
        return ResponseEntity.ok(
                Map.of("message", "Profilo capelli eliminato correttamente"));
    }
}
