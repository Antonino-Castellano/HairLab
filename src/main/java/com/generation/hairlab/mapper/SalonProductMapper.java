package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.generation.hairlab.dto.SalonProductDto;
import com.generation.hairlab.model.SalonProduct;

/**
 * Mapper MapStruct per SalonProduct.
 *
 * La relazione ManyToOne con ProductCategory viene esposta nel DTO
 * tramite productCategoryId.
 *
 * Nel percorso DTO -> Entity la categoria viene ignorata perché deve essere
 * caricata dal database dal Service tramite ProductCategoryRepository.
 */
@Mapper(config = HairLabMapperConfig.class)
public interface SalonProductMapper {

    /** Converte SalonProduct in SalonProductDto. */
    @Mapping(target = "productCategoryId", source = "productCategory.id")
    SalonProductDto toDto(SalonProduct entity);

    /** Converte una lista di SalonProduct in DTO. */
    List<SalonProductDto> toDtoList(List<SalonProduct> entities);

    /** Crea un SalonProduct senza costruire automaticamente la categoria. */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productCategory", ignore = true)
    SalonProduct toEntity(SalonProductDto dto);

    /** Aggiorna un SalonProduct senza cambiare automaticamente ID o categoria. */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productCategory", ignore = true)
    void updateEntityFromDto(SalonProductDto dto, @MappingTarget SalonProduct entity);
}
