package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.generation.hairlab.dto.HairDyeInventoryDto;
import com.generation.hairlab.model.HairDyeInventory;

/**
 * Mapper magazzino Color Lab.
 */
@Mapper(componentModel = "spring")
public interface HairDyeInventoryMapper {

    @Mapping(
        target = "hairDyeId",
        source = "hairDye.id"
    )
    HairDyeInventoryDto toDto(
        HairDyeInventory entity
    );

    List<HairDyeInventoryDto> toDtoList(
        List<HairDyeInventory> entities
    );

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hairDye", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    HairDyeInventory toEntity(
        HairDyeInventoryDto dto
    );

    List<HairDyeInventory> toEntityList(
        List<HairDyeInventoryDto> dtos
    );
}
