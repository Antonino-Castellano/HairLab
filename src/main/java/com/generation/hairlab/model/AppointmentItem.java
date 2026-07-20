package com.generation.hairlab.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * Rappresenta un singolo servizio pianificato all'interno di un appuntamento.
 *
 * Un Appointment può contenere più AppointmentItem. Ogni item identifica il
 * servizio scelto, l'operatore assegnato, la durata, il prezzo concordato e le
 * eventuali informazioni relative al completamento del servizio.
 */
@Entity
@Data
public class AppointmentItem {

    /** Identificativo univoco dell'item dell'appuntamento. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Data e ora previste per l'esecuzione del singolo servizio. */
    @Column(nullable = false)
    private LocalDateTime scheduledTime;

    /** Durata prevista del servizio, espressa in minuti. */
    @Column(nullable = false)
    private int duration;

    /** Prezzo concordato per questo specifico servizio. */
    private double agreedPrice;

    /** Note relative al risultato ottenuto dopo l'esecuzione del servizio. */
    private String resultNotes;

    /** Data e ora in cui il servizio risulta completato. */
    private LocalDateTime completedAt;

    /**
     * Appuntamento a cui appartiene questo item.
     *
     * La relazione corretta è ManyToOne: un Appointment può contenere molti
     * AppointmentItem, ma ogni AppointmentItem appartiene a un solo Appointment.
     * Non è ManyToMany perché questo oggetto non viene condiviso tra appuntamenti
     * diversi.
     */
    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    /**
     * Servizio del salone prenotato in questo item.
     *
     * La relazione è ManyToOne perché lo stesso SalonProduct può essere scelto
     * in moltissimi appuntamenti, mentre ogni AppointmentItem fa riferimento a
     * un solo servizio.
     */
    @ManyToOne
    @JoinColumn(name = "salonProduct_id")
    private SalonProduct salonProduct;

    /**
     * Dipendente incaricato di eseguire il servizio.
     *
     * La relazione è ManyToOne perché un dipendente può eseguire molti servizi
     * in appuntamenti differenti, mentre ogni AppointmentItem è assegnato a un
     * solo Employee.
     */
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
