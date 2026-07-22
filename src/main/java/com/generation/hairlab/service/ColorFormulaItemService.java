package com.generation.hairlab.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import com.generation.hairlab.dto.ColorFormulaItemDto;
import com.generation.hairlab.mapper.ColorFormulaItemMapper;
import com.generation.hairlab.model.ColorFormula;
import com.generation.hairlab.model.ColorFormulaItem;
import com.generation.hairlab.model.HairDye;
import com.generation.hairlab.repository.ColorFormulaItemRepository;
import com.generation.hairlab.repository.ColorFormulaRepository;
import com.generation.hairlab.repository.HairDyeRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato agli elementi delle formule colore.
 *
 * Risolve colorFormulaId e hairDyeIds recuperando le Entity reali
 * dai rispettivi Repository.
 */
@Service
@RequiredArgsConstructor
public class ColorFormulaItemService {

    
    private final ColorFormulaItemRepository colorFormulaItemRepository;

    private final ColorFormulaRepository colorFormulaRepository;

    private final HairDyeRepository hairDyeRepository;

    private final ColorFormulaItemMapper colorFormulaItemMapper;

    /** Restituisce tutti gli elementi delle formule. */
    @Transactional(readOnly = true)
    public List<ColorFormulaItemDto> findAll() {
        return colorFormulaItemMapper.toDtoList(colorFormulaItemRepository.findAll());
    }

    /** Cerca un elemento tramite ID. */
    @Transactional(readOnly = true)
    public ColorFormulaItemDto findById(Integer id) throws ServiceException {
        return colorFormulaItemMapper.toDto(getColorFormulaItemById(id));
    }

    /** Restituisce gli item appartenenti a una formula. */
    @Transactional(readOnly = true)
    public List<ColorFormulaItemDto> findByFormula(Integer colorFormulaId) {
        return colorFormulaItemMapper.toDtoList(
                colorFormulaItemRepository.findByColorFormula_Id(colorFormulaId));
    }

    /**
     * Inserisce un nuovo elemento della formula.
     *
     * Verifica quantità e presenza delle tinte indicate nel DTO.
     */
    @Transactional
    public ColorFormulaItemDto insert(ColorFormulaItemDto dto)
            throws ServiceException {

        validateQuantity(dto.getQuantity());

        ColorFormula formula = getColorFormula(dto.getColorFormulaId());
        Set<HairDye> hairDyes = getHairDyes(dto.getHairDyeIds());

        ColorFormulaItem item = colorFormulaItemMapper.toEntity(dto);

        item.setColorFormula(formula);
        item.setHairDyes(hairDyes);

        return colorFormulaItemMapper.toDto(
                colorFormulaItemRepository.save(item));
    }

    /** Aggiorna un elemento della formula. */
    @Transactional
    public ColorFormulaItemDto update(Integer id, ColorFormulaItemDto dto)
            throws ServiceException {

        ColorFormulaItem item = getColorFormulaItemById(id);

        validateQuantity(dto.getQuantity());

        item.setColorFormula(getColorFormula(dto.getColorFormulaId()));
        item.setHairDyes(getHairDyes(dto.getHairDyeIds()));
        item.setQuantity(dto.getQuantity());
        item.setNotes(dto.getNotes());

        return colorFormulaItemMapper.toDto(
                colorFormulaItemRepository.save(item));
    }

    /** Elimina un elemento della formula. */
    @Transactional
    public void delete(Integer id) throws ServiceException {
        colorFormulaItemRepository.delete(getColorFormulaItemById(id));
    }

    /** Restituisce la Entity ColorFormulaItem tramite ID. */
    @Transactional(readOnly = true)
    public ColorFormulaItem getColorFormulaItemById(Integer id)
            throws ServiceException {

        return colorFormulaItemRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        "ColorFormulaItem non trovato con id: " + id,
                        HttpStatus.NOT_FOUND));
    }

    private ColorFormula getColorFormula(Integer id) throws ServiceException {
        return colorFormulaRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        "Formula colore non trovata con id: " + id,
                        HttpStatus.NOT_FOUND));
    }

    /**
     * Recupera tutte le HairDye indicate nel DTO.
     *
     * Controlla che il numero di Entity recuperate coincida con il numero
     * degli ID richiesti, così nessun ID inesistente viene ignorato.
     */
    private Set<HairDye> getHairDyes(Set<Integer> ids) throws ServiceException {

        if (ids == null || ids.isEmpty()) {
            throw new ServiceException(
                    "È necessario indicare almeno una tinta/prodotto");
        }

        List<HairDye> found = hairDyeRepository.findAllById(ids);

        if (found.size() != ids.size()) {
            throw new ServiceException(
                    "Uno o più HairDye indicati non esistono",
                    HttpStatus.NOT_FOUND);
        }

        for (HairDye hairDye : found) {
            if (!hairDye.isActive()) {
                throw new ServiceException(
                        "Uno o più HairDye selezionati non sono attivi",
                        HttpStatus.CONFLICT);
            }
        }

        return new HashSet<>(found);
    }

    /** Verifica che la quantità sia maggiore di zero. */
    private void validateQuantity(double quantity) throws ServiceException {
        if (quantity <= 0) {
            throw new ServiceException(
                    "La quantità deve essere maggiore di zero");
        }
    }
}
