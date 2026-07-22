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
 * Analisi cromatica / armocromatica
 * associata a una cliente.
 */
@Entity
@Table(name = "color_analysis")
@Data
public class ColorAnalysis {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Integer id;

    /**
     * Cliente proprietario dell'analisi.
     */
    @OneToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
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

    /**
     * Classificazione generale
     * della profondità della pelle.
     */
    @Enumerated(EnumType.STRING)
    private SkinTone skinTone;

    /**
     * Colore HEX reale utilizzato
     * come riferimento della pelle
     * della cliente.
     *
     * Esempio:
     *
     * #C58A70
     */
    @Column(
        name = "skin_reference_color",
        length = 7
    )
    private String skinReferenceColor;

    /**
     * Temperatura / sottotono.
     */
    @Enumerated(EnumType.STRING)
    private Undertone undertone;

    /*
     * ============================================================
     * STAGIONE
     * ============================================================
     */

    @Enumerated(EnumType.STRING)
    private ColorSeason season;

    @Enumerated(EnumType.STRING)
    private ColorSubSeason subSeason;

    /*
     * ============================================================
     * PARAMETRI CROMATICI
     * ============================================================
     */

    @Enumerated(EnumType.STRING)
    @Column(name = "color_value")
    private ColorValue colorValue;

    @Enumerated(EnumType.STRING)
    private ContrastLevel contrastLevel;

    @Enumerated(EnumType.STRING)
    private Chroma chroma;

    /*
     * ============================================================
     * COLORI CONSIGLIATI
     * ============================================================
     */

    @ElementCollection
    @CollectionTable(
        name = "color_analysis_best_colors",
        joinColumns =
            @JoinColumn(
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
    private Map<String, String>
        bestColors =
            new HashMap<>();

    /*
     * ============================================================
     * COLORI MENO ARMONICI
     * ============================================================
     */

    @ElementCollection
    @CollectionTable(
        name = "color_analysis_avoid_colors",
        joinColumns =
            @JoinColumn(
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
    private Map<String, String>
        avoidColors =
            new HashMap<>();

    /*
     * ============================================================
     * METALLI
     * ============================================================
     */

    @ElementCollection
    @CollectionTable(
        name = "color_analysis_metals",
        joinColumns =
            @JoinColumn(
                name = "color_analysis_id"
            )
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "metal_type")
    private Set<MetalType>
        bestMetals =
            new HashSet<>();

    /*
     * ============================================================
     * NOTE
     * ============================================================
     */

    @Column(length = 2000)
    private String notes;

    /*
     * ============================================================
     * TIMESTAMP
     * ============================================================
     */

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}