package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.generation.hairlab.dto.ProductCategoryDto;
import com.generation.hairlab.model.ProductCategory;

/**
 * Mapper MapStruct per ProductCategory.
 *
 * Entity e DTO hanno gli stessi campi funzionali, quindi la conversione
 * principale viene generata automaticamente da MapStruct.
 */
@Mapper(config = HairLabMapperConfig.class)
public interface ProductCategoryMapper {

    /** Converte ProductCategory in ProductCategoryDto. */
    ProductCategoryDto toDto(ProductCategory entity);

    /** Converte una lista di categorie in DTO. */
    List<ProductCategoryDto> toDtoList(List<ProductCategory> entities);

    /** Crea una nuova ProductCategory ignorando l'ID ricevuto. */
    @Mapping(target = "id", ignore = true)
    ProductCategory toEntity(ProductCategoryDto dto);

    /** Aggiorna una categoria esistente senza modificarne l'ID. */
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(ProductCategoryDto dto, @MappingTarget ProductCategory entity);
}
