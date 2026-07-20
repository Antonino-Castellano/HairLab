package com.generation.hairlab.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.generation.hairlab.dto.ProductCategoryDto;
import com.generation.hairlab.mapper.ProductCategoryMapper;
import com.generation.hairlab.model.ProductCategory;
import com.generation.hairlab.repository.ProductCategoryRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato alla gestione delle categorie del listino.
 */
@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    /** Repository delle categorie. */
    private ProductCategoryRepository productCategoryRepository;

    /** Mapper ProductCategory <-> ProductCategoryDto. */
    private ProductCategoryMapper productCategoryMapper;

    /** Restituisce tutte le categorie. */
    public List<ProductCategoryDto> findAll() {
        return productCategoryMapper.toDtoList(productCategoryRepository.findAll());
    }

    /** Restituisce solamente le categorie attive. */
    public List<ProductCategoryDto> findActive() {
        return productCategoryMapper.toDtoList(productCategoryRepository.findByActiveTrue());
    }

    /** Cerca una categoria tramite ID. */
    public ProductCategoryDto findById(Integer id) throws ServiceException {
        return productCategoryMapper.toDto(getProductCategoryById(id));
    }

    /**
     * Inserisce una nuova categoria verificando l'unicità del nome.
     */
    public ProductCategoryDto insert(ProductCategoryDto dto) throws ServiceException {

        if (productCategoryRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new ServiceException("Esiste già una categoria con questo nome");
        }

        ProductCategory category = productCategoryMapper.toEntity(dto);

        return productCategoryMapper.toDto(productCategoryRepository.save(category));
    }

    /** Aggiorna una categoria esistente. */
    public ProductCategoryDto update(Integer id, ProductCategoryDto dto)
            throws ServiceException {

        ProductCategory category = getProductCategoryById(id);

        ProductCategory sameName = productCategoryRepository
                .findByNameIgnoreCase(dto.getName())
                .orElse(null);

        if (sameName != null && !sameName.getId().equals(id)) {
            throw new ServiceException("Esiste già un'altra categoria con questo nome");
        }

        category.setName(dto.getName());
        category.setDesc(dto.getDesc());
        category.setActive(dto.isActive());

        return productCategoryMapper.toDto(productCategoryRepository.save(category));
    }

    /** Elimina una categoria esistente. */
    public void delete(Integer id) throws ServiceException {
        productCategoryRepository.delete(getProductCategoryById(id));
    }

    /** Restituisce la Entity ProductCategory tramite ID. */
    public ProductCategory getProductCategoryById(Integer id) throws ServiceException {
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        "Categoria non trovata con id: " + id));
    }
}
