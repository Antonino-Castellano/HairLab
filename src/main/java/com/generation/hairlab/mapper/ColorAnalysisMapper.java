package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.generation.hairlab.dto.ColorAnalysisDto;
import com.generation.hairlab.model.ColorAnalysis;

/**
 * Mapper MapStruct per ColorAnalysis.
 */
@Mapper(componentModel = "spring")
public interface ColorAnalysisMapper {

    @Mapping(
        target = "customerId",
        source = "customer.id"
    )
    ColorAnalysisDto toDto(
        ColorAnalysis entity
    );

    List<ColorAnalysisDto> toDtoList(
        List<ColorAnalysis> entities
    );

    @Mapping(
        target = "id",
        ignore = true
    )
    @Mapping(
        target = "customer",
        ignore = true
    )
    @Mapping(
        target = "createdAt",
        ignore = true
    )
    @Mapping(
        target = "updatedAt",
        ignore = true
    )
    ColorAnalysis toEntity(
        ColorAnalysisDto dto
    );

    List<ColorAnalysis> toEntityList(
        List<ColorAnalysisDto> dtos
    );
}