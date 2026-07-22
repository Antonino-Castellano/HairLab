package com.generation.hairlab.service;

import java.util.List;
import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.hairlab.dto.HairDyeDto;
import com.generation.hairlab.mapper.HairDyeMapper;
import com.generation.hairlab.model.HairDye;
import com.generation.hairlab.repository.HairDyeRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato alle tinte e ai prodotti tecnici colore.
 *
 * I prodotti non vengono cancellati fisicamente quando sono
 * già utilizzati nello storico: vengono disattivati tramite active=false.
 */
@Service
@RequiredArgsConstructor
public class HairDyeService {

    /** Repository delle tinte/prodotti tecnici. */
    private final HairDyeRepository hairDyeRepository;

    /** Mapper HairDye <-> HairDyeDto. */
    private final HairDyeMapper hairDyeMapper;

    /** Restituisce tutti i prodotti tecnici. */
    @Transactional(readOnly = true)
    public List<HairDyeDto> findAll() {
        return hairDyeMapper.toDtoList(
            hairDyeRepository.findAll()
        );
    }

    /** Restituisce solamente i prodotti attivi. */
    @Transactional(readOnly = true)
    public List<HairDyeDto> findActive() {
        return hairDyeMapper.toDtoList(
            hairDyeRepository.findByActiveTrue()
        );
    }

    /** Cerca un prodotto tramite ID. */
    @Transactional(readOnly = true)
    public HairDyeDto findById(Integer id)
            throws ServiceException {
        return hairDyeMapper.toDto(
            getHairDyeById(id)
        );
    }

    /** Inserisce un nuovo prodotto verificando l'unicità del codice. */
    @Transactional
    public HairDyeDto insert(HairDyeDto dto)
            throws ServiceException {

        String normalizedCode = normalizeCode(dto.getCode());

        if (hairDyeRepository.existsByCode(normalizedCode)) {
            throw new ServiceException(
                "Esiste già un prodotto con questo codice",
                HttpStatus.CONFLICT
            );
        }

        HairDye hairDye = hairDyeMapper.toEntity(dto);

        hairDye.setBrand(normalizeText(dto.getBrand()));
        hairDye.setName(normalizeText(dto.getName()));
        hairDye.setCode(normalizedCode);

        return hairDyeMapper.toDto(
            hairDyeRepository.save(hairDye)
        );
    }

    /** Aggiorna un prodotto tecnico esistente. */
    @Transactional
    public HairDyeDto update(
            Integer id,
            HairDyeDto dto)
            throws ServiceException {

        HairDye hairDye = getHairDyeById(id);

        String normalizedCode = normalizeCode(dto.getCode());

        HairDye sameCode = hairDyeRepository
            .findByCode(normalizedCode)
            .orElse(null);

        if (sameCode != null && !sameCode.getId().equals(id)) {
            throw new ServiceException(
                "Esiste già un altro prodotto con questo codice",
                HttpStatus.CONFLICT
            );
        }

        hairDye.setPrimaryReflection(dto.getPrimaryReflection());
        hairDye.setSecondaryReflection(dto.getSecondaryReflection());
        hairDye.setBrand(normalizeText(dto.getBrand()));
        hairDye.setName(normalizeText(dto.getName()));
        hairDye.setCode(normalizedCode);
        hairDye.setProductType(dto.getProductType());
        hairDye.setToneLevel(dto.getToneLevel());
        hairDye.setActive(dto.isActive());

        return hairDyeMapper.toDto(
            hairDyeRepository.save(hairDye)
        );
    }

    /**
     * Disattiva logicamente un prodotto tecnico.
     *
     * In questo modo le formule colore storiche possono continuare
     * a riferirsi allo stesso HairDye.
     */
    @Transactional
    public void delete(Integer id)
            throws ServiceException {

        HairDye hairDye = getHairDyeById(id);

        if (!hairDye.isActive()) {
            return;
        }

        hairDye.setActive(false);
        hairDyeRepository.save(hairDye);
    }

    /** Riattiva un prodotto tecnico disattivato. */
    @Transactional
    public HairDyeDto activate(Integer id)
            throws ServiceException {

        HairDye hairDye = getHairDyeById(id);
        hairDye.setActive(true);

        return hairDyeMapper.toDto(
            hairDyeRepository.save(hairDye)
        );
    }

    /** Restituisce la Entity HairDye tramite ID. */
    @Transactional(readOnly = true)
    public HairDye getHairDyeById(Integer id)
            throws ServiceException {

        return hairDyeRepository.findById(id)
            .orElseThrow(
                () -> new ServiceException(
                    "Prodotto HairDye non trovato con id: " + id,
                    HttpStatus.NOT_FOUND
                )
            );
    }

    /** Uniforma il codice tecnico per evitare duplicati dovuti al case. */
    private String normalizeCode(String value) {
        return value == null
            ? null
            : value.trim().toUpperCase(Locale.ROOT);
    }

    /** Elimina gli spazi esterni dai campi testuali. */
    private String normalizeText(String value) {
        return value == null
            ? null
            : value.trim();
    }
}
