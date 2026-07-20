package com.generation.hairlab.dto;

import com.generation.hairlab.enums.ProductType;
import com.generation.hairlab.enums.Reflection;
import com.generation.hairlab.enums.ToneLevel;

import lombok.Data;

/**
 * DTO utilizzato per trasferire i dati relativi a una tinta
 * o a un prodotto tecnico colore.
 *
 * Il codice commerciale originale viene mantenuto separato da ToneLevel
 * e Reflection perché i produttori possono utilizzare sistemi di codifica
 * differenti per identificare tono e riflessi.
 */
@Data
public class HairDyeDto {

    /** Identificativo univoco della tinta o del prodotto tecnico. */
    private Integer id;

    /** Riflesso cromatico principale. */
    private Reflection primaryReflection;

    /** Eventuale riflesso cromatico secondario. */
    private Reflection secondaryReflection;

    /** Marca produttrice. */
    private String brand;

    /** Nome commerciale del prodotto. */
    private String name;

    /** Codice commerciale univoco assegnato dal produttore. */
    private String code;

    /** Tipologia tecnica del prodotto. */
    private ProductType productType;

    /** Altezza di tono associata al prodotto, quando applicabile. */
    private ToneLevel toneLevel;

    /** Indica se il prodotto è attualmente attivo o utilizzabile. */
    private boolean active;
}
