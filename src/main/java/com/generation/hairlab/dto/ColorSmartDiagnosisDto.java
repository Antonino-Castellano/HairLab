package com.generation.hairlab.dto;

import java.util.ArrayList;
import java.util.List;

import com.generation.hairlab.enums.ColorApplicationType;
import com.generation.hairlab.enums.ColorDiagnosisFeasibility;
import com.generation.hairlab.enums.ColorDiagnosisStrategy;
import com.generation.hairlab.enums.HairCondition;
import com.generation.hairlab.enums.HairLength;
import com.generation.hairlab.enums.HairTexture;
import com.generation.hairlab.enums.PhysicalValue;
import com.generation.hairlab.enums.Reflection;
import com.generation.hairlab.enums.ToneLevel;

import lombok.Data;

/**
 * Risultato della diagnosi tecnica preliminare.
 *
 * Il DTO è volutamente "spiegabile":
 * oltre al codice tecnico restituisce
 * motivazioni e warning leggibili.
 */
@Data
public class ColorSmartDiagnosisDto {

    private Integer customerId;

    private Integer hairProfileId;

    /*
     * ========================================================
     * BASE CLIENTE
     * ========================================================
     */

    private ToneLevel naturalTone;

    private ToneLevel currentTone;

    private Reflection currentReflection;

    private HairLength hairLength;

    private HairTexture texture;

    private PhysicalValue density;

    private PhysicalValue porosity;

    private HairCondition hairCondition;

    /*
     * ========================================================
     * OBIETTIVO
     * ========================================================
     */

    private ToneLevel targetToneLevel;

    private Reflection targetPrimaryReflection;

    private Reflection targetSecondaryReflection;

    private ColorApplicationType applicationType;

    private String targetResult;

    /*
     * ========================================================
     * ANALISI
     * ========================================================
     */

    /**
     * Positivo = schiarire.
     * Negativo = scurire.
     *
     * Esempio:
     * base 5 -> target 8 = +3
     */
    private Integer toneDifference;

    private ColorDiagnosisFeasibility feasibility;

    private ColorDiagnosisStrategy strategy;

    /**
     * Famiglie riflesso utili come direzione tecnica.
     *
     * NON sono ancora una formula automatica.
     */
    private List<Reflection> recommendedReflectionFamilies =
        new ArrayList<>();

    /**
     * Spiegazioni delle decisioni prese
     * dal motore deterministico.
     */
    private List<String> reasons =
        new ArrayList<>();

    /**
     * Warning tecnici e dati mancanti.
     */
    private List<String> warnings =
        new ArrayList<>();

    /**
     * TRUE quando la fase successiva
     * potrà tentare una proposta automatica
     * senza prima richiedere revisione manuale.
     */
    private boolean automaticFormulaCandidate;

    /**
     * Suggerimento prudenziale
     * per prova ciocca / verifica preventiva.
     */
    private boolean strandTestRecommended;
}
