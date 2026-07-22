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
 * DTO dell'analisi cromatica della cliente.
 */
@Data
public class ColorAnalysisDto {

    private Integer id;

    private Integer customerId;

    private SkinTone skinTone;
    private Undertone undertone;

    private ColorSeason season;
    private ColorSubSeason subSeason;

    private ColorValue colorValue;
    private ContrastLevel contrastLevel;
    private Chroma chroma;

    /**
     * Nome colore -> codice HEX.
     */
    private Map<String, String> bestColors;

    /**
     * Nome colore -> codice HEX.
     */
    private Map<String, String> avoidColors;

    private Set<MetalType> bestMetals;

    private String notes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}