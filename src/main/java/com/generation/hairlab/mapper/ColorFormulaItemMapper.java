package com.generation.hairlab.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.generation.hairlab.dto.ColorFormulaItemDto;
import com.generation.hairlab.model.ColorFormulaItem;
import com.generation.hairlab.model.HairDye;

/**
 * Mapper MapStruct per ColorFormulaItem.
 *
 * La relazione con ColorFormula viene rappresentata tramite colorFormulaId.
 * Il Set<HairDye> viene trasformato in Set<Integer> contenente soltanto
 * gli identificativi delle tinte.
 *
 * Nel percorso DTO -> Entity le relazioni vengono ignorate perché il Service
 * deve recuperare ColorFormula e HairDye reali dai rispettivi Repository.
 */
@Mapper(config = HairLabMapperConfig.class)
public interface ColorFormulaItemMapper {

    /** Converte ColorFormulaItem in ColorFormulaItemDto. */
    @Mapping(target = "colorFormulaId", source = "colorFormula.id")
    @Mapping(target = "hairDyeIds", source = "hairDyes")
    ColorFormulaItemDto toDto(ColorFormulaItem entity);

    /** Converte una lista di ColorFormulaItem in DTO. */
    List<ColorFormulaItemDto> toDtoList(List<ColorFormulaItem> entities);

    /** Crea un item senza risolvere automaticamente formula e tinte. */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "colorFormula", ignore = true)
    @Mapping(target = "hairDyes", ignore = true)
    ColorFormulaItem toEntity(ColorFormulaItemDto dto);

    /** Aggiorna i dati semplici dell'item lasciando le relazioni al Service. */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "colorFormula", ignore = true)
    @Mapping(target = "hairDyes", ignore = true)
    void updateEntityFromDto(ColorFormulaItemDto dto, @MappingTarget ColorFormulaItem entity);

    /**
     * Converte un insieme di Entity HairDye nei rispettivi identificativi.
     *
     * @param hairDyes tinte associate all'item
     * @return insieme degli ID
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
