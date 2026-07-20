package com.generation.hairlab.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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

    /** Converte ColorFormulaItem in ColorFormulaItemDto. */
    @Mapping(target = "colorFormulaId", source = "colorFormula.id")
    @Mapping(target = "hairDyeIds", source = "hairDyes")
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

    /**
     * Metodo di supporto per convertire Set<HairDye> in Set<Integer>.
     *
     * @param hairDyes tinte associate all'item
     * @return insieme degli identificativi delle tinte
     */
    default Set<Integer> mapHairDyesToIds(Set<HairDye> hairDyes) {
        if (hairDyes == null) {
            return Collections.emptySet();
        }

        return hairDyes.stream()
                .map(HairDye::getId)
                .collect(Collectors.toSet());
    }
}
