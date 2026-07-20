package com.generation.hairlab.model;

import com.generation.hairlab.enums.ProductType;
import com.generation.hairlab.enums.Reflection;
import com.generation.hairlab.enums.ToneLevel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Rappresenta una tinta o un prodotto tecnico colore utilizzabile nelle formule.
 *
 * Contiene le informazioni identificative del prodotto, l'altezza di tono e gli
 * eventuali riflessi primario e secondario.
 */
@Entity
@Data
public class HairDye {

    /** Identificativo univoco della tinta. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Riflesso cromatico principale della tinta. */
    @Enumerated(EnumType.STRING)
    @Column(name = "primary_reflection")
    private Reflection primaryReflection;

    /** Eventuale riflesso cromatico secondario della tinta. */
    @Enumerated(EnumType.STRING)
    @Column(name = "secondary_reflection")
    private Reflection secondaryReflection;

    /** Marca produttrice. */
    @Column(nullable = false)
    private String brand;

    /** Nome commerciale del prodotto. */
    @Column(nullable = false)
    private String name;

    /** Codice commerciale univoco assegnato al prodotto. */
    @Column(nullable = false, unique = true)
    private String code;

    /** Tipologia tecnica del prodotto. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType productType;

    /** Altezza di tono associata al prodotto, quando applicabile. */
    @Enumerated(EnumType.STRING)
    private ToneLevel toneLevel;

    /** Indica se il prodotto è attualmente disponibile per l'utilizzo. */
    private boolean active;
}
