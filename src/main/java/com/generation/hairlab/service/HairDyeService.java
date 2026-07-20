package com.generation.hairlab.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.generation.hairlab.dto.HairDyeDto;
import com.generation.hairlab.mapper.HairDyeMapper;
import com.generation.hairlab.model.HairDye;
import com.generation.hairlab.repository.HairDyeRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato alla gestione delle tinte e dei prodotti tecnici colore.
 */
@Service
@RequiredArgsConstructor
public class HairDyeService {

    /** Repository delle tinte/prodotti tecnici. */
    private final HairDyeRepository hairDyeRepository;

    /** Mapper HairDye <-> HairDyeDto. */
    private final HairDyeMapper hairDyeMapper;

    /** Restituisce tutti i prodotti tecnici. */
    public List<HairDyeDto> findAll() {
        return hairDyeMapper.toDtoList(hairDyeRepository.findAll());
    }

    /** Restituisce solamente i prodotti attivi. */
    public List<HairDyeDto> findActive() {
        return hairDyeMapper.toDtoList(hairDyeRepository.findByActiveTrue());
    }

    /** Cerca un prodotto tramite ID. */
    public HairDyeDto findById(Integer id) throws ServiceException {
        return hairDyeMapper.toDto(getHairDyeById(id));
    }

    /**
     * Inserisce un nuovo prodotto verificando l'unicità del codice.
     */
    public HairDyeDto insert(HairDyeDto dto) throws ServiceException {

        if (hairDyeRepository.existsByCode(dto.getCode())) {
            throw new ServiceException("Esiste già un prodotto con questo codice");
        }

        HairDye hairDye = hairDyeMapper.toEntity(dto);

        return hairDyeMapper.toDto(hairDyeRepository.save(hairDye));
    }

    /** Aggiorna un prodotto esistente. */
    public HairDyeDto update(Integer id, HairDyeDto dto) throws ServiceException {

        HairDye hairDye = getHairDyeById(id);

        HairDye sameCode = hairDyeRepository.findByCode(dto.getCode()).orElse(null);

        if (sameCode != null && !sameCode.getId().equals(id)) {
            throw new ServiceException("Esiste già un altro prodotto con questo codice");
        }

        hairDye.setPrimaryReflection(dto.getPrimaryReflection());
        hairDye.setSecondaryReflection(dto.getSecondaryReflection());
        hairDye.setBrand(dto.getBrand());
        hairDye.setName(dto.getName());
        hairDye.setCode(dto.getCode());
        hairDye.setProductType(dto.getProductType());
        hairDye.setToneLevel(dto.getToneLevel());
        hairDye.setActive(dto.isActive());

        return hairDyeMapper.toDto(hairDyeRepository.save(hairDye));
    }

    /** Elimina un prodotto tecnico esistente. */
    public void delete(Integer id) throws ServiceException {
        hairDyeRepository.delete(getHairDyeById(id));
    }

    /** Restituisce la Entity HairDye tramite ID. */
    public HairDye getHairDyeById(Integer id) throws ServiceException {
        return hairDyeRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        "Prodotto HairDye non trovato con id: " + id));
    }
}
