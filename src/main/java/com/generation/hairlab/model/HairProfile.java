package com.generation.hairlab.model;

import java.util.Set;

import com.generation.hairlab.enums.HairCondition;
import com.generation.hairlab.enums.HairTexture;
import com.generation.hairlab.enums.HairType;
import com.generation.hairlab.enums.PhysicalValue;
import com.generation.hairlab.enums.Reflection;
import com.generation.hairlab.enums.ToneLevel;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

/**
 * Rappresenta la scheda tecnica corrente dei capelli di un cliente.
 *
 * Contiene caratteristiche utili alla diagnosi professionale, come tono,
 * riflesso, tipologia, texture, porosità e densità, oltre a informazioni
 * descrittive sulla cute, sulla storia chimica e sulle controindicazioni.
 */
@Entity
@Data
public class HairProfile {

    /** Identificativo univoco della scheda capelli. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Cliente proprietario della scheda capelli.
     *
     * La relazione è OneToOne perché la struttura del progetto prevede una sola
     * HairProfile corrente per ciascun Customer e ogni HairProfile appartiene a
     * un solo cliente.
     */
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /** Altezza di tono naturale dei capelli. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ToneLevel naturalTone;

    /** Altezza di tono attuale dei capelli. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ToneLevel currentTone;

    /** Riflesso cromatico attualmente rilevato sui capelli. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Reflection reflection;

    /** Tipologia naturale del capello: liscio, mosso, riccio o coily. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HairType hairType;

    /** Spessore/texture della fibra capillare. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HairTexture texture;

    /** Livello di porosità del capello. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PhysicalValue porosity;

    /** Livello di densità dei capelli. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PhysicalValue density;

    /** Livello di salute dei capelli. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HairCondition hairCondition;

    /**
     * Condizioni della cute rilevate sul cliente.
     *
     * Essendo un Set<String>, non può essere salvato in una singola colonna.
     * ElementCollection crea una tabella dedicata collegata alla HairProfile.
     */
    @ElementCollection
    @CollectionTable(
        name = "hair_profile_scalp_conditions",
        joinColumns = @JoinColumn(name = "hair_profile_id")
    )
    @Column(name = "scalp_condition", nullable = false)
    private Set<String> scalpCondition;

    /**
     * Storico dei principali trattamenti chimici dichiarati o rilevati.
     *
     * La collezione viene salvata in una tabella dedicata tramite
     * ElementCollection perché contiene più valori String.
     */
    @ElementCollection
    @CollectionTable(
        name = "hair_profile_chemical_history",
        joinColumns = @JoinColumn(name = "hair_profile_id")
    )
    @Column(name = "chemical_history", nullable = false)
    private Set<String> chemicalHistory;

    /**
     * Sensibilità dichiarate o rilevate.
     *
     * La relazione è modellata come ElementCollection perché si tratta di una
     * collezione di valori semplici e non di entità autonome.
     */
    @ElementCollection
    @CollectionTable(
        name = "hair_profile_sensitivities",
        joinColumns = @JoinColumn(name = "hair_profile_id")
    )
    @Column(name = "sensitivity", nullable = false)
    private Set<String> sensitivities;

    /**
     * Controindicazioni tecniche associate al profilo.
     *
     * Anche questa collezione viene salvata in una tabella separata perché un
     * singolo campo SQL non può contenere direttamente un Set<String> tramite
     * una normale annotazione @Column.
     */
    @ElementCollection
    @CollectionTable(
        name = "hair_profile_contraindications",
        joinColumns = @JoinColumn(name = "hair_profile_id")
    )
    @Column(name = "contraindication", nullable = false)
    private Set<String> contraindications;

    /** Note tecniche libere sulla scheda capelli. */
    private String notes;

   
}
