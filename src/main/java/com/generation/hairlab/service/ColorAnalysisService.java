package com.generation.hairlab.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import com.generation.hairlab.dto.ColorAnalysisDto;
import com.generation.hairlab.enums.ColorAnalysisEnums.ColorSeason;
import com.generation.hairlab.enums.ColorAnalysisEnums.ColorSubSeason;
import com.generation.hairlab.mapper.ColorAnalysisMapper;
import com.generation.hairlab.model.ColorAnalysis;
import com.generation.hairlab.repository.ColorAnalysisRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato alla gestione
 * dell'analisi cromatica.
 */
@Service
@RequiredArgsConstructor
public class ColorAnalysisService {

    private final ColorAnalysisRepository
        colorAnalysisRepository;

    private final ColorAnalysisMapper
        colorAnalysisMapper;

    private final CustomerService
        customerService;

    /**
     * Recupera tutte le analisi.
     */
    @Transactional(readOnly = true)
    public List<ColorAnalysisDto> findAll() {

        return colorAnalysisMapper.toDtoList(
            colorAnalysisRepository.findAll()
        );
    }

    /**
     * Recupera tramite ID.
     */
    @Transactional(readOnly = true)
    public ColorAnalysisDto findById(
            Integer id)
            throws ServiceException {

        return colorAnalysisMapper.toDto(
            getColorAnalysisById(id)
        );
    }

    /**
     * Recupera l'analisi di una cliente.
     */
    @Transactional(readOnly = true)
    public ColorAnalysisDto findByCustomerId(
            Integer customerId)
            throws ServiceException {

        ColorAnalysis analysis =
            colorAnalysisRepository
                .findByCustomerId(
                    customerId
                )
                .orElseThrow(
                    () ->
                        new ServiceException(
                            "Color analysis not found",
                            HttpStatus.NOT_FOUND
                        )
                );

        return colorAnalysisMapper.toDto(
            analysis
        );
    }

    /**
     * Inserisce una nuova analisi.
     */
    @Transactional
    public ColorAnalysisDto insert(
            ColorAnalysisDto dto)
            throws ServiceException {

        if (
            dto.getCustomerId() == null
        ) {

            throw new ServiceException(
                "Customer id is required"
            );
        }

        if (
            colorAnalysisRepository
                .existsByCustomerId(
                    dto.getCustomerId()
                )
        ) {

            throw new ServiceException(
                "Customer already has a color analysis",
                HttpStatus.CONFLICT
            );
        }

        validateAnalysis(
            dto
        );

        ColorAnalysis analysis =
            colorAnalysisMapper.toEntity(
                dto
            );

        var customer =
            customerService
                .getCustomerById(
                    dto.getCustomerId()
                );

        if (!customer.isActive()) {

            throw new ServiceException(
                "Customer is not active",
                HttpStatus.CONFLICT
            );
        }

        analysis.setCustomer(
            customer
        );

        analysis.setSkinReferenceColor(
            normalizeHex(
                dto.getSkinReferenceColor()
            )
        );

        analysis.setBestColors(
            copyColors(
                dto.getBestColors()
            )
        );

        analysis.setAvoidColors(
            copyColors(
                dto.getAvoidColors()
            )
        );

        analysis.setBestMetals(
            dto.getBestMetals() == null
                ? new HashSet<>()
                : new HashSet<>(
                    dto.getBestMetals()
                )
        );

        LocalDateTime now =
            LocalDateTime.now();

        analysis.setCreatedAt(
            now
        );

        analysis.setUpdatedAt(
            now
        );

        return colorAnalysisMapper.toDto(
            colorAnalysisRepository.save(
                analysis
            )
        );
    }

    /**
     * Aggiorna un'analisi.
     */
    @Transactional
    public ColorAnalysisDto update(
            Integer id,
            ColorAnalysisDto dto)
            throws ServiceException {

        ColorAnalysis analysis =
            getColorAnalysisById(
                id
            );

        validateAnalysis(
            dto
        );

        /*
         * PELLE.
         */
        analysis.setSkinTone(
            dto.getSkinTone()
        );

        analysis.setSkinReferenceColor(
            normalizeHex(
                dto.getSkinReferenceColor()
            )
        );

        analysis.setUndertone(
            dto.getUndertone()
        );

        /*
         * STAGIONE.
         */
        analysis.setSeason(
            dto.getSeason()
        );

        analysis.setSubSeason(
            dto.getSubSeason()
        );

        /*
         * PARAMETRI.
         */
        analysis.setColorValue(
            dto.getColorValue()
        );

        analysis.setContrastLevel(
            dto.getContrastLevel()
        );

        analysis.setChroma(
            dto.getChroma()
        );

        /*
         * PALETTE.
         */
        analysis.setBestColors(
            copyColors(
                dto.getBestColors()
            )
        );

        analysis.setAvoidColors(
            copyColors(
                dto.getAvoidColors()
            )
        );

        analysis.setBestMetals(
            dto.getBestMetals() == null
                ? new HashSet<>()
                : new HashSet<>(
                    dto.getBestMetals()
                )
        );

        /*
         * NOTE.
         */
        analysis.setNotes(
            dto.getNotes()
        );

        analysis.setUpdatedAt(
            LocalDateTime.now()
        );

        return colorAnalysisMapper.toDto(
            colorAnalysisRepository.save(
                analysis
            )
        );
    }

