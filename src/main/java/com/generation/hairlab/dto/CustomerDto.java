package com.generation.hairlab.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

/**
 * DTO utilizzato per trasferire i dati relativi a un cliente
 * tra backend e frontend.
 *
 * Il DTO separa i dati esposti tramite API
 * dalle Entity JPA utilizzate per la persistenza.
 *
 * La relazione con gli appuntamenti viene rappresentata
 * tramite una lista di identificativi evitando riferimenti
 * circolari del tipo:
 *
 * Customer -> Appointment -> Customer.
 */
@Data
public class CustomerDto {

    /**
     * Identificativo univoco del cliente.
     *
     * Durante la creazione può essere null
     * perché viene generato dal database.
     */
    private Integer id;

    /**
     * Nome del cliente.
     */
    private String firstName;

    /**
     * Cognome del cliente.
     */
    private String lastName;

    /**
     * Numero di telefono del cliente.
     */
    private String phoneNumber;

    /**
     * Indirizzo email del cliente.
     */
    private String email;

    /**
     * Data di nascita del cliente.
     */
    private LocalDate dob;

    /**
     * Indica se il cliente è attivo nel gestionale.
     *
     * Un cliente disattivato rimane comunque
     * presente nello storico.
     */
    private boolean active;

    /**
     * Foto profilo del cliente.
     *
     * Il frontend invia e riceve una stringa Base64.
     *
     * Esempio:
     *
     * data:image/jpeg;base64,/9j/4AAQ...
     *
     * Se il cliente non possiede una foto,
     * il campo può essere null.
     */
    private String profileImage;

    /**
     * Data e ora di creazione dell'anagrafica.
     */
    private LocalDateTime createdAt;

    /**
     * Data e ora dell'ultima modifica.
     */
    private LocalDateTime updatedAt;

    /**
     * Identificativi degli appuntamenti
     * associati al cliente.
     *
     * Si utilizzano gli ID invece delle Entity
     * per evitare strutture JSON ricorsive.
     */
    private List<Integer> appointmentIds;
}