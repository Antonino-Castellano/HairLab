package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.generation.hairlab.dto.ColorFormulaItemDto;
import com.generation.hairlab.model.ColorFormulaItem;
import com.generation.hairlab.model.HairDye;

/**
 * Mapper MapStruct dedicato
 * agli elementi delle formule colore.
 *
 * Le relazioni vengono rappresentate nel DTO tramite:
 *
 * colorFormula.id
 * ->
 * colorFormulaId
 *
 * Set<HairDye>
 * ->
 * Set<Integer> hairDyeIds
 */
@Mapper(
    componentModel = "spring"
)
public interface ColorFormulaItemMapper {

    /**
     * Converte la Entity nel DTO.
     *
     * Il Set<HairDye> viene convertito
     * in Set<Integer> tramite
     * mapHairDyeToId().
     */
    @Mapping(
        target = "colorFormulaId",
        source = "colorFormula.id"
    )
    @Mapping(
        target = "hairDyeIds",
        source = "hairDyes"
    )
    ColorFormulaItemDto toDto(
        ColorFormulaItem entity
    );

    /**
     * Converte una lista di Entity
     * in una lista di DTO.
     */
    List<ColorFormulaItemDto> toDtoList(
        List<ColorFormulaItem> entities
    );

    /**
     * Converte il DTO in una nuova Entity.
     *
     * Le relazioni non vengono create dal Mapper:
     * vengono recuperate dal database
     * dal ColorFormulaItemService.
     */
    @Mapping(
        target = "id",
        ignore = true
    )
    @Mapping(
        target = "colorFormula",
        ignore = true
    )
    @Mapping(
        target = "hairDyes",
        ignore = true
    )
    ColorFormulaItem toEntity(
        ColorFormulaItemDto dto
    );

    /**
     * Converte una lista di DTO
     * in una lista di Entity.
     */
    List<ColorFormulaItem> toEntityList(
        List<ColorFormulaItemDto> dtos
    );

    /**
     * Conversione elementare:
     *
     * HairDye
     * ->
     * Integer ID
     *
     * MapStruct utilizza automaticamente
     * questo metodo quando deve convertire
     * Set<HairDye> in Set<Integer>.
     */
    default Integer mapHairDyeToId(
        HairDye hairDye
    ) {

        if (hairDye == null) {

            return null;
        }

        return hairDye.getId();
    }
}