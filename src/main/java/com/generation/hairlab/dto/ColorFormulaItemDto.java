package com.generation.hairlab.dto;

import java.util.Set;

import lombok.Data;

/**
 * DTO utilizzato per trasferire un elemento appartenente
 * a una formula colore.
 *
 * La relazione con ColorFormula viene rappresentata tramite colorFormulaId.
 * Le tinte associate vengono rappresentate tramite un insieme di ID
 * HairDye invece di trasferire direttamente le Entity.
 *
 * Questa struttura rispecchia il model attuale, nel quale un
 * ColorFormulaItem contiene un Set di HairDye.
 */
@Data
public class ColorFormulaItemDto {

    /** Identificativo univoco dell'elemento della formula. */
    private Integer id;

    /** Identificativo della formula a cui appartiene l'elemento. */
    private Integer colorFormulaId;

    /** Identificativi delle tinte associate all'elemento della formula. */
    private Set<Integer> hairDyeIds;

    /** Quantità prevista per l'elemento della formula. */
    private double quantity;

    /** Eventuali note tecniche relative all'elemento. */
    private String notes;
}
