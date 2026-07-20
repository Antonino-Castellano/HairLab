package com.generation.hairlab.dto;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * DTO utilizzato per trasferire i dati relativi a un singolo servizio
 * pianificato all'interno di un appuntamento.
 *
 * Le relazioni con Appointment, SalonProduct ed Employee vengono rappresentate
 * tramite i rispettivi identificativi. Il Service utilizzerà questi ID per
 * recuperare le Entity reali dal database.
 */
@Data
public class AppointmentItemDto {

    /** Identificativo univoco dell'item. */
    private Integer id;

    /** Identificativo dell'appuntamento a cui appartiene l'item. */
    private Integer appointmentId;

    /** Identificativo del servizio o prodotto prenotato. */
    private Integer salonProductId;

    /** Identificativo del dipendente incaricato di eseguire il servizio. */
    private Integer employeeId;

    /** Data e ora previste per l'inizio dello specifico servizio. */
    private LocalDateTime scheduledTime;

    /** Durata prevista o concordata del servizio in minuti. */
    private int duration;

    /**
     * Prezzo concordato per lo specifico servizio.
     *
     * Rimane separato dal prezzo base del SalonProduct per conservare
     * correttamente lo storico anche se il listino viene modificato.
     */
    private double agreedPrice;

    /** Eventuali note relative al risultato ottenuto. */
    private String resultNotes;

    /**
     * Data e ora effettive di completamento del servizio.
     *
     * Può essere null finché il servizio non è stato completato.
     */
    private LocalDateTime completedAt;
}
