package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.generation.hairlab.dto.HairDyeDto;
import com.generation.hairlab.model.HairDye;

/**
 * Mapper MapStruct per HairDye.
 *
 * I campi tecnici, inclusi enum come ProductType, ToneLevel e Reflection,
 * hanno gli stessi tipi in Entity e DTO e vengono quindi mappati
 * automaticamente da MapStruct.
 */
@Mapper(config = HairLabMapperConfig.class)
public interface HairDyeMapper {

    /** Converte HairDye in HairDyeDto. */
    HairDyeDto toDto(HairDye entity);

    /** Converte una lista di HairDye in DTO. */
    List<HairDyeDto> toDtoList(List<HairDye> entities);

    /** Crea una nuova HairDye ignorando l'ID. */
    @Mapping(target = "id", ignore = true)
    HairDye toEntity(HairDyeDto dto);

    /** Aggiorna una HairDye esistente senza modificarne l'ID. */
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(HairDyeDto dto, @MappingTarget HairDye entity);
}
