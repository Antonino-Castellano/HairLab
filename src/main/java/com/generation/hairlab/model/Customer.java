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
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.Data;

/**
 * Rappresenta un cliente registrato nel gestionale HairLab.
 *
 * Contiene:
 * - dati anagrafici;
 * - stato del cliente;
 * - foto profilo;
 * - date di creazione e modifica;
 * - storico degli appuntamenti.
 */
@Entity
@Data
public class Customer {

    /**
     * Identificativo univoco del cliente.
     *
     * Viene generato automaticamente dal database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nome del cliente.
     */
    @Column(nullable = false)
    private String firstName;

    /**
     * Cognome del cliente.
     */
    @Column(nullable = false)
    private String lastName;

    /**
     * Numero di telefono del cliente.
     */
    @Column(nullable = false)
    private String phoneNumber;

    /**
     * Email del cliente.
     *
     * Deve essere univoca all'interno del gestionale.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Data di nascita del cliente.
     */
    @Column(nullable = false)
    private LocalDate dob;

    /**
     * Indica se il cliente è attualmente attivo.
     *
     * La disattivazione permette di mantenere
     * lo storico senza eliminare fisicamente il cliente.
     */
    @Column(nullable = false)
    private boolean active;

    /**
     * Foto profilo del cliente.
     *
     * Angular invia una stringa Base64 del tipo:
     *
     * data:image/jpeg;base64,/9j/4AAQ...
     *
     * @Lob indica che il campo può contenere
     * una grande quantità di testo.
     *
     * LONGTEXT viene utilizzato perché una stringa Base64
     * può essere molto più lunga di un normale VARCHAR.
     *
     * Il campo può essere null perché la foto
     * non è obbligatoria.
     */
    @Lob
    @Column(name = "profile_image", columnDefinition = "LONGTEXT")
    private String profileImage;

    /**
     * Data e ora di creazione dell'anagrafica.
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * Data e ora dell'ultima modifica dell'anagrafica.
     */
    private LocalDateTime updatedAt;

    /**
     * Appuntamenti appartenenti al cliente.
     *
     * La relazione è OneToMany perché:
     *
     * un Customer
     * può avere
     * molti Appointment.
     *
     * mappedBy = "customer" indica che la relazione
     * viene gestita dal campo customer presente
     * nella Entity Appointment.
     *
     * cascade = ALL permette di propagare
     * le operazioni sul cliente agli appuntamenti.
     *
     * orphanRemoval = true elimina gli appuntamenti
     * rimossi dalla relazione.
     */
    @OneToMany(
        mappedBy = "customer",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Appointment> appointments;
}