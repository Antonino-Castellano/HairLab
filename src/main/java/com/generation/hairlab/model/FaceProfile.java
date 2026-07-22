package com.generation.hairlab.model;

import java.time.LocalDateTime;

import com.generation.hairlab.enums.FaceProfileEnums.ChinProjection;
import com.generation.hairlab.enums.FaceProfileEnums.ChinShape;
import com.generation.hairlab.enums.FaceProfileEnums.EyeColor;
import com.generation.hairlab.enums.FaceProfileEnums.EyeOrientation;
import com.generation.hairlab.enums.FaceProfileEnums.EyeShape;
import com.generation.hairlab.enums.FaceProfileEnums.EyeSpacing;
import com.generation.hairlab.enums.FaceProfileEnums.EyebrowShape;
import com.generation.hairlab.enums.FaceProfileEnums.FaceShape;
import com.generation.hairlab.enums.FaceProfileEnums.HairlineShape;
import com.generation.hairlab.enums.FaceProfileEnums.JawShape;
import com.generation.hairlab.enums.FaceProfileEnums.Length;
import com.generation.hairlab.enums.FaceProfileEnums.Level;
import com.generation.hairlab.enums.FaceProfileEnums.LipBalance;
import com.generation.hairlab.enums.FaceProfileEnums.LipFullness;
import com.generation.hairlab.enums.FaceProfileEnums.LipShape;
import com.generation.hairlab.enums.FaceProfileEnums.NoseProfile;
import com.generation.hairlab.enums.FaceProfileEnums.NoseTip;
import com.generation.hairlab.enums.FaceProfileEnums.Size;
import com.generation.hairlab.enums.FaceProfileEnums.Thickness;
import com.generation.hairlab.enums.FaceProfileEnums.Width;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Profilo morfologico del viso di una cliente.
 *
 * Ogni cliente può possedere un solo FaceProfile.
 *
 * La classe contiene caratteristiche descrittive utili
 * alla consulenza estetica e hairstyling.
 *
 * Non rappresenta una diagnosi medica.
 */
@Entity
@Table(name = "face_profile")
@Data
public class FaceProfile {

    /**
     * Identificativo univoco.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Cliente proprietario del profilo.
     *
     * unique = true garantisce un solo FaceProfile
     * per ogni Customer.
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
     * FORMA GENERALE
     * ============================================================
     */

    @Enumerated(EnumType.STRING)
    private FaceShape faceShape;

    /*
     * ============================================================
     * FRONTE E ATTACCATURA
     * ============================================================
     */

    @Enumerated(EnumType.STRING)
    private Level foreheadHeight;

    @Enumerated(EnumType.STRING)
    private Width foreheadWidth;

    @Enumerated(EnumType.STRING)
    private HairlineShape hairlineShape;

    /*
     * ============================================================
     * OCCHI
     * ============================================================
     */

    @Enumerated(EnumType.STRING)
    private EyeShape eyeShape;

    @Enumerated(EnumType.STRING)
    private EyeOrientation eyeOrientation;

    @Enumerated(EnumType.STRING)
    private EyeSpacing eyeSpacing;

    @Enumerated(EnumType.STRING)
    private Size eyeSize;

    @Enumerated(EnumType.STRING)
    private EyeColor eyeColor;

    /**
     * Permette di descrivere colori complessi.
     *
     * Esempio:
     * "Verde-grigio con anello centrale ambrato".
     */
    @Column(length = 1000)
    private String eyeColorNotes;

    /*
     * ============================================================
     * SOPRACCIGLIA
     * ============================================================
     */

    @Enumerated(EnumType.STRING)
    private EyebrowShape eyebrowShape;

    @Enumerated(EnumType.STRING)
    private Thickness eyebrowThickness;

    /*
     * ============================================================
     * NASO
     * ============================================================
     */

    @Enumerated(EnumType.STRING)
    private Length noseLength;

    @Enumerated(EnumType.STRING)
    private Width noseWidth;

    @Enumerated(EnumType.STRING)
    private NoseProfile noseProfile;

    @Enumerated(EnumType.STRING)
    private NoseTip noseTip;

    /*
     * ============================================================
     * ZIGOMI
     * ============================================================
     */

    @Enumerated(EnumType.STRING)
    private Width cheekboneWidth;

    @Enumerated(EnumType.STRING)
    private Level cheekboneProminence;

    /*
     * ============================================================
     * MASCELLA
     * ============================================================
     */

    @Enumerated(EnumType.STRING)
    private Width jawWidth;

    @Enumerated(EnumType.STRING)
    private Level jawDefinition;

    @Enumerated(EnumType.STRING)
    private JawShape jawShape;

    /*
     * ============================================================
     * MENTO
     * ============================================================
     */

    @Enumerated(EnumType.STRING)
    private ChinShape chinShape;

    @Enumerated(EnumType.STRING)
    private ChinProjection chinProjection;

    /*
     * ============================================================
     * BOCCA E LABBRA
     * ============================================================
     */

    @Enumerated(EnumType.STRING)
    private Width mouthWidth;

    @Enumerated(EnumType.STRING)
    private LipFullness lipFullness;

    @Enumerated(EnumType.STRING)
    private LipBalance lipBalance;

    @Enumerated(EnumType.STRING)
    private LipShape lipShape;

    /*
     * ============================================================
     * NOTE
     * ============================================================
     */

    /**
     * Osservazioni libere del professionista.
     */
    @Column(length = 2000)
    private String notes;

    /**
     * Obiettivo estetico/stilistico espresso
     * durante la consulenza.
     *
     * Esempio:
     * "Valorizzare gli zigomi e ridurre visivamente
     * la verticalità del volto".
     */
    @Column(length = 2000)
    private String stylingGoals;

    /**
     * Data di creazione.
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * Data dell'ultima modifica.
     */
    private LocalDateTime updatedAt;
}