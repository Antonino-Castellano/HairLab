package com.generation.hairlab.dto;

import java.time.LocalDateTime;

import com.generation.hairlab.enums.AppointmentStatus;

import lombok.Data;

/**
 * DTO ottimizzato per la Timeline operatori.
 *
 * Contiene già tutte le informazioni necessarie
 * alla visualizzazione:
 *
 * - appuntamento;
 * - cliente;
 * - operatore;
 * - servizio;
 * - orario;
 * - durata;
 * - stato.
 *
 * Il frontend non deve quindi ricostruire
 * questi dati tramite molte chiamate separate.
 */
@Data
public class AppointmentTimelineItemDto {

    private Integer appointmentItemId;

    private Integer appointmentId;

    private Integer customerId;

    private String customerName;

    private Integer employeeId;

    private String employeeName;

    private Integer salonProductId;

    private String serviceName;

    private LocalDateTime scheduledTime;

    private int duration;

    private AppointmentStatus status;
}
