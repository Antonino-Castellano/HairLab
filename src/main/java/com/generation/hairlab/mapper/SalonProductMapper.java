package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.generation.hairlab.dto.SalonProductDto;
import com.generation.hairlab.model.SalonProduct;

/**
 * Mapper MapStruct utilizzato per convertire SalonProduct
 * in SalonProductDto e viceversa.
 *
 * La relazione ManyToOne con ProductCategory viene rappresentata nel DTO
 * tramite productCategoryId. Nel percorso inverso la categoria viene ignorata
 * perché deve essere recuperata dal Service.
 */
@Mapper(componentModel = "spring")
public interface SalonProductMapper {

    /** Converte SalonProduct in SalonProductDto. */
    @Mapping(target = "productCategoryId", source = "productCategory.id")
    @Mapping(target = "description", source = "description")
    SalonProductDto toDto(SalonProduct entity);

    /** Converte una lista di SalonProduct in DTO. */
    List<SalonProductDto> toDtoList(List<SalonProduct> entities);

    /**
     * Converte SalonProductDto in una nuova Entity SalonProduct.
     *
     * ProductCategory deve essere impostata nel Service usando
     * productCategoryId.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productCategory", ignore = true)
    @Mapping(target = "description", source = "description")
    SalonProduct toEntity(SalonProductDto dto);

    /** Converte una lista di SalonProductDto in Entity. */
    List<SalonProduct> toEntityList(List<SalonProductDto> dtos);
}