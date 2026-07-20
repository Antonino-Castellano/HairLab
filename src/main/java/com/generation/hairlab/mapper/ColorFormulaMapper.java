package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.generation.hairlab.dto.ColorFormulaDto;
import com.generation.hairlab.model.ColorFormula;

/**
 * Mapper MapStruct per ColorFormula.
 *
 * Le relazioni con Consultation e AppointmentItem vengono esposte nel DTO
 * come identificativi. Nel percorso DTO -> Entity tali relazioni sono
 * ignorate e devono essere risolte nel Service.
 */
@Mapper(config = HairLabMapperConfig.class)
public interface ColorFormulaMapper {

    /** Converte ColorFormula in ColorFormulaDto. */
    @Mapping(target = "consultationId", source = "consultation.id")
    @Mapping(target = "appointmentItemId", source = "appointmentItem.id")
    ColorFormulaDto toDto(ColorFormula entity);

    /** Converte una lista di ColorFormula in DTO. */
    List<ColorFormulaDto> toDtoList(List<ColorFormula> entities);

    /**
     * Crea una ColorFormula dai dati del DTO.
     *
     * createdAt viene ignorato perché deve essere valorizzato dal backend.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "consultation", ignore = true)
    @Mapping(target = "appointmentItem", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ColorFormula toEntity(ColorFormulaDto dto);

    /**
     * Aggiorna una formula senza cambiare automaticamente ID,
     * relazioni o data originaria di creazione.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "consultation", ignore = true)
    @Mapping(target = "appointmentItem", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(ColorFormulaDto dto, @MappingTarget ColorFormula entity);
}
