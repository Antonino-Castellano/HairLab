package com.generation.hairlab.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * Rappresenta un servizio o prodotto del listino offerto dal salone.
 *
 * Ogni elemento appartiene a una categoria e contiene nome, descrizione,
 * durata prevista, prezzo base e stato di attivazione.
 */
@Entity
@Data
public class SalonProduct {

    /** Identificativo univoco del servizio/prodotto del salone. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Categoria alla quale appartiene il servizio.
     *
     * La relazione è ManyToOne perché una ProductCategory può raggruppare molti
     * SalonProduct, mentre ogni SalonProduct appartiene a una sola categoria.
     * Il precedente OneToMany era incompatibile con un campo singolo di tipo
     * ProductCategory.
     */
    @ManyToOne
    @JoinColumn(name = "productCategory_id")
    private ProductCategory productCategory;

    /** Nome univoco del servizio/prodotto. */
    @Column(nullable = false, unique = true)
    private String name;

    /** Descrizione libera del servizio/prodotto. */
    private String description;

    /** Durata prevista del servizio, espressa in minuti. */
    @Column(nullable = false)
    private int duration;

    /** Prezzo base del servizio. */
    private double basePrice;

    /** Indica se il servizio/prodotto è attualmente attivo. */
    private boolean active;
}
