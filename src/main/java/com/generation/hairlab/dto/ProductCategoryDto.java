package com.generation.hairlab.dto;

import lombok.Data;

/**
 * DTO utilizzato per trasferire i dati relativi a una categoria
 * del catalogo dei servizi del salone.
 *
 * Esempi di categorie possono essere TAGLIO, COLORE, STYLING
 * o TRATTAMENTI.
 */
@Data
public class ProductCategoryDto {

    /** Identificativo univoco della categoria. */
    private Integer id;

    /** Nome della categoria. */
    private String name;

    /** Descrizione della categoria. */
    private String desc;

    /** Indica se la categoria è attualmente utilizzabile. */
    private boolean active;
}
