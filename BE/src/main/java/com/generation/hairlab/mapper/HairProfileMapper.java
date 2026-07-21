package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.generation.hairlab.dto.HairProfileDto;
import com.generation.hairlab.model.HairProfile;

/**
 * Mapper MapStruct utilizzato per convertire HairProfile in HairProfileDto
 * e viceversa.
 *
 * La relazione OneToOne con Customer viene rappresentata nel DTO tramite
 * customerId. Nel percorso DTO -> Entity il Customer viene ignorato perché
 * deve essere recuperato dal database nel Service.
 */
@Mapper(componentModel = "spring")
public interface HairProfileMapper {

    /** Converte HairProfile in HairProfileDto. */
    @Mapping(target = "customerId", source = "customer.id")
    HairProfileDto toDto(HairProfile entity);

    /** Converte una lista di HairProfile in una lista di HairProfileDto. */
    List<HairProfileDto> toDtoList(List<HairProfile> entities);

    /**
     * Converte HairProfileDto in una nuova Entity HairProfile.
     *
     * Il Customer non viene creato dal mapper perché customerId deve essere
     * risolto dal Service tramite CustomerRepository.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    HairProfile toEntity(HairProfileDto dto);

    /** Converte una lista di HairProfileDto in una lista di HairProfile. */
    List<HairProfile> toEntityList(List<HairProfileDto> dtos);
}
