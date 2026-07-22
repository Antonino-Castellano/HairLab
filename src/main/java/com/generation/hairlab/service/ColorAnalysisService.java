package com.generation.hairlab.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.generation.hairlab.dto.ColorAnalysisDto;
import com.generation.hairlab.enums.ColorAnalysisEnums.ColorSeason;
import com.generation.hairlab.enums.ColorAnalysisEnums.ColorSubSeason;
import com.generation.hairlab.mapper.ColorAnalysisMapper;
import com.generation.hairlab.model.ColorAnalysis;
import com.generation.hairlab.repository.ColorAnalysisRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato all'analisi cromatica.
 */
@Service
@RequiredArgsConstructor
public class ColorAnalysisService {

    private final ColorAnalysisRepository colorAnalysisRepository;

    private final ColorAnalysisMapper colorAnalysisMapper;

    private final CustomerService customerService;

    public List<ColorAnalysisDto> findAll() {

        return colorAnalysisMapper.toDtoList(
            colorAnalysisRepository.findAll()
        );
    }

    public ColorAnalysisDto findById(
            Integer id)
            throws ServiceException {

        return colorAnalysisMapper.toDto(
            getColorAnalysisById(id)
        );
    }

    public ColorAnalysisDto findByCustomerId(
            Integer customerId)
            throws ServiceException {

        return colorAnalysisMapper.toDto(
            colorAnalysisRepository
                .findByCustomerId(customerId)
                .orElseThrow(
                    () -> new ServiceException(
                        "Analisi cromatica non trovata per il cliente: "
                        + customerId
                    )
                )
        );
    }

    public ColorAnalysisDto insert(
            ColorAnalysisDto dto)
            throws ServiceException {

        if (dto.getCustomerId() == null) {
            throw new ServiceException(
                "CustomerId obbligatorio"
            );
        }

        if (
            colorAnalysisRepository
                .existsByCustomerId(
                    dto.getCustomerId()
                )
        ) {
            throw new ServiceException(
                "Il cliente possiede già un'analisi cromatica"
            );
        }

        validateSeason(
            dto.getSeason(),
            dto.getSubSeason()
        );

        validateColors(
            dto.getBestColors()
        );

        validateColors(
            dto.getAvoidColors()
        );

        ColorAnalysis entity =
            colorAnalysisMapper.toEntity(dto);

        entity.setCustomer(
            customerService.getCustomerById(
                dto.getCustomerId()
            )
        );

        entity.setBestColors(
            dto.getBestColors() == null
                ? new HashMap<>()
                : new HashMap<>(
                    dto.getBestColors()
                )
        );

        entity.setAvoidColors(
            dto.getAvoidColors() == null
                ? new HashMap<>()
                : new HashMap<>(
                    dto.getAvoidColors()
                )
        );

        entity.setBestMetals(
            dto.getBestMetals() == null
                ? new HashSet<>()
                : new HashSet<>(
                    dto.getBestMetals()
                )
        );

        entity.setCreatedAt(
            LocalDateTime.now()
        );

        entity.setUpdatedAt(
            LocalDateTime.now()
        );

        return colorAnalysisMapper.toDto(
            colorAnalysisRepository.save(entity)
        );
    }

    public ColorAnalysisDto update(
            Integer id,
            ColorAnalysisDto dto)
            throws ServiceException {

        ColorAnalysis entity =
            getColorAnalysisById(id);

        validateSeason(
            dto.getSeason(),
            dto.getSubSeason()
        );

        validateColors(
            dto.getBestColors()
        );

        validateColors(
            dto.getAvoidColors()
        );

        entity.setSkinTone(
            dto.getSkinTone()
        );

        entity.setUndertone(
            dto.getUndertone()
        );

        entity.setSeason(
            dto.getSeason()
        );

        entity.setSubSeason(
            dto.getSubSeason()
        );

        entity.setColorValue(
            dto.getColorValue()
        );

        entity.setContrastLevel(
            dto.getContrastLevel()
        );

        entity.setChroma(
            dto.getChroma()
        );

        entity.setBestColors(
            dto.getBestColors() == null
                ? new HashMap<>()
                : new HashMap<>(
                    dto.getBestColors()
                )
        );

        entity.setAvoidColors(
            dto.getAvoidColors() == null
                ? new HashMap<>()
                : new HashMap<>(
                    dto.getAvoidColors()
                )
        );

        entity.setBestMetals(
            dto.getBestMetals() == null
                ? new HashSet<>()
                : new HashSet<>(
                    dto.getBestMetals()
                )
        );

        entity.setNotes(
            dto.getNotes()
        );

        entity.setUpdatedAt(
            LocalDateTime.now()
        );

        return colorAnalysisMapper.toDto(
            colorAnalysisRepository.save(entity)
        );
    }

    public void delete(
            Integer id)
            throws ServiceException {

        colorAnalysisRepository.delete(
            getColorAnalysisById(id)
        );
    }

    public ColorAnalysis getColorAnalysisById(
            Integer id)
            throws ServiceException {

        return colorAnalysisRepository
            .findById(id)
            .orElseThrow(
                () -> new ServiceException(
                    "Analisi cromatica non trovata con id: "
                    + id
                )
            );
    }

    /**
     * Verifica che stagione e sottostagione
     * siano coerenti.
     */
    private void validateSeason(
            ColorSeason season,
            ColorSubSeason subSeason)
            throws ServiceException {

        if (
            season == null ||
            subSeason == null
        ) {
            return;
        }

        boolean valid =
            switch (season) {

                case SPRING ->
                    subSeason == ColorSubSeason.LIGHT_SPRING ||
                    subSeason == ColorSubSeason.WARM_SPRING ||
                    subSeason == ColorSubSeason.BRIGHT_SPRING;

                case SUMMER ->
                    subSeason == ColorSubSeason.LIGHT_SUMMER ||
                    subSeason == ColorSubSeason.COOL_SUMMER ||
                    subSeason == ColorSubSeason.SOFT_SUMMER;

                case AUTUMN ->
                    subSeason == ColorSubSeason.SOFT_AUTUMN ||
                    subSeason == ColorSubSeason.WARM_AUTUMN ||
                    subSeason == ColorSubSeason.DEEP_AUTUMN;

                case WINTER ->
                    subSeason == ColorSubSeason.BRIGHT_WINTER ||
                    subSeason == ColorSubSeason.COOL_WINTER ||
                    subSeason == ColorSubSeason.DEEP_WINTER;
            };

        if (!valid) {
            throw new ServiceException(
                "La sottostagione non è compatibile con la stagione selezionata"
            );
        }
    }

    /**
     * Controlla che i colori abbiano
     * un codice HEX valido.
     */
    private void validateColors(
            Map<String, String> colors)
            throws ServiceException {

        if (colors == null) {
            return;
        }

        for (
            Map.Entry<String, String> entry :
            colors.entrySet()
        ) {

            if (
                entry.getKey() == null ||
                entry.getKey().isBlank()
            ) {
                throw new ServiceException(
                    "Il nome del colore non può essere vuoto"
                );
            }

            String hex =
                entry.getValue();

            if (
                hex == null ||
                !hex.matches(
                    "^#[0-9A-Fa-f]{6}$"
                )
            ) {
                throw new ServiceException(
                    "Codice colore HEX non valido per: "
                    + entry.getKey()
                );
            }
        }
    }
}