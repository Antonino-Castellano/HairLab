package com.generation.hairlab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.hairlab.dto.ColorSmartDiagnosisDto;
import com.generation.hairlab.dto.ColorSmartDiagnosisRequestDto;
import com.generation.hairlab.service.ColorSmartDiagnosisService;
import com.generation.hairlab.service.ServiceException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Endpoint Smart Formula - Fase diagnostica.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(
    "/hairlab/api/color-smart-diagnosis"
)
public class ColorSmartDiagnosisController {

    private final ColorSmartDiagnosisService
        diagnosisService;

    /**
     * Analizza base cliente + obiettivo.
     */
    @PostMapping("/analyze")
    public ResponseEntity<ColorSmartDiagnosisDto>
        analyze(
            @Valid
            @RequestBody
            ColorSmartDiagnosisRequestDto request)
            throws ServiceException {

        return ResponseEntity.ok(
            diagnosisService.analyze(
                request
            )
        );
    }
}
