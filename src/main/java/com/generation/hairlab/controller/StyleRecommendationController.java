package com.generation.hairlab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.hairlab.service.ServiceException;
import com.generation.hairlab.service.StyleRecommendationService;

import lombok.RequiredArgsConstructor;

/**
 * Controller REST dedicato al motore
 * dei suggerimenti professionali HairLab.
 *
 * I suggerimenti non vengono salvati nel database:
 * vengono generati dinamicamente ogni volta
 * che il frontend effettua la richiesta.
 *
 * Il motore combina:
 *
 * - HairProfile;
 * - FaceProfile;
 * - ColorAnalysis.
 */
@RestController
@RequestMapping(
    "/hairlab/api/style-recommendation"
)
@RequiredArgsConstructor

/*
 * Permette al frontend Angular,
 * eseguito su localhost:4200,
 * di chiamare questo Controller.
 *
 * È necessario soprattutto perché
 * le richieste protette contengono
 * l'header Authorization con il JWT.
 *
 * Il browser esegue quindi prima
 * una richiesta OPTIONS di preflight CORS.
 */
@CrossOrigin(
    origins = "http://localhost:4200"
)
public class StyleRecommendationController {

    /**
     * Service che contiene il motore
     * di generazione dei suggerimenti.
     */
    private final StyleRecommendationService
        styleRecommendationService;

    /**
     * Genera dinamicamente i suggerimenti
     * per una specifica cliente.
     *
     * Esempio:
     *
     * GET
     * /hairlab/api/style-recommendation/customer/1
     *
     * @param customerId ID della cliente
     * @return suggerimenti HairLab
     */
    @GetMapping(
        "/customer/{customerId}"
    )
    public ResponseEntity<?> generate(
            @PathVariable Integer customerId) {

        try {

            return ResponseEntity.ok(
                styleRecommendationService
                    .generate(
                        customerId
                    )
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