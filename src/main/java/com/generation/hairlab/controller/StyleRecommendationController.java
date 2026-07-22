package com.generation.hairlab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.hairlab.dto.StyleRecommendationDto;
import com.generation.hairlab.service.ServiceException;
import com.generation.hairlab.service.StyleRecommendationService;

import lombok.RequiredArgsConstructor;

/** Controller REST dedicato ai suggerimenti professionali HairLab. */
@RestController
@RequestMapping("/hairlab/api/style-recommendation")
@RequiredArgsConstructor
public class StyleRecommendationController {

    private final StyleRecommendationService styleRecommendationService;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<StyleRecommendationDto> generate(
            @PathVariable Integer customerId) throws ServiceException {
        return ResponseEntity.ok(
                styleRecommendationService.generate(customerId));
    }
}
