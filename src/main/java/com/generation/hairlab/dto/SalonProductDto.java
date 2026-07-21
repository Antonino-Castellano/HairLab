package com.generation.hairlab.dto;

import lombok.Data;

/**
 * DTO utilizzato per trasferire i dati relativi a un servizio
 * o prodotto presente nel listino del salone.
 *
 * La relazione ManyToOne con ProductCategory viene rappresentata tramite
 * productCategoryId invece di trasferire direttamente la Entity.
 *
 * Il Service utilizzerà l'identificativo per recuperare la categoria
 * dal database e associarla al SalonProduct.
 */
@Data
public class SalonProductDto {

    /** Identificativo univoco del servizio o prodotto. */
    private Integer id;

    /** Identificativo della categoria a cui appartiene il servizio. */
    private Integer productCategoryId;

    /** Nome del servizio o prodotto. */
    private String name;

    /** Descrizione del servizio o prodotto. */
    private String desc;

    /** Durata prevista del servizio espressa in minuti. */
    private int duration;

    /** Prezzo base del servizio. */
    private double basePrice;

    /** Indica se il servizio o prodotto è attualmente attivo. */
    private boolean active;
}
