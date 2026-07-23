package com.generation.hairlab.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.generation.hairlab.enums.InventoryUnit;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO dello stock corrente di un prodotto Color Lab.
 */
@Data
public class HairDyeInventoryDto {

    private Integer id;

    @NotNull(
        message = "Il prodotto tecnico è obbligatorio"
    )
    private Integer hairDyeId;

    @NotNull(
        message = "La quantità disponibile è obbligatoria"
    )
    @DecimalMin(
        value = "0.00",
        message = "La quantità disponibile non può essere negativa"
    )
    private BigDecimal quantityAvailable;

    @NotNull(
        message = "L'unità di misura è obbligatoria"
    )
    private InventoryUnit unit;

    @NotNull(
        message = "La soglia minima è obbligatoria"
    )
    @DecimalMin(
        value = "0.00",
        message = "La soglia minima non può essere negativa"
    )
    private BigDecimal lowStockThreshold;

    private LocalDateTime updatedAt;
}
