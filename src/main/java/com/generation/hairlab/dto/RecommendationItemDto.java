package com.generation.hairlab.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Singolo suggerimento prodotto
 * dal motore HairLab.
 */
@Data
public class RecommendationItemDto {

    /**
     * Titolo del suggerimento.
     *
     * Esempio:
     * "Long bob alla clavicola".
     */
    private String title;

    /**
     * Descrizione.
     */
    private String description;

    /**
     * Compatibilità secondo le regole HairLab.
     *
     * NON rappresenta una probabilità scientifica.
     *
     * Range:
     * 0 - 100.
     */
    private Integer compatibilityScore;

    /**
     * Motivazioni del suggerimento.
     */
    private List<String> reasons =
        new ArrayList<>();
}