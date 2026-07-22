package com.generation.hairlab.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.generation.hairlab.enums.ColorAnalysisEnums.Chroma;
import com.generation.hairlab.enums.ColorAnalysisEnums.ColorSeason;
import com.generation.hairlab.enums.ColorAnalysisEnums.ColorSubSeason;
import com.generation.hairlab.enums.ColorAnalysisEnums.ColorValue;
import com.generation.hairlab.enums.ColorAnalysisEnums.ContrastLevel;
import com.generation.hairlab.enums.ColorAnalysisEnums.MetalType;
import com.generation.hairlab.enums.ColorAnalysisEnums.SkinTone;
import com.generation.hairlab.enums.ColorAnalysisEnums.Undertone;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Analisi cromatica e armocromatica della cliente.
 *
 * Non duplica informazioni già presenti in:
 *
 * - HairProfile per il colore naturale dei capelli;
 * - FaceProfile per il colore degli occhi.
 *
 * StyleRecommendationService combinerà tutti i profili.
 */
@Entity
@Table(name = "color_analysis")
@Data
public class ColorAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Cliente proprietario dell'analisi.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "customer_id",
        nullable = false,
        unique = true
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Customer customer;

    /*
     * ============================================================
     * PELLE
     * ============================================================
     */

    @Enumerated(EnumType.STRING)
    private SkinTone skinTone;

    @Enumerated(EnumType.STRING)
    private Undertone undertone;

    /*
     * ============================================================
     * ARMOCROMIA
     * ============================================================
     */

    @Enumerated(EnumType.STRING)
    private ColorSeason season;

    @Enumerated(EnumType.STRING)
    private ColorSubSeason subSeason;

    /**
     * Valore o profondità cromatica.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "color_value")
    private ColorValue colorValue;

    /**
     * Contrasto naturale.
     */
    @Enumerated(EnumType.STRING)
    private ContrastLevel contrastLevel;

    /**
     * Intensità cromatica.
     */
    @Enumerated(EnumType.STRING)
    private Chroma chroma;

    /*
     * ============================================================
     * PALETTE CONSIGLIATA
     * ============================================================
     *
     * Utilizziamo Map<String, String>:
     *
     * Nome colore -> HEX
     *
     * Esempio:
     *
     * "Borgogna" -> "#6D213C"
     */

    @ElementCollection
    @CollectionTable(
        name = "color_analysis_best_colors",
        joinColumns = @JoinColumn(
            name = "color_analysis_id"
        )
    )
    @MapKeyColumn(
        name = "color_name",
        length = 100
    )
    @Column(
        name = "hex_code",
        length = 7
    )
    private Map<String, String> bestColors =
        new HashMap<>();

    /**
     * Colori generalmente meno armonici.
     */
    @ElementCollection
    @CollectionTable(
        name = "color_analysis_avoid_colors",
        joinColumns = @JoinColumn(
            name = "color_analysis_id"
        )
    )
    @MapKeyColumn(
        name = "color_name",
        length = 100
    )
    @Column(
        name = "hex_code",
        length = 7
    )
    private Map<String, String> avoidColors =
        new HashMap<>();

    /**
     * Metalli consigliati.
     */
    @ElementCollection
    @CollectionTable(
        name = "color_analysis_metals",
        joinColumns = @JoinColumn(
            name = "color_analysis_id"
        )
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "metal_type")
    private Set<MetalType> bestMetals =
        new HashSet<>();

    /**
     * Note libere del professionista.
     */
    @Column(length = 2000)
    private String notes;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}