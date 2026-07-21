package com.generation.hairlab.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.generation.hairlab.dto.SalonProductDto;
import com.generation.hairlab.mapper.SalonProductMapper;
import com.generation.hairlab.model.ProductCategory;
import com.generation.hairlab.model.SalonProduct;
import com.generation.hairlab.repository.ProductCategoryRepository;
import com.generation.hairlab.repository.SalonProductRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato ai servizi/prodotti presenti nel listino del salone.
 *
 * Risolve la relazione con ProductCategory tramite productCategoryId
 * contenuto nel DTO.
 */
@Service
@RequiredArgsConstructor
public class SalonProductService {

    /** Repository dei servizi del salone. */
    private final SalonProductRepository salonProductRepository;

    /** Repository necessario per risolvere productCategoryId. */
    private final ProductCategoryRepository productCategoryRepository;

    /** Mapper SalonProduct <-> SalonProductDto. */
    private final SalonProductMapper salonProductMapper;

    /** Restituisce tutti i servizi/prodotti. */
    public List<SalonProductDto> findAll() {
        return salonProductMapper.toDtoList(salonProductRepository.findAll());
    }

    /** Restituisce solamente i servizi/prodotti attivi. */
    public List<SalonProductDto> findActive() {
        return salonProductMapper.toDtoList(salonProductRepository.findByActiveTrue());
    }

    /** Cerca un servizio tramite ID. */
    public SalonProductDto findById(Integer id) throws ServiceException {
        return salonProductMapper.toDto(getSalonProductById(id));
    }

    /**
     * Inserisce un nuovo SalonProduct.
     *
     * Il Mapper converte i campi semplici; il Service recupera la vera
     * ProductCategory dal database e costruisce la relazione.
     */
    public SalonProductDto insert(SalonProductDto dto) throws ServiceException {

        if (salonProductRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new ServiceException("Esiste già un servizio con questo nome");
        }

        ProductCategory category = getCategory(dto.getProductCategoryId());

        SalonProduct product = salonProductMapper.toEntity(dto);
        product.setProductCategory(category);

        return salonProductMapper.toDto(salonProductRepository.save(product));
    }

    /** Aggiorna un SalonProduct esistente. */
    public SalonProductDto update(Integer id, SalonProductDto dto)
            throws ServiceException {

        SalonProduct product = getSalonProductById(id);

        SalonProduct sameName = salonProductRepository
                .findByNameIgnoreCase(dto.getName())
                .orElse(null);

        if (sameName != null && !sameName.getId().equals(id)) {
            throw new ServiceException("Esiste già un altro servizio con questo nome");
        }

        ProductCategory category = getCategory(dto.getProductCategoryId());

        product.setProductCategory(category);
        product.setName(dto.getName());
        product.setDescription(dto.getDesc());
        product.setDuration(dto.getDuration());
        product.setBasePrice(dto.getBasePrice());
        product.setActive(dto.isActive());

        return salonProductMapper.toDto(salonProductRepository.save(product));
    }

    /** Elimina un SalonProduct esistente. */
    public void delete(Integer id) throws ServiceException {
        salonProductRepository.delete(getSalonProductById(id));
    }

    /** Restituisce i servizi appartenenti a una categoria. */
    public List<SalonProductDto> findByCategory(Integer categoryId) {
        return salonProductMapper.toDtoList(
                salonProductRepository.findByProductCategory_Id(categoryId));
    }

    /** Restituisce la Entity SalonProduct tramite ID. */
    public SalonProduct getSalonProductById(Integer id) throws ServiceException {
        return salonProductRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        "Servizio non trovato con id: " + id));
    }

    /** Recupera la categoria necessaria alla relazione ManyToOne. */
    private ProductCategory getCategory(Integer categoryId) throws ServiceException {
        return productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ServiceException(
                        "Categoria non trovata con id: " + categoryId));
    }
}
