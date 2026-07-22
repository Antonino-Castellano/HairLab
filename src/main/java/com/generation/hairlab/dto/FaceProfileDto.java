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
 */
@Data
public class FaceProfileDto {

    private Integer id;

    /**
     * Nel DTO rappresentiamo Customer
     * solamente attraverso il suo ID.
     */
    private Integer customerId;

    private FaceShape faceShape;

    private Level foreheadHeight;
    private Width foreheadWidth;
    private HairlineShape hairlineShape;

    private EyeShape eyeShape;
    private EyeOrientation eyeOrientation;
    private EyeSpacing eyeSpacing;
    private Size eyeSize;
    private EyeColor eyeColor;
    private String eyeColorNotes;

    private EyebrowShape eyebrowShape;
    private Thickness eyebrowThickness;

    private Length noseLength;
    private Width noseWidth;
    private NoseProfile noseProfile;
    private NoseTip noseTip;

    private Width cheekboneWidth;
    private Level cheekboneProminence;

    private Width jawWidth;
    private Level jawDefinition;
    private JawShape jawShape;

    private ChinShape chinShape;
    private ChinProjection chinProjection;

    private Width mouthWidth;
    private LipFullness lipFullness;
    private LipBalance lipBalance;
    private LipShape lipShape;

    private String notes;
    private String stylingGoals;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}