package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.generation.hairlab.dto.ColorFormulaDto;
import com.generation.hairlab.model.ColorFormula;

/**
 * Mapper MapStruct utilizzato per convertire ColorFormula
 * in ColorFormulaDto e viceversa.
 *
 * Le relazioni con Consultation e AppointmentItem vengono rappresentate
 * nel DTO tramite identificativi.
 */
@Mapper(componentModel = "spring")
public interface ColorFormulaMapper {

    /** Converte ColorFormula in ColorFormulaDto. */
    @Mapping(target = "consultationId", source = "consultation.id")
    @Mapping(target = "appointmentItemId", source = "appointmentItem.id")
    ColorFormulaDto toDto(ColorFormula entity);

    /** Converte una lista di ColorFormula in DTO. */
    List<ColorFormulaDto> toDtoList(List<ColorFormula> entities);

    /**
     * Converte ColorFormulaDto in una nuova Entity ColorFormula.
     *
     * consultation e appointmentItem vengono ignorati perché devono
     * essere risolti nel Service. createdAt viene gestito dal backend.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "consultation", ignore = true)
    @Mapping(target = "appointmentItem", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ColorFormula toEntity(ColorFormulaDto dto);

    /** Converte una lista di ColorFormulaDto in Entity. */
    List<ColorFormula> toEntityList(List<ColorFormulaDto> dtos);
}
