package com.generation.hairlab.model;

import java.time.LocalDateTime;

import com.generation.hairlab.enums.ConsultationType;
import com.generation.hairlab.enums.FeasibilityStatus;

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
 * Rappresenta una consulenza tecnica effettuata su un cliente del salone.
 *
 * La consulenza collega il cliente e l'operatore che la esegue e può essere
 * associata opzionalmente a un appuntamento. Contiene diagnosi, obiettivo,
 * valutazione di fattibilità, rischi e procedura proposta.
 */
@Entity
@Data
public class Consultation {

    /*
     * ID univoco della consulenza.
     *
     * IDENTITY lascia al database MySQL il compito
     * di generare automaticamente il valore.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /*
     * Cliente a cui appartiene la consulenza.
     *
     * Molte consulenze possono appartenere allo stesso cliente.
     *
     * nullable = false:
     * una consulenza non può esistere senza un cliente.
     */
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;


    /*
     * Operatore che ha effettuato la consulenza.
     *
     * Ad esempio:
     * - parrucchiere
     * - colorista
     * - hair stylist
     */
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;


    /*
     * Appuntamento eventualmente collegato alla consulenza.
     *
     * È nullable perché una consulenza potrebbe essere effettuata
     * anche senza prenotare immediatamente un appuntamento.
     */
    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;


    /*
     * Data e ora in cui viene effettuata la consulenza.
     */
    @Column(name = "consultation_date", nullable = false)
    private LocalDateTime consultationDate;


    /*
     * Tipo di consulenza.
     *
     * Esempi:
     * HAIR_CUT
     * HAIR_COLOR
     * HAIR_STYLING
     * HAIR_ANALYSIS
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConsultationType type;


    /*
     * Obiettivo espresso dal cliente.
     *
     * Esempio:
     * "Vorrei passare da castano scuro a rame chiaro"
     */
    @Column(nullable = false)
    private String objective;


    /*
     * Diagnosi tecnica iniziale effettuata dall'operatore.
     *
     * Qui possiamo descrivere:
     * - stato del capello
     * - colore presente
     * - danni
     * - precedenti trattamenti
     */
    @Column(name = "initial_diagnosis", nullable = false)
    private String initialDiagnosis;


    /*
     * Condizione attuale del capello.
     *
     * Può contenere una descrizione sintetica dello stato
     * rilevato al momento della consulenza.
     */
    @Column(name = "current_condition", nullable = false)
    private String currentCondition;


    /*
     * Valutazione della fattibilità del trattamento richiesto.
     *
     * Esempi:
     * FEASIBLE
     * FEASIBLE_WITH_LIMITATIONS
     * NOT_FEASIBLE
     */
    @Enumerated(EnumType.STRING)
    private FeasibilityStatus feasibility;


    /*
     * Eventuali rischi individuati.
     *
     * Esempio:
     * "Possibile sensibilizzazione della fibra"
     * "Rischio di rottura sulle lunghezze"
     */
    private String risks;


    /*
     * Procedura proposta dall'operatore.
     *
     * Esempio:
     * "Decapaggio leggero, tonalizzazione e trattamento ricostruttivo"
     */
    @Column(name = "proposed_procedure", nullable = false)
    private String proposedProcedure;


    /*
     * Note tecniche aggiuntive.
     */
    @Column(name = "technical_notes", nullable = false)
    private String technicalNotes;

}