package com.generation.hairlab.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * Rappresenta un elemento che compone una formula colore.
 *
 * L'item appartiene a una ColorFormula e, secondo la struttura attualmente
 * scelta dal progetto, può contenere un insieme di HairDye associati a una
 * quantità e a eventuali note tecniche.
 */
@Entity
@Data
public class ColorFormulaItem {

    /** Identificativo univoco dell'elemento della formula. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Formula colore proprietaria dell'item.
     *
     * La relazione è ManyToOne perché una ColorFormula può contenere molti item,
     * mentre ogni ColorFormulaItem appartiene a una sola formula.
     */
    @ManyToOne
    @JoinColumn(name = "colorFormula_id")
    private ColorFormula colorFormula;

    /**
     * Insieme delle tinte associate all'item.
     *
     * Poiché il campo è un Set<HairDye>, la relazione viene mappata come
     * ManyToMany tramite una tabella ponte: uno stesso HairDye può essere
     * riutilizzato in più item e un item può contenere più HairDye.
     *
     * Questo corregge il precedente OneToMany con JoinColumn, che non descriveva
     * correttamente una collezione di entità riutilizzabili e avrebbe spostato la
     * chiave esterna direttamente sulla tabella HairDye.
     */
    @ManyToMany
    @JoinTable(
        name = "color_formula_item_hair_dye",
        joinColumns = @JoinColumn(name = "color_formula_item_id"),
        inverseJoinColumns = @JoinColumn(name = "hair_dye_id")
    )
    private Set<HairDye> hairDyes;

    /** Quantità prevista per l'elemento della formula. */
    @Column(nullable = false)
    private double quantity;

    /** Eventuali note relative all'elemento della formula. */
    private String notes;
}
