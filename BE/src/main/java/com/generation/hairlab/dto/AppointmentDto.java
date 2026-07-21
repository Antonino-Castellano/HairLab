package com.generation.hairlab.dto;

import java.time.LocalDateTime;

import com.generation.hairlab.enums.AppointmentStatus;

import lombok.Data;

/**
 * DTO utilizzato per trasferire i dati generali relativi a un appuntamento.
 *
 * La relazione ManyToOne con Customer viene rappresentata tramite customerId.
 * I singoli servizi dell'appuntamento vengono invece rappresentati tramite
 * AppointmentItemDto e possono essere gestiti con operazioni dedicate.
 *
 * Questa separazione evita di trasferire direttamente le Entity JPA e limita
 * il rischio di riferimenti circolari nella serializzazione JSON.
 */
@Data
public class AppointmentDto {

    /** Identificativo univoco dell'appuntamento. */
    private Integer id;

    /** Identificativo del cliente a cui appartiene l'appuntamento. */
    private Integer customerId;

    /** Data e ora generale di inizio dell'appuntamento. */
    private LocalDateTime startDateTime;

    /** Stato corrente dell'appuntamento. */
    private AppointmentStatus status;

    /** Eventuali note generali relative all'appuntamento. */
    private String notes;

    /** Data e ora di creazione dell'appuntamento. */
    private LocalDateTime createdAt;

    /** Data e ora dell'ultima modifica dell'appuntamento. */
    private LocalDateTime updatedAt;
}
