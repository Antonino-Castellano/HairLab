package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.generation.hairlab.dto.HairDyeDto;
import com.generation.hairlab.model.HairDye;

/**
 * Mapper MapStruct utilizzato per convertire HairDye
 * in HairDyeDto e viceversa.
 *
 * I campi tecnici e gli enum hanno gli stessi nomi e tipi
 * nella Entity e nel DTO.
 */
@Mapper(componentModel = "spring")
public interface HairDyeMapper {

    /** Converte HairDye in HairDyeDto. */
    HairDyeDto toDto(HairDye entity);

    /** Converte una lista di HairDye in DTO. */
    List<HairDyeDto> toDtoList(List<HairDye> entities);

    /** Converte HairDyeDto in una nuova Entity HairDye. */
    @Mapping(target = "id", ignore = true)
    HairDye toEntity(HairDyeDto dto);

    /** Converte una lista di HairDyeDto in Entity. */
    List<HairDye> toEntityList(List<HairDyeDto> dtos);
}
