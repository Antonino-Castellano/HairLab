package com.generation.hairlab.model;

import java.util.Set;

import com.generation.hairlab.enums.HairCondition;
import com.generation.hairlab.enums.HairLength;
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
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Rappresenta la scheda tecnica corrente
 * dei capelli di una cliente.
 *
 * Ogni HairProfile appartiene a un solo Customer
 * e ogni Customer può possedere una sola HairProfile.
 */
@Entity
@Data
public class HairProfile {

    /**
     * Identificativo univoco della scheda.
     */
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Integer id;

    /**
     * Cliente proprietario della scheda capelli.
     *
     * nullable = false:
     * una HairProfile deve sempre appartenere a un cliente.
     *
     * unique = true:
     * impedisce anche a livello database
     * la creazione di due HairProfile
     * per lo stesso Customer.
     *
     * Le esclusioni Lombok evitano ricorsioni
     * durante toString(), equals() e hashCode().
     */
    @OneToOne
    @JoinColumn(
        name = "customer_id",
        nullable = false,
        unique = true
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Customer customer;

    /**
     * Altezza di tono naturale.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ToneLevel naturalTone;

    /**
     * Altezza di tono attuale.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ToneLevel currentTone;

    /**
     * Riflesso cromatico attuale.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Reflection reflection;

    /**
     * Tipologia naturale:
     *
     * STRAIGHT
     * WAVY
     * CURLY
     * COILY
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HairType hairType;

    /**
     * Texture/spessore della fibra.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HairTexture texture;

    /**
     * Lunghezza tecnica dei capelli.
     *
     * Il campo viene introdotto come nullable
     * per permettere la migrazione sicura dei profili
     * già presenti nel database.
     *
     * I nuovi flussi Color Lab lo utilizzeranno
     * insieme a densità e spessore per stimare
     * la quantità di prodotto necessaria.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "hair_length")
    private HairLength hairLength;

    /**
     * Porosità.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PhysicalValue porosity;

    /**
     * Densità.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PhysicalValue density;

    /**
     * Condizione generale del capello.
     *
     * Il frontend considera questo campo obbligatorio,
     * quindi rendiamo coerente anche il database.
     */
    @Enumerated(EnumType.STRING)
    @Column(
        name = "hair_condition",
        nullable = false
    )
    private HairCondition hairCondition;

    /**
     * Condizioni della cute.
     *
     * Trattandosi di più String,
     * JPA utilizza una tabella dedicata.
     */
    @ElementCollection
    @CollectionTable(
        name = "hair_profile_scalp_conditions",
        joinColumns = @JoinColumn(
            name = "hair_profile_id"
        )
    )
    @Column(
        name = "scalp_condition",
        nullable = false
    )
    private Set<String> scalpCondition;

    /**
     * Storico dei trattamenti chimici.
     */
    @ElementCollection
    @CollectionTable(
        name = "hair_profile_chemical_history",
        joinColumns = @JoinColumn(
            name = "hair_profile_id"
        )
    )
    @Column(
        name = "chemical_history",
        nullable = false
    )
    private Set<String> chemicalHistory;

    /**
     * Sensibilità rilevate o dichiarate.
     */
    @ElementCollection
    @CollectionTable(
        name = "hair_profile_sensitivities",
        joinColumns = @JoinColumn(
            name = "hair_profile_id"
        )
    )
    @Column(
        name = "sensitivity",
        nullable = false
    )
    private Set<String> sensitivities;

    /**
     * Controindicazioni tecniche.
     */
    @ElementCollection
    @CollectionTable(
        name = "hair_profile_contraindications",
        joinColumns = @JoinColumn(
            name = "hair_profile_id"
        )
    )
    @Column(
        name = "contraindication",
        nullable = false
    )
    private Set<String> contraindications;

    /**
     * Note tecniche libere.
     */
    private String notes;
}