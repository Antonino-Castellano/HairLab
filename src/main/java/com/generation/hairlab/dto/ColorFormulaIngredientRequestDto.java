package com.generation.hairlab.dto;

import java.math.BigDecimal;

import com.generation.hairlab.enums.InventoryUnit;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Ingrediente inviato dal Formula Builder.
 *
 * Nel Blocco 3 il Builder lavora in grammatura:
 *
 * un prodotto tecnico
 * +
 * una quantità precisa.
 */
@Data
public class ColorFormulaIngredientRequestDto {

    @NotNull(
        message = "Il prodotto tecnico è obbligatorio"
    )
    private Integer hairDyeId;

    @NotNull(
        message = "La quantità è obbligatoria"
    )
    @DecimalMin(
        value = "0.01",
        message = "La quantità deve essere maggiore di zero"
    )
    private BigDecimal quantity;

    /**
     * Per il Formula Builder manuale
     * usiamo normalmente GRAM.
     *
     * Il campo resta esplicito
     * per mantenere il dominio estendibile.
     */
    @NotNull(
        message = "L'unità di misura è obbligatoria"
    )
    private InventoryUnit unit;

    private String notes;
}
