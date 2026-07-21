package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.generation.hairlab.dto.ColorFormulaItemDto;
import com.generation.hairlab.model.ColorFormulaItem;
import com.generation.hairlab.model.HairDye;

/**
 * Mapper MapStruct utilizzato per convertire ColorFormulaItem
 * in ColorFormulaItemDto e viceversa.
 *
 * La relazione con ColorFormula viene rappresentata tramite colorFormulaId.
 * Il Set di HairDye viene trasformato in un Set di identificativi.
 *
 * Nel percorso inverso le relazioni vengono ignorate perché devono essere
 * recuperate dal database nel Service.
 */
@Mapper(componentModel = "spring")
public interface ColorFormulaItemMapper {

    @Named("hairDyeToId")
    default Integer hairDyeToId(HairDye hairDye) {
        return hairDye != null ? hairDye.getId() : null;
    }

    /** Converte ColorFormulaItem in ColorFormulaItemDto. */
    @Mapping(target = "colorFormulaId", source = "colorFormula.id")
    @Mapping(target = "hairDyeIds", source = "hairDyes", qualifiedByName = "hairDyeToId")
    ColorFormulaItemDto toDto(ColorFormulaItem entity);

    /** Converte una lista di ColorFormulaItem in DTO. */
    List<ColorFormulaItemDto> toDtoList(List<ColorFormulaItem> entities);

    /**
     * Converte ColorFormulaItemDto in una nuova Entity ColorFormulaItem.
     *
     * ColorFormula e HairDye vengono ignorati perché devono essere
     * recuperati tramite Repository nel Service.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "colorFormula", ignore = true)
    @Mapping(target = "hairDyes", ignore = true)
    ColorFormulaItem toEntity(ColorFormulaItemDto dto);

    /** Converte una lista di ColorFormulaItemDto in Entity. */
    List<ColorFormulaItem> toEntityList(List<ColorFormulaItemDto> dtos);

}
