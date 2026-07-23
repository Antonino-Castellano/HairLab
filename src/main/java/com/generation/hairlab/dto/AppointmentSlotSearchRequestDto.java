package com.generation.hairlab.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * Richiesta per trovare i primi orari
 * in cui l'intera sequenza di servizi
 * può essere eseguita senza conflitti.
 *
 * Esempio:
 *
 * data: 25/07
 *
 * servizio 1:
 * Anna - 90 min
 *
 * servizio 2:
 * Luca - 45 min
 *
 * HairLab cerca un orario di partenza
 * in cui:
 *
 * Anna sia libera per i primi 90 minuti
 * E
 * Luca sia libero nei 45 minuti successivi.
 */
@Data
public class AppointmentSlotSearchRequestDto {

    /**
     * Giorno su cui effettuare la ricerca.
     */
    @NotNull(
        message = "La data è obbligatoria"
    )
    private LocalDate date;

    /**
     * Inizio della finestra di ricerca.
     *
     * Esempio: 08:00
     */
    @NotNull(
        message = "L'orario iniziale della ricerca è obbligatorio"
    )
    private LocalTime windowStart;

    /**
     * Fine della finestra di ricerca.
     *
     * Esempio: 20:00
     */
    @NotNull(
        message = "L'orario finale della ricerca è obbligatorio"
    )
    private LocalTime windowEnd;

    /**
     * Passo tra un tentativo e il successivo.
     *
     * Default:
     * 15 minuti.
     */
    @Positive(
        message = "Il passo di ricerca deve essere maggiore di zero"
    )
    private Integer stepMinutes = 15;

    /**
     * Numero massimo di proposte restituite.
     *
     * Default:
     * 8.
     */
    @Positive(
        message = "Il numero massimo di risultati deve essere maggiore di zero"
    )
    private Integer maxResults = 8;

    /**
     * Durante la modifica ignoriamo
     * l'appuntamento stesso.
     */
    private Integer excludeAppointmentId;

    /**
     * Sequenza dei servizi.
     */
    @NotEmpty(
        message = "Inserisci almeno un servizio da pianificare"
    )
    @Valid
    private List<AppointmentSlotItemRequestDto> items =
        new ArrayList<>();
}
