package com.generation.hairlab.dto;

import java.util.Set;

import com.generation.hairlab.enums.HairCondition;
import com.generation.hairlab.enums.HairTexture;
import com.generation.hairlab.enums.HairType;
import com.generation.hairlab.enums.PhysicalValue;
import com.generation.hairlab.enums.Reflection;
import com.generation.hairlab.enums.ToneLevel;

import lombok.Data;

/**
 * DTO utilizzato per trasferire la scheda tecnica corrente dei capelli
 * associata a un cliente.
 *
 * La relazione OneToOne con Customer viene rappresentata tramite customerId.
 * Sarà il Service a utilizzare tale identificativo per recuperare la Entity
 * Customer e costruire la relazione prima del salvataggio.
 */
@Data
public class HairProfileDto {

    /** Identificativo univoco della scheda capelli. */
    private Integer id;

    /**
     * Identificativo del cliente proprietario della scheda.
     *
     * Sostituisce il riferimento diretto alla Entity Customer.
     */
    private Integer customerId;

    /** Altezza di tono naturale dei capelli. */
    private ToneLevel naturalTone;

    /** Altezza di tono attuale dei capelli. */
    private ToneLevel currentTone;

    /** Riflesso cromatico attualmente rilevato. */
    private Reflection reflection;

    /** Tipologia naturale del capello. */
    private HairType hairType;

    /** Spessore o texture della fibra capillare. */
    private HairTexture texture;

    /** Livello di porosità del capello. */
    private PhysicalValue porosity;

    /** Livello di densità dei capelli. */
    private PhysicalValue density;

    /** Livello di salute dei capelli. */
    private HairCondition hairCondition;

    /** Condizioni della cute rilevate o dichiarate. */
    private Set<String> scalpCondition;

    /** Storico dei principali trattamenti chimici. */
    private Set<String> chemicalHistory;

    /** Eventuali sensibilità rilevate o dichiarate. */
    private Set<String> sensitivities;

    /** Eventuali controindicazioni tecniche. */
    private Set<String> contraindications;

    /** Note tecniche libere relative alla scheda capelli. */
    private String notes;
}
