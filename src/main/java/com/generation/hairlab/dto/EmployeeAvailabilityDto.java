package com.generation.hairlab.dto;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * Risultato della verifica disponibilità
 * di un singolo operatore.
 *
 * Quando available = false vengono restituiti,
 * se disponibili, anche i dati essenziali
 * del conflitto che blocca lo slot.
 */
@Data
public class EmployeeAvailabilityDto {

    private Integer employeeId;

    private String firstName;

    private String lastName;

    private boolean available;

    /**
     * Appuntamento che crea il conflitto.
     */
    private Integer conflictingAppointmentId;

    /**
     * Inizio del servizio già presente.
     */
    private LocalDateTime conflictStart;

    /**
     * Fine calcolata del servizio già presente.
     */
    private LocalDateTime conflictEnd;

    /**
     * Messaggio leggibile dal frontend.
     */
    private String message;
}
