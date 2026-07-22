package com.generation.hairlab.dto;

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

import lombok.Data;

/**
 * DTO del profilo morfologico del viso.
 *
 * La relazione con Customer viene rappresentata
 * attraverso customerId.
 */
@Data
public class FaceProfileDto {

    private Integer id;

    private Integer customerId;

    /*
     * FORMA GENERALE.
     */
    private FaceShape faceShape;

    /*
     * FRONTE.
     */
    private Level foreheadHeight;

    private Width foreheadWidth;

    private HairlineShape hairlineShape;

    /*
     * OCCHI.
     */
    private EyeShape eyeShape;

    private EyeOrientation eyeOrientation;

    private EyeSpacing eyeSpacing;

    private Size eyeSize;

    private EyeColor eyeColor;

    /**
     * Campione HEX reale utilizzato
     * come riferimento visivo.
     */
    private String eyeReferenceColor;

    private String eyeColorNotes;

    /*
     * SOPRACCIGLIA.
     */
    private EyebrowShape eyebrowShape;

    private Thickness eyebrowThickness;

    /*
     * NASO.
     */
    private Length noseLength;

    private Width noseWidth;

    private NoseProfile noseProfile;

    private NoseTip noseTip;

    /*
     * ZIGOMI.
     */
    private Width cheekboneWidth;

    private Level cheekboneProminence;

    /*
     * MASCELLA.
     */
    private Width jawWidth;

    private Level jawDefinition;

    private JawShape jawShape;

    /*
     * MENTO.
     */
    private ChinShape chinShape;

    private ChinProjection chinProjection;

    /*
     * BOCCA E LABBRA.
     */
    private Width mouthWidth;

    private LipFullness lipFullness;

    private LipBalance lipBalance;

    private LipShape lipShape;

    /*
     * NOTE.
     */
    private String notes;

    private String stylingGoals;

    /*
     * TIMESTAMP.
     */
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}