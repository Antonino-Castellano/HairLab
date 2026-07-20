package com.generation.hairlab.model;

import java.time.LocalDateTime;

import com.generation.hairlab.enums.ConsultationType;
import com.generation.hairlab.enums.FeasibilityStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Rappresenta una consulenza tecnica effettuata su un cliente del salone.
 *
 * La consulenza collega il cliente e l'operatore che la esegue e può essere
 * associata opzionalmente a un appuntamento. Contiene diagnosi, obiettivo,
 * valutazione di fattibilità, rischi e procedura proposta.
 */
@Entity
@Table(name = "consultations")
public class Consultation {

    /*
     * ID univoco della consulenza.
     *
     * IDENTITY lascia al database MySQL il compito
     * di generare automaticamente il valore.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    /*
     * Cliente a cui appartiene la consulenza.
     *
     * Molte consulenze possono appartenere allo stesso cliente.
     *
     * nullable = false:
     * una consulenza non può esistere senza un cliente.
     */
    @ManyToOne(fetch = FetchType.LAZY)
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;


    /*
     * Appuntamento eventualmente collegato alla consulenza.
     *
     * È nullable perché una consulenza potrebbe essere effettuata
     * anche senza prenotare immediatamente un appuntamento.
     */
    @ManyToOne(fetch = FetchType.LAZY)
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
    @Column(nullable = false, length = 30)
    private ConsultationType type;


    /*
     * Obiettivo espresso dal cliente.
     *
     * Esempio:
     * "Vorrei passare da castano scuro a rame chiaro"
     */
    @Column(columnDefinition = "TEXT")
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
    @Column(name = "initial_diagnosis", columnDefinition = "TEXT")
    private String initialDiagnosis;


    /*
     * Condizione attuale del capello.
     *
     * Può contenere una descrizione sintetica dello stato
     * rilevato al momento della consulenza.
     */
    @Column(name = "current_condition", columnDefinition = "TEXT")
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
    @Column(length = 40)
    private FeasibilityStatus feasibility;


    /*
     * Eventuali rischi individuati.
     *
     * Esempio:
     * "Possibile sensibilizzazione della fibra"
     * "Rischio di rottura sulle lunghezze"
     */
    @Column(columnDefinition = "TEXT")
    private String risks;


    /*
     * Procedura proposta dall'operatore.
     *
     * Esempio:
     * "Decapaggio leggero, tonalizzazione e trattamento ricostruttivo"
     */
    @Column(name = "proposed_procedure", columnDefinition = "TEXT")
    private String proposedProcedure;


    /*
     * Note tecniche aggiuntive.
     */
    @Column(name = "technical_notes", columnDefinition = "TEXT")
    private String technicalNotes;


    /*
     * Costruttore vuoto obbligatorio per JPA.
     */
    public Consultation() {
    }


    /*
     * Costruttore senza ID.
     *
     * L'ID viene infatti generato automaticamente dal database.
     */
    public Consultation(
            Customer customer,
            Employee employee,
            Appointment appointment,
            LocalDateTime consultationDate,
            ConsultationType type,
            String objective,
            String initialDiagnosis,
            String currentCondition,
            FeasibilityStatus feasibility,
            String risks,
            String proposedProcedure,
            String technicalNotes) {

        this.customer = customer;
        this.employee = employee;
        this.appointment = appointment;
        this.consultationDate = consultationDate;
        this.type = type;
        this.objective = objective;
        this.initialDiagnosis = initialDiagnosis;
        this.currentCondition = currentCondition;
        this.feasibility = feasibility;
        this.risks = risks;
        this.proposedProcedure = proposedProcedure;
        this.technicalNotes = technicalNotes;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public LocalDateTime getConsultationDate() {
        return consultationDate;
    }

    public void setConsultationDate(LocalDateTime consultationDate) {
        this.consultationDate = consultationDate;
    }

    public ConsultationType getType() {
        return type;
    }

    public void setType(ConsultationType type) {
        this.type = type;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getInitialDiagnosis() {
        return initialDiagnosis;
    }

    public void setInitialDiagnosis(String initialDiagnosis) {
        this.initialDiagnosis = initialDiagnosis;
    }

    public String getCurrentCondition() {
        return currentCondition;
    }

    public void setCurrentCondition(String currentCondition) {
        this.currentCondition = currentCondition;
    }

    public FeasibilityStatus getFeasibility() {
        return feasibility;
    }

    public void setFeasibility(FeasibilityStatus feasibility) {
        this.feasibility = feasibility;
    }

    public String getRisks() {
        return risks;
    }

    public void setRisks(String risks) {
        this.risks = risks;
    }

    public String getProposedProcedure() {
        return proposedProcedure;
    }

    public void setProposedProcedure(String proposedProcedure) {
        this.proposedProcedure = proposedProcedure;
    }

    public String getTechnicalNotes() {
        return technicalNotes;
    }

    public void setTechnicalNotes(String technicalNotes) {
        this.technicalNotes = technicalNotes;
    }
}