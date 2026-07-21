package com.generation.hairlab.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Rappresenta una categoria utilizzata per organizzare i servizi del salone.
 *
 * Esempi possibili sono taglio, colore, styling o trattamenti.
 */
@Entity
@Data
public class ProductCategory {

    /** Identificativo univoco della categoria. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Nome univoco della categoria. */
    @Column(unique = true, nullable = false)
    private String name;

    /** Descrizione libera della categoria. */
    private String description;

    /** Indica se la categoria è attualmente utilizzabile. */
    private boolean active;
}