    /**
     * Elimina un'analisi.
     */
    @Transactional
    public void delete(
            Integer id)
            throws ServiceException {

        colorAnalysisRepository.delete(
            getColorAnalysisById(
                id
            )
        );
    }

    /**
     * Recupera internamente l'Entity.
     */
    @Transactional(readOnly = true)
    public ColorAnalysis getColorAnalysisById(
            Integer id)
            throws ServiceException {

        return colorAnalysisRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ServiceException(
                        "Color analysis not found",
                        HttpStatus.NOT_FOUND
                    )
            );
    }

    /**
     * Esegue tutte le validazioni.
     */
    private void validateAnalysis(
            ColorAnalysisDto dto)
            throws ServiceException {

        validateSeason(
            dto
        );

        validateReferenceColor(
            dto.getSkinReferenceColor(),
            "skin reference color"
        );

        validateColors(
            dto.getBestColors(),
            "best colors"
        );

        validateColors(
            dto.getAvoidColors(),
            "avoid colors"
        );
    }

    /**
     * Controlla che la sottostagione
     * appartenga alla macro stagione selezionata.
     */
    private void validateSeason(
            ColorAnalysisDto dto)
            throws ServiceException {

        ColorSubSeason subSeason =
            dto.getSubSeason();

        if (subSeason == null) {

            return;
        }

        ColorSeason season =
            dto.getSeason();

        if (season == null) {

            throw new ServiceException(
                "Season is required when sub season is specified"
            );
        }

        boolean valid =
            switch (season) {

                case SPRING ->
                    subSeason ==
                        ColorSubSeason.LIGHT_SPRING ||
                    subSeason ==
                        ColorSubSeason.WARM_SPRING ||
                    subSeason ==
                        ColorSubSeason.BRIGHT_SPRING;

                case SUMMER ->
                    subSeason ==
                        ColorSubSeason.LIGHT_SUMMER ||
                    subSeason ==
                        ColorSubSeason.COOL_SUMMER ||
                    subSeason ==
                        ColorSubSeason.SOFT_SUMMER;

                case AUTUMN ->
                    subSeason ==
                        ColorSubSeason.SOFT_AUTUMN ||
                    subSeason ==
                        ColorSubSeason.WARM_AUTUMN ||
                    subSeason ==
                        ColorSubSeason.DEEP_AUTUMN;

                case WINTER ->
                    subSeason ==
                        ColorSubSeason.BRIGHT_WINTER ||
                    subSeason ==
                        ColorSubSeason.COOL_WINTER ||
                    subSeason ==
                        ColorSubSeason.DEEP_WINTER;
            };

        if (!valid) {

            throw new ServiceException(
                "Sub season is not compatible with selected season"
            );
        }
    }

    /**
     * Controlla una palette nome -> HEX.
     */
    private void validateColors(
            Map<String, String> colors,
            String fieldName)
            throws ServiceException {

        if (colors == null) {

            return;
        }

        for (
            Map.Entry<String, String>
                entry :
                colors.entrySet()
        ) {

            if (
                entry.getKey() == null ||
                entry.getKey().isBlank()
            ) {

                throw new ServiceException(
                    fieldName +
                    " contains a color without name"
                );
            }

            validateReferenceColor(
                entry.getValue(),
                fieldName +
                " / " +
                entry.getKey()
            );
        }
    }

    /**
     * Valida formato #RRGGBB.
     */
    private void validateReferenceColor(
            String value,
            String fieldName)
            throws ServiceException {

        if (
            value == null ||
            value.isBlank()
        ) {

            return;
        }

        if (
            !value.matches(
                "^#[0-9A-Fa-f]{6}$"
            )
        ) {

            throw new ServiceException(
                fieldName +
                " must use #RRGGBB format"
            );
        }
    }

    /**
     * Copia e normalizza una palette.
     */
    private Map<String, String> copyColors(
            Map<String, String> source) {

        Map<String, String> result =
            new HashMap<>();

        if (source == null) {

            return result;
        }

        source.forEach(
            (name, hex) ->
                result.put(
                    name,
                    normalizeHex(hex)
                )
        );

        return result;
    }

    /**
     * Uniforma HEX in maiuscolo.
     */
    private String normalizeHex(
            String value) {

        if (
            value == null ||
            value.isBlank()
        ) {

            return null;
        }

        return value.toUpperCase();
    }
}
