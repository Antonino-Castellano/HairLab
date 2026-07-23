package com.generation.hairlab.model;

import java.math.BigDecimal;

import com.generation.hairlab.enums.InventoryUnit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Singolo ingrediente di una ColorFormula.
 *
 * REGOLA STRUTTURALE:
 *
 * un item = un solo HairDye/prodotto tecnico
 *         + una quantità precisa
 *         + una unità di misura.
 *
 * Questo elimina l'ambiguità della precedente relazione:
 *
 * Set<HairDye> + una sola quantity.
 */
@Entity
@Data
public class ColorFormulaItem {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "color_formula_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ColorFormula colorFormula;

    /**
     * Un solo ingrediente per riga formula.
     *
     * Il campo DB resta inizialmente nullable
     * per permettere l'avvio su database legacy
     * che possiedono ancora la vecchia tabella ponte.
     *
     * Il Service lo rende obbligatorio
     * per tutti i nuovi item.
     */
    @ManyToOne
    @JoinColumn(name = "hair_dye_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private HairDye hairDye;

    /**
     * Quantità precisa dell'ingrediente.
     *
     * BigDecimal evita gli errori tipici
     * dei numeri floating point.
     */
    @Column(
        nullable = false,
        precision = 10,
        scale = 2
    )
    private BigDecimal quantity;

    /**
     * Unità con cui la quantità è espressa.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "unit")
    private InventoryUnit unit;

    /** Note tecniche dell'ingrediente. */
    private String notes;
}
