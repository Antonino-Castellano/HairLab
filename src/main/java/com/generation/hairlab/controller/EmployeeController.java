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

import com.generation.hairlab.dto.EmployeeDto;
import com.generation.hairlab.service.EmployeeService;
import com.generation.hairlab.service.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/** Controller REST dedicato ai dipendenti del salone. */
@RestController
@RequiredArgsConstructor
@RequestMapping("/hairlab/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<EmployeeDto>> findActive() {
        return ResponseEntity.ok(employeeService.findActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> findById(@PathVariable Integer id) throws ServiceException {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> insert(@Valid @RequestBody EmployeeDto dto) throws ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.insert(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable Integer id, @Valid @RequestBody EmployeeDto dto) throws ServiceException {
        return ResponseEntity.ok(employeeService.update(id, dto));
    }

    /** Eliminazione/Disattivazione logica del dipendente. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Integer id) throws ServiceException {
        employeeService.delete(id);
        return ResponseEntity.ok(Map.of("message", "Dipendente eliminato correttamente"));
    }

    /** Riattiva un dipendente disattivato. */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<EmployeeDto> activate(@PathVariable Integer id) throws ServiceException {
        return ResponseEntity.ok(employeeService.activate(id));
    }

    /** Disattiva un dipendente attivo. */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<EmployeeDto> deactivate(@PathVariable Integer id) throws ServiceException {
        return ResponseEntity.ok(employeeService.deactivate(id));
    }
}