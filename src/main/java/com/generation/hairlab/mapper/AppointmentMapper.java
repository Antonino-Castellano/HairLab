package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.generation.hairlab.dto.AppointmentDto;
import com.generation.hairlab.model.Appointment;

/**
 * Mapper MapStruct utilizzato per convertire Appointment
 * in AppointmentDto e viceversa.
 *
 * La relazione con Customer viene rappresentata nel DTO tramite customerId.
 * Nel percorso DTO -> Entity il Customer viene ignorato perché deve essere
 * recuperato dal database nel Service.
 */
@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    /** Converte Appointment in AppointmentDto. */
    @Mapping(target = "customerId", source = "customer.id")
    AppointmentDto toDto(Appointment entity);

    /** Converte una lista di Appointment in DTO. */
    List<AppointmentDto> toDtoList(List<Appointment> entities);

    /**
     * Converte AppointmentDto in una nuova Entity Appointment.
     *
     * customer, id, createdAt e updatedAt vengono ignorati perché
     * devono essere gestiti dal Service/backend.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Appointment toEntity(AppointmentDto dto);

    /** Converte una lista di AppointmentDto in Entity. */
    List<Appointment> toEntityList(List<AppointmentDto> dtos);
}
