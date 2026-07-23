package com.generation.hairlab.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

/**
 * DTO utilizzato dal modulo di gestione appuntamenti
 * per descrivere un singolo servizio da eseguire.
 *
 * Non contiene appointmentId e scheduledTime:
 *
 * - appointmentId viene determinato dal backend
 *   dopo la creazione dell'appuntamento;
 *
 * - scheduledTime viene calcolato automaticamente
 *   concatenando i servizi nell'ordine ricevuto.
 */
@Data
public class AppointmentItemRequestDto {

    /**
     * ID opzionale.
     *
     * In modifica può essere valorizzato dal frontend,
     * ma nel flusso attuale gli item vengono rigenerati
     * in modo atomico.
     */
    private Integer id;

    /**
     * Servizio del listino selezionato.
     */
    @NotNull(
        message = "Il servizio è obbligatorio"
    )
    private Integer salonProductId;

    /**
     * Operatore assegnato.
     */
    @NotNull(
        message = "Il dipendente è obbligatorio"
    )
    private Integer employeeId;

    /**
     * Durata storicizzata in minuti.
     */
    @Positive(
        message = "La durata deve essere maggiore di zero"
    )
    private int duration;

    /**
     * Prezzo concordato e storicizzato.
     */
    @PositiveOrZero(
        message = "Il prezzo non può essere negativo"
    )
    private double agreedPrice;

    /**
     * Note opzionali sul servizio.
     */
    private String resultNotes;
}
