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

import com.generation.hairlab.dto.ConsultationDto;
import com.generation.hairlab.service.ConsultationService;
import com.generation.hairlab.service.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/** Controller REST dedicato alle consulenze tecniche. */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/consultation")
public class ConsultationController {

    private final ConsultationService consultationService;

    @GetMapping
    public ResponseEntity<List<ConsultationDto>> findAll() {
        return ResponseEntity.ok(consultationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultationDto> findById(
            @PathVariable Integer id) throws ServiceException {
        return ResponseEntity.ok(consultationService.findById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ConsultationDto>> findByCustomer(
            @PathVariable Integer customerId) {
        return ResponseEntity.ok(consultationService.findByCustomer(customerId));
    }

    @PostMapping
    public ResponseEntity<ConsultationDto> insert(
            @Valid @RequestBody ConsultationDto dto) throws ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(consultationService.insert(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultationDto> update(
            @PathVariable Integer id,
            @Valid @RequestBody ConsultationDto dto) throws ServiceException {
        return ResponseEntity.ok(consultationService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(
            @PathVariable Integer id) throws ServiceException {
        consultationService.delete(id);
        return ResponseEntity.ok(
                Map.of("message", "Consulenza eliminata correttamente"));
    }
}
