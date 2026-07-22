package com.generation.hairlab.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * DTO aggregato contenente l'intera analisi
 * di una cliente HairLab.
 *
 * Permette al frontend di recuperare con
 * una sola richiesta:
 *
 * - dati cliente;
 * - profilo capelli;
 * - profilo viso;
 * - analisi cromatica;
 * - suggerimenti HairLab.
 *
 * I profili possono essere null se non sono
 * ancora stati compilati.
 */
@Data
public class CustomerAnalysisDto {

    /**
     * Dati anagrafici della cliente.
     */
    private CustomerDto customer;

    /**
     * Profilo tecnico dei capelli.
     *
     * Può essere null.
     */
    private HairProfileDto hairProfile;

    /**
     * Profilo morfologico del viso.
     *
     * Può essere null.
     */
    private FaceProfileDto faceProfile;

    /**
     * Analisi cromatica / armocromia.
     *
     * Può essere null.
     */
    private ColorAnalysisDto colorAnalysis;

    /**
     * Suggerimenti generati dinamicamente
     * sulla base dei profili disponibili.
     */
    private StyleRecommendationDto recommendations;

    /**
     * true quando tutti e tre i profili
     * tecnici principali sono presenti.
     */
    private boolean completeProfile;

    /**
     * Sezioni ancora mancanti.
     *
     * Il frontend può usare questa lista
     * per mostrare:
     *
     * "Completa il profilo del viso"
     *
     * oppure:
     *
     * "Aggiungi analisi cromatica".
     */
    private List<String> missingSections =
        new ArrayList<>();
}