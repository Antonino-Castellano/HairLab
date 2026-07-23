package com.generation.hairlab.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * Singolo servizio usato durante
 * la ricerca degli slot disponibili.
 *
 * Per cercare un orario libero dobbiamo sapere:
 *
 * - quale operatore eseguirà il servizio;
 * - quanto durerà.
 */
@Data
public class AppointmentSlotItemRequestDto {

    /**
     * Operatore assegnato al servizio.
     */
    @NotNull(
        message = "L'operatore è obbligatorio"
    )
    private Integer employeeId;

    /**
     * Durata in minuti.
     */
    @Positive(
        message = "La durata deve essere maggiore di zero"
    )
    private int duration;
}
