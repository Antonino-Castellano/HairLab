package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.generation.hairlab.dto.ConsultationDto;
import com.generation.hairlab.model.Consultation;

/**
 * Mapper MapStruct per Consultation.
 *
 * Customer, Employee e Appointment vengono convertiti nei rispettivi ID
 * quando la Entity viene trasformata in DTO.
 *
 * Nel percorso inverso le relazioni non vengono create dal mapper:
 * devono essere risolte dal Service tramite i Repository.
 */
@Mapper(config = HairLabMapperConfig.class)
public interface ConsultationMapper {

    /** Converte Consultation in ConsultationDto. */
    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "appointmentId", source = "appointment.id")
    ConsultationDto toDto(Consultation entity);

    /** Converte una lista di Consultation in DTO. */
    List<ConsultationDto> toDtoList(List<Consultation> entities);

    /** Crea una Consultation senza risolvere automaticamente le relazioni. */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "appointment", ignore = true)
    Consultation toEntity(ConsultationDto dto);

    /** Aggiorna i campi della Consultation lasciando ID e relazioni al Service. */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "appointment", ignore = true)
    void updateEntityFromDto(ConsultationDto dto, @MappingTarget Consultation entity);
}
