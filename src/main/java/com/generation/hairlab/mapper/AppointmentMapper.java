package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.generation.hairlab.dto.AppointmentDto;
import com.generation.hairlab.model.Appointment;

/**
 * Mapper MapStruct per Appointment.
 *
 * La relazione con Customer viene rappresentata nel DTO tramite customerId.
 * Nel percorso inverso il Customer non viene creato automaticamente:
 * sarà il Service a recuperarlo tramite CustomerRepository.
 */
@Mapper(config = HairLabMapperConfig.class)
public interface AppointmentMapper {

    /** Converte Appointment in AppointmentDto. */
    @Mapping(target = "customerId", source = "customer.id")
    AppointmentDto toDto(Appointment entity);

    /** Converte una lista di Appointment in DTO. */
    List<AppointmentDto> toDtoList(List<Appointment> entities);

    /**
     * Crea un Appointment dai dati semplici del DTO.
     *
     * customer, id e timestamp gestiti dal backend vengono ignorati.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Appointment toEntity(AppointmentDto dto);

    /**
     * Aggiorna un Appointment esistente senza modificare automaticamente
     * relazione, ID e timestamp tecnici.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(AppointmentDto dto, @MappingTarget Appointment entity);
}
