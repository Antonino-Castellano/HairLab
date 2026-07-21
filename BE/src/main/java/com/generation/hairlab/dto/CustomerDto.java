package com.generation.hairlab.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

/**
 * DTO utilizzato per trasferire i dati relativi a un cliente.
 *
 * Il DTO separa i dati esposti dalle Entity JPA utilizzate per la persistenza.
 * La relazione con gli appuntamenti non viene rappresentata tramite oggetti
 * Appointment, ma tramite i relativi identificativi, evitando riferimenti
 * circolari durante la serializzazione JSON.
 *
 * Lo stesso DTO può essere utilizzato sia in ingresso sia in uscita:
 * in fase di creazione id, createdAt e updatedAt possono essere null e
 * vengono valorizzati dal backend.
 */
@Data
public class CustomerDto {

    /** Identificativo univoco del cliente. */
    private Integer id;

    /** Nome del cliente. */
    private String firstName;

    /** Cognome del cliente. */
    private String lastName;

    /** Numero di telefono del cliente. */
    private String phoneNumber;

    /** Indirizzo email del cliente. */
    private String email;

    /** Data di nascita del cliente. */
    private LocalDate dob;

    /**
     * Indica se il cliente è attivo nel gestionale.
     *
     * La disattivazione permette di mantenere lo storico senza eliminare
     * fisicamente il cliente dal database.
     */
    private boolean active;

    /** Data e ora di creazione dell'anagrafica. */
    private LocalDateTime createdAt;

    /** Data e ora dell'ultima modifica dell'anagrafica. */
    private LocalDateTime updatedAt;

    /**
     * Identificativi degli appuntamenti associati al cliente.
     *
     * Si utilizzano gli ID invece delle Entity Appointment per evitare
     * strutture JSON ricorsive Customer -> Appointment -> Customer.
     */
    private List<Integer> appointmentIds;
}
