package com.generation.hairlab.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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
 * Service dei singoli ingredienti delle formule.
 *
 * Un ColorFormulaItem usa UN SOLO HairDye.
 */
@Service
@RequiredArgsConstructor
public class ColorFormulaItemService {

    private final ColorFormulaItemRepository
        colorFormulaItemRepository;

    private final ColorFormulaRepository
        colorFormulaRepository;

    private final HairDyeRepository
        hairDyeRepository;

    private final ColorFormulaItemMapper
        colorFormulaItemMapper;

    @Transactional(readOnly = true)
    public List<ColorFormulaItemDto> findAll() {

        return colorFormulaItemMapper.toDtoList(
            colorFormulaItemRepository.findAll()
        );
    }

    @Transactional(readOnly = true)
    public ColorFormulaItemDto findById(
            Integer id)
            throws ServiceException {

        return colorFormulaItemMapper.toDto(
            getColorFormulaItemById(
                id
            )
        );
    }

    @Transactional(readOnly = true)
    public List<ColorFormulaItemDto> findByFormula(
            Integer colorFormulaId) {

        return colorFormulaItemMapper.toDtoList(
            colorFormulaItemRepository
                .findByColorFormula_Id(
                    colorFormulaId
                )
        );
    }

    @Transactional
    public ColorFormulaItemDto insert(
            ColorFormulaItemDto dto)
            throws ServiceException {

        validateQuantity(
            dto.getQuantity()
        );

        ColorFormula formula =
            getColorFormula(
                dto.getColorFormulaId()
            );

        HairDye hairDye =
            getActiveHairDye(
                dto.getHairDyeId()
            );

        ColorFormulaItem item =
            colorFormulaItemMapper.toEntity(
                dto
            );

        item.setColorFormula(
            formula
        );

        item.setHairDye(
            hairDye
        );

        item.setNotes(
            normalizeNullable(
                dto.getNotes()
            )
        );

        return colorFormulaItemMapper.toDto(
            colorFormulaItemRepository.save(
                item
            )
        );
    }

    @Transactional
    public ColorFormulaItemDto update(
            Integer id,
            ColorFormulaItemDto dto)
            throws ServiceException {

        ColorFormulaItem item =
            getColorFormulaItemById(
                id
            );

        validateQuantity(
            dto.getQuantity()
        );

        item.setColorFormula(
            getColorFormula(
                dto.getColorFormulaId()
            )
        );

        item.setHairDye(
            getActiveHairDye(
                dto.getHairDyeId()
            )
        );

        item.setQuantity(
            dto.getQuantity()
        );

        item.setUnit(
            dto.getUnit()
        );

        item.setNotes(
            normalizeNullable(
                dto.getNotes()
            )
        );

        return colorFormulaItemMapper.toDto(
            colorFormulaItemRepository.save(
                item
            )
        );
    }

    @Transactional
    public void delete(
            Integer id)
            throws ServiceException {

        colorFormulaItemRepository.delete(
            getColorFormulaItemById(
                id
            )
        );
    }

    @Transactional(readOnly = true)
    public ColorFormulaItem getColorFormulaItemById(
            Integer id)
            throws ServiceException {

        return colorFormulaItemRepository
            .findById(
                id
            )
            .orElseThrow(
                () ->
                    new ServiceException(
                        "ColorFormulaItem non trovato con id: "
                            + id,
                        HttpStatus.NOT_FOUND
                    )
            );
    }

    private ColorFormula getColorFormula(
            Integer id)
            throws ServiceException {

        return colorFormulaRepository
            .findById(
                id
            )
            .orElseThrow(
                () ->
                    new ServiceException(
                        "Formula colore non trovata con id: "
                            + id,
                        HttpStatus.NOT_FOUND
                    )
            );
    }

    private HairDye getActiveHairDye(
            Integer id)
            throws ServiceException {

        HairDye hairDye =
            hairDyeRepository
                .findById(
                    id
                )
                .orElseThrow(
                    () ->
                        new ServiceException(
                            "Prodotto HairDye non trovato con id: "
                                + id,
                            HttpStatus.NOT_FOUND
                        )
                );

        if (
            !hairDye.isActive()
        ) {

            throw new ServiceException(
                "Il prodotto tecnico selezionato non è attivo",
                HttpStatus.CONFLICT
            );
        }

        return hairDye;
    }

    private void validateQuantity(
            BigDecimal quantity)
            throws ServiceException {

        if (
            quantity == null
            ||
            quantity.compareTo(
                BigDecimal.ZERO
            ) <= 0
        ) {

            throw new ServiceException(
                "La quantità deve essere maggiore di zero",
                HttpStatus.BAD_REQUEST
            );
        }
    }

    private String normalizeNullable(
            String value) {

        if (
            value == null
        ) {

            return null;
        }

        String normalized =
            value.trim();

        return normalized.isEmpty()
            ? null
            : normalized;
    }
}
