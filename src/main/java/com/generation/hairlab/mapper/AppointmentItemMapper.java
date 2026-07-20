package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.generation.hairlab.dto.AppointmentItemDto;
import com.generation.hairlab.model.AppointmentItem;

/**
 * Mapper MapStruct per AppointmentItem.
 *
 * Le tre relazioni ManyToOne vengono rappresentate nel DTO tramite:
 * - appointmentId
 * - salonProductId
 * - employeeId
 *
 * Nel percorso inverso le Entity collegate vengono ignorate: il Service
 * deve recuperarle dai rispettivi Repository e assegnarle all'item.
 */
@Mapper(config = HairLabMapperConfig.class)
public interface AppointmentItemMapper {

    /** Converte AppointmentItem in AppointmentItemDto. */
    @Mapping(target = "appointmentId", source = "appointment.id")
    @Mapping(target = "salonProductId", source = "salonProduct.id")
    @Mapping(target = "employeeId", source = "employee.id")
    AppointmentItemDto toDto(AppointmentItem entity);

    /** Converte una lista di AppointmentItem in DTO. */
    List<AppointmentItemDto> toDtoList(List<AppointmentItem> entities);

    /** Crea un AppointmentItem senza risolvere automaticamente le relazioni. */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appointment", ignore = true)
    @Mapping(target = "salonProduct", ignore = true)
    @Mapping(target = "employee", ignore = true)
    AppointmentItem toEntity(AppointmentItemDto dto);

    /** Aggiorna l'item senza modificare automaticamente ID e relazioni. */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appointment", ignore = true)
    @Mapping(target = "salonProduct", ignore = true)
    @Mapping(target = "employee", ignore = true)
    void updateEntityFromDto(AppointmentItemDto dto, @MappingTarget AppointmentItem entity);
}
