package com.generation.hairlab.dto;

import java.time.LocalDateTime;
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

import lombok.Data;

/**
 * DTO dell'analisi cromatica.
 */
@Data
public class ColorAnalysisDto {

    private Integer id;

    private Integer customerId;

    /*
     * PELLE.
     */
    private SkinTone skinTone;

    /**
     * Campione HEX reale
     * della pelle della cliente.
     */
    private String skinReferenceColor;

    private Undertone undertone;

    /*
     * STAGIONE.
     */
    private ColorSeason season;

    private ColorSubSeason subSeason;

    /*
     * PARAMETRI.
     */
    private ColorValue colorValue;

    private ContrastLevel contrastLevel;

    private Chroma chroma;

    /*
     * PALETTE.
     */
    private Map<String, String>
        bestColors;

    private Map<String, String>
        avoidColors;

    private Set<MetalType>
        bestMetals;

    /*
     * NOTE.
     */
    private String notes;

    /*
     * TIMESTAMP.
     */
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}