package com.generation.hairlab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.hairlab.service.CustomerAnalysisService;
import com.generation.hairlab.service.ServiceException;

import lombok.RequiredArgsConstructor;

/**
 * Controller REST dedicato alla scheda
 * tecnica completa di una cliente.
 *
 * Questo Controller è solamente di lettura.
 *
 * Le modifiche dei singoli profili continuano
 * a essere gestite dai rispettivi Controller:
 *
 * HairProfileController
 * FaceProfileController
 * ColorAnalysisController
 */
@RestController
@RequestMapping("/hairlab/api/customer-analysis")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerAnalysisController {

    private final CustomerAnalysisService
        customerAnalysisService;

    /**
     * Restituisce la scheda completa
     * di una cliente.
     *
     * Esempio:
     *
     * GET
     * /hairlab/api/customer-analysis/customer/1
     */
    @GetMapping(
        "/customer/{customerId}"
    )
    public ResponseEntity<?> findByCustomerId(
            @PathVariable Integer customerId) {

        try {

            return ResponseEntity.ok(
                customerAnalysisService
                    .findByCustomerId(
                        customerId
                    )
            );

        } catch (ServiceException e) {

            return ResponseEntity
                .badRequest()
                .body(
                    e.toMap(
                        "cannot build customer analysis"
                    )
                );
        }
    }
}