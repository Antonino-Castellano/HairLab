package com.generation.hairlab.dto;

import java.time.LocalDate;
import java.util.Set;

import com.generation.hairlab.enums.JobTitle;
import com.generation.hairlab.enums.Specialization;

import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO utilizzato per trasferire i dati relativi a un dipendente
 * o collaboratore del salone.
 *
 * JobTitle rappresenta la mansione principale, mentre Specialization
 * rappresenta le competenze professionali specifiche. Le specializzazioni
 * sono mantenute come Set perché uno stesso dipendente può possederne più di una.
 */
@Data
public class EmployeeDto {

    /** Identificativo univoco del dipendente. */
    private Integer id;

    /** Nome del dipendente. */
    private String firstName;

    /** Cognome del dipendente. */
    private String lastName;

    /** Indirizzo email del dipendente. */
    private String email;

    /** Numero di telefono del dipendente. */
    private String telephoneNumber;

    /** Mansione principale svolta dal dipendente. */
    private JobTitle jobTitle;

    /** Insieme delle specializzazioni professionali del dipendente. */
    private Set<Specialization> specializations;

    /** Data di assunzione o di inizio collaborazione. */
    private LocalDate hireDate;

    /** Indica se il dipendente è attualmente attivo. */
    private boolean active;

    /** Eventuali note interne relative al dipendente. */
    private String notes;

    @Size(max = 2_000_000, message = "L'immagine del profilo è troppo grande")
    private String profileImage;
}
