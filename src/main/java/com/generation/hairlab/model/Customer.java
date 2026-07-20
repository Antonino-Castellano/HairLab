package com.generation.hairlab.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

/**
 * Rappresenta un cliente registrato nel gestionale HairLab.
 *
 * Contiene i principali dati anagrafici e mantiene la relazione con lo storico
 * degli appuntamenti effettuati o prenotati dal cliente.
 */
@Entity
@Data
public class Customer {

    /** Identificativo univoco del cliente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Nome del cliente. */
    @Column(nullable = false)
    private String firstName;

    /** Cognome del cliente. */
    @Column(nullable = false)
    private String lastName;

    /** Numero di telefono del cliente. */
    @Column(nullable = false)
    private String phoneNumber;

    /** Email del cliente, univoca nel sistema. */
    @Column(nullable = false, unique = true)
    private String email;

    /** Data di nascita del cliente. */
    @Column(nullable = false)
    private LocalDate dob;

    /** Indica se il cliente è attualmente attivo nel gestionale. */
    @Column(nullable = false)
    private boolean active;

    /** Data e ora di creazione dell'anagrafica. */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /** Data e ora dell'ultima modifica dell'anagrafica. */
    private LocalDateTime updatedAt;

    /**
     * Appuntamenti appartenenti al cliente.
     *
     * La relazione è OneToMany perché un Customer può avere molti Appointment,
     * mentre ogni Appointment possiede un solo Customer tramite il campo
     * "customer". mappedBy indica che la chiave esterna è gestita da Appointment.
     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;
}
