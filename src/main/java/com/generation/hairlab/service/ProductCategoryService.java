package com.generation.hairlab.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.hairlab.dto.ProductCategoryDto;
import com.generation.hairlab.mapper.ProductCategoryMapper;
import com.generation.hairlab.model.ProductCategory;
import com.generation.hairlab.model.SalonProduct;
import com.generation.hairlab.repository.ProductCategoryRepository;
import com.generation.hairlab.repository.SalonProductRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato alle categorie del listino.
 *
 * La cancellazione è logica: una categoria viene disattivata
 * senza rimuovere il record dal database.
 */
@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    /** Repository delle categorie. */
    private final ProductCategoryRepository productCategoryRepository;

    /** Repository dei servizi collegati alla categoria. */
    private final SalonProductRepository salonProductRepository;

    /** Mapper ProductCategory <-> ProductCategoryDto. */
    private final ProductCategoryMapper productCategoryMapper;

    /** Restituisce tutte le categorie. */
    @Transactional(readOnly = true)
    public List<ProductCategoryDto> findAll() {
        return productCategoryMapper.toDtoList(
            productCategoryRepository.findAll()
        );
    }

    /** Restituisce solamente le categorie attive. */
    @Transactional(readOnly = true)
    public List<ProductCategoryDto> findActive() {
        return productCategoryMapper.toDtoList(
            productCategoryRepository.findByActiveTrue()
        );
    }

    /** Cerca una categoria tramite ID. */
    @Transactional(readOnly = true)
    public ProductCategoryDto findById(Integer id)
            throws ServiceException {
        return productCategoryMapper.toDto(
            getProductCategoryById(id)
        );
    }

    /** Inserisce una categoria verificando l'unicità del nome. */
    @Transactional
    public ProductCategoryDto insert(ProductCategoryDto dto)
            throws ServiceException {

        String normalizedName = normalizeText(dto.getName());

        if (productCategoryRepository.existsByNameIgnoreCase(normalizedName)) {
            throw new ServiceException(
                "Esiste già una categoria con questo nome",
                HttpStatus.CONFLICT
            );
        }

        ProductCategory category = productCategoryMapper.toEntity(dto);
        category.setName(normalizedName);
        category.setDescription(normalizeNullableText(dto.getDescription()));

        return productCategoryMapper.toDto(
            productCategoryRepository.save(category)
        );
    }

    /** Aggiorna una categoria esistente. */
    @Transactional
    public ProductCategoryDto update(
            Integer id,
            ProductCategoryDto dto)
            throws ServiceException {

        ProductCategory category = getProductCategoryById(id);
        String normalizedName = normalizeText(dto.getName());

        ProductCategory sameName = productCategoryRepository
            .findByNameIgnoreCase(normalizedName)
            .orElse(null);

        if (sameName != null && !sameName.getId().equals(id)) {
            throw new ServiceException(
                "Esiste già un'altra categoria con questo nome",
                HttpStatus.CONFLICT
            );
        }

        category.setName(normalizedName);
        category.setDescription(normalizeNullableText(dto.getDescription()));
        category.setActive(dto.isActive());

        /*
         * Una categoria disattivata non deve lasciare servizi
         * ancora selezionabili come attivi.
         */
        if (!category.isActive()) {
            deactivateActiveProducts(category.getId());
        }

        return productCategoryMapper.toDto(
            productCategoryRepository.save(category)
        );
    }

    /**
     * Disattiva logicamente la categoria.
     *
     * Vengono disattivati anche i SalonProduct attivi appartenenti
     * alla categoria, evitando un catalogo incoerente.
     */
    @Transactional
    public void delete(Integer id)
            throws ServiceException {

        ProductCategory category = getProductCategoryById(id);

        if (!category.isActive()) {
            return;
        }

        category.setActive(false);
        deactivateActiveProducts(id);
        productCategoryRepository.save(category);
    }

    /**
     * Riattiva la categoria.
     *
     * I servizi precedentemente disattivati non vengono riattivati
     * automaticamente: la riattivazione dei singoli servizi resta esplicita.
     */
    @Transactional
    public ProductCategoryDto activate(Integer id)
            throws ServiceException {

        ProductCategory category = getProductCategoryById(id);
        category.setActive(true);

        return productCategoryMapper.toDto(
            productCategoryRepository.save(category)
        );
    }

    /** Restituisce la Entity ProductCategory tramite ID. */
    @Transactional(readOnly = true)
    public ProductCategory getProductCategoryById(Integer id)
            throws ServiceException {

        return productCategoryRepository.findById(id)
            .orElseThrow(
                () -> new ServiceException(
                    "Categoria non trovata con id: " + id,
                    HttpStatus.NOT_FOUND
                )
            );
    }

    /** Disattiva i servizi ancora attivi appartenenti alla categoria. */
    private void deactivateActiveProducts(Integer categoryId) {

        List<SalonProduct> activeProducts = salonProductRepository
            .findByProductCategory_IdAndActiveTrue(categoryId);

        for (SalonProduct product : activeProducts) {
            product.setActive(false);
        }

        if (!activeProducts.isEmpty()) {
            salonProductRepository.saveAll(activeProducts);
        }
    }

    private String normalizeText(String value) {
        return value == null
            ? null
            : value.trim();
    }

    private String normalizeNullableText(String value) {
        if (value == null) {
            return null;
        }

        String normalized = value.trim();

        return normalized.isEmpty()
            ? null
            : normalized;
    }
}
