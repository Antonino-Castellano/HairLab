package com.generation.hairlab.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * Richiesta di verifica disponibilità operatori
 * per uno specifico intervallo temporale.
 *
 * Esempio:
 *
 * startDateTime = 2026-07-25T09:00
 * duration = 90
 *
 * intervallo richiesto:
 *
 * 09:00 -> 10:30
 *
 * In modifica è possibile valorizzare
 * excludeAppointmentId per ignorare
 * gli item dell'appuntamento che stiamo modificando.
 */
@Data
public class AppointmentAvailabilityRequestDto {

    /**
     * Data e ora di inizio del servizio.
     */
    @NotNull(
        message = "Data e ora di inizio sono obbligatorie"
    )
    private LocalDateTime startDateTime;

    /**
     * Durata del servizio in minuti.
     */
    @Positive(
        message = "La durata deve essere maggiore di zero"
    )
    private int duration;

    /**
     * Appuntamento da ignorare durante
     * una verifica in modalità modifica.
     *
     * Può essere null in creazione.
     */
    private Integer excludeAppointmentId;
}
