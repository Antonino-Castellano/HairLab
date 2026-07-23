package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.generation.hairlab.dto.ColorFormulaDto;
import com.generation.hairlab.model.ColorFormula;

/**
 * Mapper ColorFormula <-> ColorFormulaDto.
 *
 * Tutte le relazioni JPA vengono rappresentate
 * nel DTO tramite identificativi.
 */
@Mapper(componentModel = "spring")
public interface ColorFormulaMapper {

    @Mapping(
        target = "customerId",
        source = "customer.id"
    )
    @Mapping(
        target = "consultationId",
        source = "consultation.id"
    )
    @Mapping(
        target = "appointmentItemId",
        source = "appointmentItem.id"
    )
    ColorFormulaDto toDto(
        ColorFormula entity
    );

    List<ColorFormulaDto> toDtoList(
        List<ColorFormula> entities
    );

    /**
     * Le Entity collegate vengono risolte nel Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "consultation", ignore = true)
    @Mapping(target = "appointmentItem", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ColorFormula toEntity(
        ColorFormulaDto dto
    );

    List<ColorFormula> toEntityList(
        List<ColorFormulaDto> dtos
    );
}
