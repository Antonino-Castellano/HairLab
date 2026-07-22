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

import com.generation.hairlab.dto.FaceProfileDto;
import com.generation.hairlab.service.FaceProfileService;
import com.generation.hairlab.service.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/** Controller REST dedicato all'analisi morfologica del viso. */
@RestController
@RequestMapping("/hairlab/api/face-profile")
@RequiredArgsConstructor
public class FaceProfileController {

    private final FaceProfileService faceProfileService;

    @GetMapping
    public ResponseEntity<List<FaceProfileDto>> findAll() {
        return ResponseEntity.ok(faceProfileService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaceProfileDto> findById(
            @PathVariable Integer id) throws ServiceException {
        return ResponseEntity.ok(faceProfileService.findById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<FaceProfileDto> findByCustomerId(
            @PathVariable Integer customerId) throws ServiceException {
        return ResponseEntity.ok(faceProfileService.findByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<FaceProfileDto> insert(
            @Valid @RequestBody FaceProfileDto dto) throws ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(faceProfileService.insert(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FaceProfileDto> update(
            @PathVariable Integer id,
            @Valid @RequestBody FaceProfileDto dto) throws ServiceException {
        return ResponseEntity.ok(faceProfileService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable Integer id) throws ServiceException {
        faceProfileService.delete(id);
        return ResponseEntity.ok(
                Map.of("message", "Profilo viso eliminato correttamente"));
    }
}
