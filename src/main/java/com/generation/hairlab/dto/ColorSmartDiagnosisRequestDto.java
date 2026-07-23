package com.generation.hairlab.dto;

import com.generation.hairlab.enums.ColorApplicationType;
import com.generation.hairlab.enums.Reflection;
import com.generation.hairlab.enums.ToneLevel;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Input del motore diagnostico Color Lab.
 *
 * La base di partenza NON viene inviata dal frontend:
 * viene letta direttamente dalla HairProfile del Customer,
 * così il motore usa sempre i dati tecnici salvati.
 */
@Data
public class ColorSmartDiagnosisRequestDto {

    @NotNull(
        message = "Il cliente è obbligatorio"
    )
    private Integer customerId;

    @NotNull(
        message = "Il tono target è obbligatorio"
    )
    private ToneLevel targetToneLevel;

    @NotNull(
        message = "Il riflesso target principale è obbligatorio"
    )
    private Reflection targetPrimaryReflection;

    private Reflection targetSecondaryReflection;

    @NotNull(
        message = "Il tipo di applicazione è obbligatorio"
    )
    private ColorApplicationType applicationType;

    /**
     * Testo libero dell'obiettivo.
     *
     * Verrà riutilizzato come targetResult
     * nel Formula Builder.
     */
    private String targetResult;
}
