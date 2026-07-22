package com.generation.hairlab.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Risultato completo del motore
 * dei suggerimenti HairLab.
 */
@Data
public class StyleRecommendationDto {

    private Integer customerId;

    private LocalDateTime generatedAt;

    /**
     * Permettono al frontend di capire
     * quali profili erano disponibili.
     */
    private boolean hairProfileAvailable;

    private boolean faceProfileAvailable;

    private boolean colorAnalysisAvailable;

    /**
     * Tagli suggeriti.
     */
    private List<RecommendationItemDto>
        haircutRecommendations =
            new ArrayList<>();

    /**
     * Frange suggerite.
     */
    private List<RecommendationItemDto>
        fringeRecommendations =
            new ArrayList<>();

    /**
     * Colori capelli suggeriti.
     */
    private List<RecommendationItemDto>
        colorRecommendations =
            new ArrayList<>();

    /**
     * Styling suggerito.
     */
    private List<RecommendationItemDto>
        stylingRecommendations =
            new ArrayList<>();

    /**
     * Avvisi tecnici derivati soprattutto
     * da HairProfile.
     */
    private List<String> technicalWarnings =
        new ArrayList<>();

    /**
     * Note generali.
     */
    private List<String> generalNotes =
        new ArrayList<>();
}