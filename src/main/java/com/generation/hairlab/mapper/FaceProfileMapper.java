package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.generation.hairlab.dto.FaceProfileDto;
import com.generation.hairlab.model.FaceProfile;

/**
 * Mapper MapStruct per FaceProfile.
 *
 * Customer viene rappresentato nel DTO tramite customerId.
 */
@Mapper(componentModel = "spring")
public interface FaceProfileMapper {

    /**
     * Entity -> DTO.
     */
    @Mapping(
        target = "customerId",
        source = "customer.id"
    )
    FaceProfileDto toDto(
        FaceProfile entity
    );

    /**
     * Lista Entity -> lista DTO.
     */
    List<FaceProfileDto> toDtoList(
        List<FaceProfile> entities
    );

    /**
     * DTO -> nuova Entity.
     *
     * Customer viene recuperato dal Service.
     */
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
    FaceProfile toEntity(
        FaceProfileDto dto
    );

    List<FaceProfile> toEntityList(
        List<FaceProfileDto> dtos
    );
}