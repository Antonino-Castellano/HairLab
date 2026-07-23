package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.generation.hairlab.dto.ColorFormulaItemDto;
import com.generation.hairlab.model.ColorFormulaItem;

/**
 * Mapper degli ingredienti di una formula.
 *
 * Le relazioni vengono rappresentate tramite:
 *
 * colorFormula.id -> colorFormulaId
 * hairDye.id      -> hairDyeId
 */
@Mapper(componentModel = "spring")
public interface ColorFormulaItemMapper {

    @Mapping(
        target = "colorFormulaId",
        source = "colorFormula.id"
    )
    @Mapping(
        target = "hairDyeId",
        source = "hairDye.id"
    )
    ColorFormulaItemDto toDto(
        ColorFormulaItem entity
    );

    List<ColorFormulaItemDto> toDtoList(
        List<ColorFormulaItem> entities
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "colorFormula", ignore = true)
    @Mapping(target = "hairDye", ignore = true)
    ColorFormulaItem toEntity(
        ColorFormulaItemDto dto
    );

    List<ColorFormulaItem> toEntityList(
        List<ColorFormulaItemDto> dtos
    );
}
