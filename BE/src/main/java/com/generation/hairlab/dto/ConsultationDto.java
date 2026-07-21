package com.generation.hairlab.dto;

import java.time.LocalDateTime;

import com.generation.hairlab.enums.ConsultationType;
import com.generation.hairlab.enums.FeasibilityStatus;

import lombok.Data;

/**
 * DTO utilizzato per trasferire i dati relativi a una consulenza tecnica.
 *
 * Le relazioni con Customer, Employee e Appointment vengono rappresentate
 * tramite i rispettivi identificativi invece di trasferire direttamente
 * le Entity JPA.
 *
 * appointmentId può essere null perché una consulenza può essere effettuata
 * anche senza essere immediatamente collegata a un appuntamento.
 */
@Data
public class ConsultationDto {

    /** Identificativo univoco della consulenza. */
    private Integer id;

    /** Identificativo del cliente oggetto della consulenza. */
    private Integer customerId;

    /** Identificativo dell'operatore che effettua la consulenza. */
    private Integer employeeId;

    /** Identificativo dell'eventuale appuntamento collegato. */
    private Integer appointmentId;

    /** Data e ora della consulenza. */
    private LocalDateTime consultationDate;

    /** Tipologia della consulenza. */
    private ConsultationType type;

    /** Obiettivo espresso dal cliente. */
    private String objective;

    /** Diagnosi tecnica iniziale effettuata dall'operatore. */
    private String initialDiagnosis;

    /** Condizione attuale rilevata sui capelli. */
    private String currentCondition;

    /** Valutazione della fattibilità del trattamento richiesto. */
    private FeasibilityStatus feasibility;

    /** Eventuali rischi tecnici individuati. */
    private String risks;

    /** Procedura tecnica proposta dall'operatore. */
    private String proposedProcedure;

    /** Note tecniche aggiuntive. */
    private String technicalNotes;
}
