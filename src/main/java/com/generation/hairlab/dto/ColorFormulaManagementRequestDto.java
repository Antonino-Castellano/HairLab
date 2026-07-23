package com.generation.hairlab.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.generation.hairlab.enums.ColorApplicationType;
import com.generation.hairlab.enums.ColorFormulaStatus;
import com.generation.hairlab.enums.MixingRatio;
import com.generation.hairlab.enums.Oxygen;
import com.generation.hairlab.enums.Reflection;
import com.generation.hairlab.enums.ToneLevel;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Richiesta aggregata del Formula Builder.
 *
 * Salva atomicamente:
 *
 * ColorFormula
 * +
 * tutti i ColorFormulaItem.
 *
 * La formula resta sempre collegata
 * direttamente al Customer.
 */
@Data
public class ColorFormulaManagementRequestDto {

    @NotNull(
        message = "Il cliente è obbligatorio"
    )
    private Integer customerId;

    private Integer consultationId;

    private Integer appointmentItemId;

    @NotBlank(
        message = "Il nome della formula è obbligatorio"
    )
    private String name;

    @NotBlank(
        message = "L'obiettivo colore è obbligatorio"
    )
    private String targetResult;

    private ToneLevel targetToneLevel;

    private Reflection targetPrimaryReflection;

    private Reflection targetSecondaryReflection;

    @NotNull(
        message = "Il tipo di applicazione è obbligatorio"
    )
    private ColorApplicationType applicationType;

    @NotNull(
        message = "Il volume developer è obbligatorio"
    )
    private Oxygen volumeDeveloper;

    @NotNull(
        message = "Il rapporto di miscelazione è obbligatorio"
    )
    private MixingRatio mixingRatio;

    /**
     * Moltiplicatore custom.
     *
     * Esempio:
     * 1 : 1.8
     * -> 1.80
     */
    @DecimalMin(
        value = "0.01",
        message = "Il rapporto custom deve essere maggiore di zero"
    )
    private BigDecimal customDeveloperRatio;

    @NotNull(
        message = "Lo stato della formula è obbligatorio"
    )
    private ColorFormulaStatus status;

    private String notes;

    @NotEmpty(
        message = "Inserisci almeno un ingrediente"
    )
    @Valid
    private List<ColorFormulaIngredientRequestDto> ingredients =
        new ArrayList<>();
}
