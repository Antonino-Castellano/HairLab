package com.generation.hairlab.model;

import java.time.LocalDate;
import java.util.Set;

import com.generation.hairlab.enums.JobTitle;
import com.generation.hairlab.enums.Specialization;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import lombok.Data;

/**
 * Rappresenta un dipendente o collaboratore operativo del salone.
 *
 * Oltre ai dati anagrafici contiene la mansione principale e l'insieme delle
 * specializzazioni professionali che possono essere utilizzate per descrivere
 * le competenze dell'operatore.
 */
@Entity
@Data
public class Employee {

    /** Identificativo univoco del dipendente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Nome del dipendente. */
    @Column(nullable = false)
    private String firstName;

    /** Cognome del dipendente. */
    @Column(nullable = false)
    private String lastName;

    /** Email univoca del dipendente. */
    @Column(nullable = false, unique = true)
    private String email;

    /** Numero di telefono univoco del dipendente. */
    @Column(nullable = false, unique = true)
    private String telephoneNumber;

    /** Mansione principale svolta dal dipendente nel salone. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobTitle jobTitle;

    /**
     * Insieme delle specializzazioni professionali del dipendente.
     *
     * Specialization è un enum e non un'entità: per questo la collezione viene
     * mappata con ElementCollection e salvata in una tabella dedicata collegata
     * all'Employee tramite employee_id. Una semplice @Column non può contenere
     * direttamente un Set di valori.
     */
    @ElementCollection
    @CollectionTable(
        name = "employee_specializations",
        joinColumns = @JoinColumn(name = "employee_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "specialization", nullable = false)
    private Set<Specialization> specializations;

    /** Data di assunzione o inizio collaborazione. */
    @Column(nullable = false)
    private LocalDate hireDate;

    /** Indica se il dipendente è attualmente attivo. */
    private boolean active;

    /** Eventuali note interne relative al dipendente. */
    private String notes;

    @Lob
    @Column(name = "profile_image", columnDefinition = "LONGTEXT")
    private String profileImage;
}
