package com.generation.hairlab.model;

import java.time.LocalDateTime;

import com.generation.hairlab.enums.AppointmentStatus;

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
 * Rappresenta un appuntamento prenotato da un cliente nel salone.
 *
 * Un appuntamento appartiene a un solo cliente, mentre lo stesso cliente può
 * avere molti appuntamenti nel tempo. I singoli servizi eseguiti durante
 * l'appuntamento vengono rappresentati separatamente tramite AppointmentItem.
 */
@Entity
@Data
public class Appointment {

    /**
     * Identificativo univoco dell'appuntamento.
     *
     * IDENTITY delega al database la generazione automatica dell'ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Cliente proprietario dell'appuntamento.
     *
     * La relazione è ManyToOne perché un cliente può avere molti appuntamenti,
     * mentre ogni appuntamento appartiene a un solo cliente.
     */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /** Data e ora di inizio dell'appuntamento. */
    @Column(nullable = false)
    private LocalDateTime startDateTime;

    /** Stato corrente dell'appuntamento. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    /** Eventuali note generali relative all'appuntamento. */
    private String notes;

    /** Data e ora di creazione della prenotazione. */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /** Data e ora dell'ultima modifica dell'appuntamento. */
    private LocalDateTime updatedAt;
}
