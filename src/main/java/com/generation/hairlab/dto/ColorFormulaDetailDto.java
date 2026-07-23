package com.generation.hairlab.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Dettaglio aggregato della formula.
 *
 * Oltre alla formula e agli ingredienti
 * contiene i totali calcolati dal backend.
 */
@Data
public class ColorFormulaDetailDto {

    private ColorFormulaDto formula;

    private List<ColorFormulaItemDto> ingredients =
        new ArrayList<>();

    /** Totale dei componenti colore in grammi. */
    private BigDecimal totalColorQuantity;

    /** Developer calcolato secondo il rapporto. */
    private BigDecimal developerQuantity;

    /** Totale miscela colore + developer. */
    private BigDecimal totalMixtureQuantity;
}
