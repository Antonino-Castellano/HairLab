package com.generation.hairlab.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * Risolve la relazione con ProductCategory e usa una cancellazione
 * logica per preservare gli AppointmentItem storici.
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
    @Transactional(readOnly = true)
    public List<SalonProductDto> findAll() {
        return salonProductMapper.toDtoList(
            salonProductRepository.findAll()
        );
    }

    /** Restituisce solamente i servizi/prodotti attivi. */
    @Transactional(readOnly = true)
    public List<SalonProductDto> findActive() {
        return salonProductMapper.toDtoList(
            salonProductRepository.findByActiveTrue()
        );
    }

    /** Cerca un servizio tramite ID. */
    @Transactional(readOnly = true)
    public SalonProductDto findById(Integer id)
            throws ServiceException {
        return salonProductMapper.toDto(
            getSalonProductById(id)
        );
    }

    /** Inserisce un nuovo servizio/prodotto. */
    @Transactional
    public SalonProductDto insert(SalonProductDto dto)
            throws ServiceException {

        String normalizedName = normalizeText(dto.getName());

        if (salonProductRepository.existsByNameIgnoreCase(normalizedName)) {
            throw new ServiceException(
                "Esiste già un servizio con questo nome",
                HttpStatus.CONFLICT
            );
        }

        ProductCategory category = getCategory(dto.getProductCategoryId());
        validateCategoryForState(category, dto.isActive());
        validateCommercialValues(dto);

        SalonProduct product = salonProductMapper.toEntity(dto);

        product.setProductCategory(category);
        product.setName(normalizedName);
        product.setDescription(normalizeNullableText(dto.getDescription()));

        return salonProductMapper.toDto(
            salonProductRepository.save(product)
        );
    }

    /** Aggiorna un SalonProduct esistente. */
    @Transactional
    public SalonProductDto update(
            Integer id,
            SalonProductDto dto)
            throws ServiceException {

        SalonProduct product = getSalonProductById(id);
        String normalizedName = normalizeText(dto.getName());

        SalonProduct sameName = salonProductRepository
            .findByNameIgnoreCase(normalizedName)
            .orElse(null);

        if (sameName != null && !sameName.getId().equals(id)) {
            throw new ServiceException(
                "Esiste già un altro servizio con questo nome",
                HttpStatus.CONFLICT
            );
        }

        ProductCategory category = getCategory(dto.getProductCategoryId());
        validateCategoryForState(category, dto.isActive());
        validateCommercialValues(dto);

        product.setProductCategory(category);
        product.setName(normalizedName);
        product.setDescription(normalizeNullableText(dto.getDescription()));
        product.setDuration(dto.getDuration());
        product.setBasePrice(dto.getBasePrice());
        product.setActive(dto.isActive());

        return salonProductMapper.toDto(
            salonProductRepository.save(product)
        );
    }

    /**
     * Disattiva logicamente un servizio.
     *
     * Gli AppointmentItem già registrati continuano a conservare
     * il riferimento allo stesso servizio storico.
     */
    @Transactional
    public void delete(Integer id)
            throws ServiceException {

        SalonProduct product = getSalonProductById(id);

        if (!product.isActive()) {
            return;
        }

        product.setActive(false);
        salonProductRepository.save(product);
    }

    /** Riattiva un servizio solo se la categoria è attiva. */
    @Transactional
    public SalonProductDto activate(Integer id)
            throws ServiceException {

        SalonProduct product = getSalonProductById(id);
        ProductCategory category = product.getProductCategory();

        if (category == null) {
            throw new ServiceException(
                "Il servizio non possiede una categoria valida",
                HttpStatus.CONFLICT
            );
        }

        validateCategoryForState(category, true);

        product.setActive(true);

        return salonProductMapper.toDto(
            salonProductRepository.save(product)
        );
    }

    /** Restituisce i servizi appartenenti a una categoria. */
    @Transactional(readOnly = true)
    public List<SalonProductDto> findByCategory(Integer categoryId)
            throws ServiceException {

        /*
         * Verifichiamo prima l'esistenza della categoria,
         * così un ID inesistente produce 404 e non una lista vuota ambigua.
         */
        getCategory(categoryId);

        return salonProductMapper.toDtoList(
            salonProductRepository.findByProductCategory_Id(categoryId)
        );
    }

    /** Restituisce la Entity SalonProduct tramite ID. */
    @Transactional(readOnly = true)
    public SalonProduct getSalonProductById(Integer id)
            throws ServiceException {

        return salonProductRepository.findById(id)
            .orElseThrow(
                () -> new ServiceException(
                    "Servizio non trovato con id: " + id,
                    HttpStatus.NOT_FOUND
                )
            );
    }

    /** Recupera la categoria necessaria alla relazione ManyToOne. */
    @Transactional(readOnly = true)
    public ProductCategory getCategory(Integer categoryId)
            throws ServiceException {

        return productCategoryRepository.findById(categoryId)
            .orElseThrow(
                () -> new ServiceException(
                    "Categoria non trovata con id: " + categoryId,
                    HttpStatus.NOT_FOUND
                )
            );
    }

    /**
     * Un servizio attivo non può appartenere a una categoria disattivata.
     */
    private void validateCategoryForState(
            ProductCategory category,
            boolean productActive)
            throws ServiceException {

        if (productActive && !category.isActive()) {
            throw new ServiceException(
                "Non è possibile attivare un servizio appartenente a una categoria disattivata",
                HttpStatus.CONFLICT
            );
        }
    }

    /** Controlla durata e prezzo prima del salvataggio. */
    private void validateCommercialValues(SalonProductDto dto)
            throws ServiceException {

        if (dto.getDuration() <= 0) {
            throw new ServiceException(
                "La durata del servizio deve essere maggiore di zero",
                HttpStatus.BAD_REQUEST
            );
        }

        if (dto.getBasePrice() < 0) {
            throw new ServiceException(
                "Il prezzo base non può essere negativo",
                HttpStatus.BAD_REQUEST
            );
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
