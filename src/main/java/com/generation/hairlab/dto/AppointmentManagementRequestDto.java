package com.generation.hairlab.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Request aggregata per creare o modificare
 * un appuntamento completo di servizi.
 *
 * IMPORTANTE:
 *
 * lo stato NON viene più ricevuto dal form.
 *
 * Le transizioni:
 *
 * BOOKED
 * -> CONFIRMED
 * -> IN_PROGRESS
 * -> COMPLETED
 *
 * vengono gestite esclusivamente
 * da AppointmentWorkflowService.
 *
 * In questo modo un normale PUT di modifica
 * non può saltare arbitrariamente
 * da BOOKED a COMPLETED.
 */
@Data
public class AppointmentManagementRequestDto {

    /**
     * Cliente proprietario dell'appuntamento.
     */
    @NotNull(
        message = "Il cliente è obbligatorio"
    )
    private Integer customerId;

    /**
     * Data e ora generale di inizio.
     */
    @NotNull(
        message = "Data e ora dell'appuntamento sono obbligatorie"
    )
    private LocalDateTime startDateTime;

    /**
     * Note generali opzionali.
     */
    private String notes;

    /**
     * Servizi prenotati.
     */
    @NotEmpty(
        message = "Inserisci almeno un servizio"
    )
    @Valid
    private List<AppointmentItemRequestDto> items =
        new ArrayList<>();
}
