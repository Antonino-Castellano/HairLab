package com.generation.hairlab.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.generation.hairlab.enums.ColorApplicationType;
import com.generation.hairlab.enums.ColorFormulaStatus;
import com.generation.hairlab.enums.MixingRatio;
import com.generation.hairlab.enums.Oxygen;
import com.generation.hairlab.enums.Reflection;
import com.generation.hairlab.enums.ToneLevel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Rappresenta una formula colore salvata in HairLab.
 *
 * BLOCCO COLOR LAB 1
 *
 * La formula viene collegata DIRETTAMENTE al Customer.
 *
 * Consultation e AppointmentItem restano relazioni opzionali:
 * una formula può infatti essere:
 *
 * - costruita manualmente nel Color Lab;
 * - generata da una consulenza;
 * - utilizzata durante uno specifico servizio.
 *
 * Il collegamento diretto al cliente permette di costruire
 * uno storico formula indipendente dal percorso con cui
 * la formula è stata creata.
 */
@Entity
@Data
public class ColorFormula {

    /** Identificativo univoco della formula. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Cliente proprietario della formula.
     *
     * Il campo DB rimane inizialmente nullable
     * per non rompere l'avvio con eventuali formule legacy.
     *
     * Il Service, però, rende customerId obbligatorio
     * per tutte le nuove formule e per gli aggiornamenti.
     */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Customer customer;

    /**
     * Consulenza opzionale da cui deriva la formula.
     */
    @ManyToOne
    @JoinColumn(name = "consultation_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Consultation consultation;

    /**
     * Servizio appuntamento opzionale
     * nel quale la formula viene utilizzata.
     */
    @ManyToOne
    @JoinColumn(name = "appointment_item_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AppointmentItem appointmentItem;

    /** Nome identificativo della formula. */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Descrizione libera del risultato cromatico.
     */
    @Column(nullable = false)
    private String targetResult;

    /**
     * Tono target strutturato.
     *
     * Nullable per compatibilità con le formule esistenti.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "target_tone_level")
    private ToneLevel targetToneLevel;

    /**
     * Riflesso target principale.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "target_primary_reflection")
    private Reflection targetPrimaryReflection;

    /**
     * Eventuale riflesso target secondario.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "target_secondary_reflection")
    private Reflection targetSecondaryReflection;

    /**
     * Area / tecnica principale di applicazione.
     *
     * Sarà utilizzata anche dal motore
     * per il calcolo della grammatura.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "application_type")
    private ColorApplicationType applicationType;

    /** Volume dell'ossidante/developer previsto. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Oxygen volumeDeveloper;

    /** Rapporto di miscelazione previsto. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MixingRatio mixingRatio;

    /**
     * Moltiplicatore personalizzato del developer.
     *
     * Esempio:
     *
     * 1 : 1.8
     * ->
     * customDeveloperRatio = 1.80
     *
     * Viene utilizzato soltanto quando:
     * mixingRatio = CUSTOM.
     */
    @Column(
        name = "custom_developer_ratio",
        precision = 6,
        scale = 3
    )
    private BigDecimal customDeveloperRatio;

    /** Stato della formula nel suo ciclo di vita. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ColorFormulaStatus status;

    /** Eventuali note tecniche. */
    private String notes;

    /** Data e ora di creazione della formula. */
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
