package com.generation.hairlab.dto;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * Singolo slot suggerito da HairLab.
 */
@Data
public class AppointmentSlotSuggestionDto {

    /**
     * Inizio dell'intero appuntamento.
     */
    private LocalDateTime startDateTime;

    /**
     * Fine dell'intera sequenza di servizi.
     */
    private LocalDateTime endDateTime;

    /**
     * Durata complessiva in minuti.
     */
    private int totalDuration;
}
