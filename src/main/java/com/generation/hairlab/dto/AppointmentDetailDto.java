package com.generation.hairlab.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * DTO aggregato restituito dalla gestione appuntamenti.
 *
 * Contiene:
 *
 * - dati generali dell'appuntamento;
 * - elenco dei servizi associati.
 */
@Data
public class AppointmentDetailDto {

    private AppointmentDto appointment;

    private List<AppointmentItemDto> items =
        new ArrayList<>();
}
