package com.generation.hairlab.model;

import java.time.LocalDateTime;

import com.generation.hairlab.enums.ColorFormulaStatus;
import com.generation.hairlab.enums.MixingRatio;
import com.generation.hairlab.enums.Oxygen;

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

/**
 * Rappresenta una formula colore creata durante il lavoro tecnico sul cliente.
 *
 * La formula può essere collegata alla consulenza che l'ha generata e allo
 * specifico AppointmentItem nel quale viene proposta o utilizzata.
 */
@Entity
@Data
public class ColorFormula {

    /** Identificativo univoco della formula colore. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Consulenza da cui deriva la formula.
     *
     * La relazione è ManyToOne perché una Consultation può produrre più formule
     * alternative o successive, mentre ogni ColorFormula fa riferimento a una
     * sola consulenza.
     */
    @ManyToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    /**
     * Servizio dell'appuntamento al quale la formula è collegata.
     *
     * La relazione è ManyToOne perché uno stesso AppointmentItem può avere più
     * formule associate, per esempio una proposta iniziale e una formula poi
     * effettivamente utilizzata.
     */
    @ManyToOne
    @JoinColumn(name = "appointmentItem_id")
    private AppointmentItem appointmentItem;

    /** Nome identificativo della formula. */
    @Column(nullable = false, unique = true)
    private String name;

    /** Descrizione del risultato cromatico che si vuole ottenere. */
    @Column(nullable = false)
    private String targetResult;

    /** Volume dell'ossidante/developer previsto dalla formula. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Oxygen volumeDeveloper;

    /** Rapporto di miscelazione previsto tra i componenti della formula. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MixingRatio mixingRatio;

    /** Stato della formula nel suo ciclo di vita. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ColorFormulaStatus status;

    /** Eventuali note tecniche aggiuntive. */
    private String notes;

    /** Data e ora di creazione della formula. */
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
