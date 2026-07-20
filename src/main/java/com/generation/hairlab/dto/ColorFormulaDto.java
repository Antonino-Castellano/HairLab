package com.generation.hairlab.dto;

import java.time.LocalDateTime;

import com.generation.hairlab.enums.ColorFormulaStatus;
import com.generation.hairlab.enums.MixingRatio;
import com.generation.hairlab.enums.Oxygen;

import lombok.Data;

/**
 * DTO utilizzato per trasferire i dati relativi a una formula colore.
 *
 * Le relazioni con Consultation e AppointmentItem vengono rappresentate
 * tramite i rispettivi identificativi, evitando di trasferire direttamente
 * le Entity JPA.
 *
 * Gli elementi e gli ingredienti della formula vengono gestiti separatamente
 * tramite ColorFormulaItemDto.
 */
@Data
public class ColorFormulaDto {

    /** Identificativo univoco della formula. */
    private Integer id;

    /** Identificativo della consulenza da cui deriva la formula. */
    private Integer consultationId;

    /** Identificativo dell'AppointmentItem sul quale la formula viene utilizzata. */
    private Integer appointmentItemId;

    /** Nome assegnato alla formula. */
    private String name;

    /**
     * Descrizione del risultato cromatico desiderato.
     *
     * Rimane una String perché un risultato tecnico può essere molto specifico
     * e non sempre rappresentabile tramite un singolo enum.
     */
    private String targetResult;

    /** Volume dell'ossidante utilizzato. */
    private Oxygen volumeDeveloper;

    /** Rapporto di miscelazione previsto. */
    private MixingRatio mixingRatio;

    /** Stato corrente della formula colore. */
    private ColorFormulaStatus status;

    /** Eventuali note tecniche relative alla formula. */
    private String notes;

    /** Data e ora di creazione della formula. */
    private LocalDateTime createdAt;
}
