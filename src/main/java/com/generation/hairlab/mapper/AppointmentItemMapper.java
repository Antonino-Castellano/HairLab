package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.generation.hairlab.dto.AppointmentItemDto;
import com.generation.hairlab.model.AppointmentItem;

/**
 * Mapper MapStruct utilizzato per convertire AppointmentItem
 * in AppointmentItemDto e viceversa.
 *
 * Le relazioni con Appointment, SalonProduct ed Employee vengono
 * rappresentate nel DTO tramite i rispettivi identificativi.
 *
 * Nel percorso inverso le Entity collegate vengono ignorate:
 * il Service deve recuperarle dai rispettivi Repository.
 */
@Mapper(componentModel = "spring")
public interface AppointmentItemMapper {

    /** Converte AppointmentItem in AppointmentItemDto. */
    @Mapping(target = "appointmentId", source = "appointment.id")
    @Mapping(target = "salonProductId", source = "salonProduct.id")
    @Mapping(target = "employeeId", source = "employee.id")
    AppointmentItemDto toDto(AppointmentItem entity);

    /** Converte una lista di AppointmentItem in DTO. */
    List<AppointmentItemDto> toDtoList(List<AppointmentItem> entities);

    /**
     * Converte AppointmentItemDto in una nuova Entity AppointmentItem.
     *
     * Le relazioni vengono ignorate perché devono essere risolte
     * nel Service tramite gli ID presenti nel DTO.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appointment", ignore = true)
    @Mapping(target = "salonProduct", ignore = true)
    @Mapping(target = "employee", ignore = true)
    AppointmentItem toEntity(AppointmentItemDto dto);

    /** Converte una lista di AppointmentItemDto in Entity. */
    List<AppointmentItem> toEntityList(List<AppointmentItemDto> dtos);
}
