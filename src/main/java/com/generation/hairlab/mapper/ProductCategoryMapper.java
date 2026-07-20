package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.generation.hairlab.dto.ProductCategoryDto;
import com.generation.hairlab.model.ProductCategory;

/**
 * Mapper MapStruct utilizzato per convertire ProductCategory
 * in ProductCategoryDto e viceversa.
 */
@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {

    /** Converte ProductCategory in ProductCategoryDto. */
    ProductCategoryDto toDto(ProductCategory entity);

    /** Converte una lista di ProductCategory in DTO. */
    List<ProductCategoryDto> toDtoList(List<ProductCategory> entities);

    /** Converte ProductCategoryDto in una nuova Entity ProductCategory. */
    @Mapping(target = "id", ignore = true)
    ProductCategory toEntity(ProductCategoryDto dto);

    /** Converte una lista di ProductCategoryDto in Entity. */
    List<ProductCategory> toEntityList(List<ProductCategoryDto> dtos);
}
