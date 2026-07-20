package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.generation.hairlab.dto.ConsultationDto;
import com.generation.hairlab.model.Consultation;

/**
 * Mapper MapStruct utilizzato per convertire Consultation
 * in ConsultationDto e viceversa.
 *
 * Customer, Employee e Appointment vengono rappresentati nel DTO
 * tramite i rispettivi identificativi.
 */
@Mapper(componentModel = "spring")
public interface ConsultationMapper {

    /** Converte Consultation in ConsultationDto. */
    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "appointmentId", source = "appointment.id")
    ConsultationDto toDto(Consultation entity);

    /** Converte una lista di Consultation in DTO. */
    List<ConsultationDto> toDtoList(List<Consultation> entities);

    /**
     * Converte ConsultationDto in una nuova Entity Consultation.
     *
     * Le relazioni vengono ignorate perché devono essere recuperate
     * nel Service tramite customerId, employeeId e appointmentId.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "appointment", ignore = true)
    Consultation toEntity(ConsultationDto dto);

    /** Converte una lista di ConsultationDto in Entity. */
    List<Consultation> toEntityList(List<ConsultationDto> dtos);
}
