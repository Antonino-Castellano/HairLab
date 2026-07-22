package com.generation.hairlab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.hairlab.service.ServiceException;
import com.generation.hairlab.service.StyleRecommendationService;

import lombok.RequiredArgsConstructor;

/**
 * Controller dedicato al motore
 * dei suggerimenti HairLab.
 */
@RestController
@RequestMapping(
    "/hairlab/api/style-recommendation"
)
@RequiredArgsConstructor
public class StyleRecommendationController {

    private final StyleRecommendationService
        styleRecommendationService;

    /**
     * Genera dinamicamente i suggerimenti
     * per un cliente.
     *
     * GET:
     *
     * /hairlab/api/style-recommendation/customer/1
     */
    @GetMapping(
        "/customer/{customerId}"
    )
    public ResponseEntity<?> generate(
            @PathVariable Integer customerId) {

        try {

            return ResponseEntity.ok(
                styleRecommendationService
                    .generate(customerId)
            );

        } catch (ServiceException e) {

            return ResponseEntity
                .badRequest()
                .body(
                    e.toMap(
                        "cannot generate style recommendations"
                    )
                );
        }
    }
}