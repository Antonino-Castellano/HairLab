package com.generation.hairlab.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.hairlab.dto.ColorFormulaDetailDto;
import com.generation.hairlab.dto.ColorFormulaDto;
import com.generation.hairlab.dto.ColorFormulaIngredientRequestDto;
import com.generation.hairlab.dto.ColorFormulaItemDto;
import com.generation.hairlab.dto.ColorFormulaManagementRequestDto;
import com.generation.hairlab.enums.InventoryUnit;
import com.generation.hairlab.enums.MixingRatio;
import com.generation.hairlab.repository.ColorFormulaItemRepository;

import lombok.RequiredArgsConstructor;

/**
 * Orchestratore transazionale del Formula Builder.
 *
 * Una formula non viene salvata in due fasi indipendenti.
 *
 * Il blocco è atomico:
 *
 * 1. salva/aggiorna ColorFormula;
 * 2. salva tutti gli ingredienti;
 * 3. se qualcosa fallisce, rollback completo.
 */
@Service
@RequiredArgsConstructor
public class ColorFormulaManagementService {

    private final ColorFormulaService
        colorFormulaService;

    private final ColorFormulaItemService
        colorFormulaItemService;

    private final ColorFormulaItemRepository
        colorFormulaItemRepository;

    /**
     * Crea formula + ingredienti.
     */
    @Transactional(
        rollbackFor = Exception.class
    )
    public ColorFormulaDetailDto create(
            ColorFormulaManagementRequestDto request)
            throws ServiceException {

        validateRequest(
            request
        );

        ColorFormulaDto created =
            colorFormulaService.insert(
                toFormulaDto(
                    request
                )
            );

        saveIngredients(
            created.getId(),
            request.getIngredients()
        );

        return findById(
            created.getId()
        );
    }

    /**
     * Aggiorna atomicamente formula e ingredienti.
     *
     * Gli item precedenti vengono sostituiti
     * dalla nuova composizione del Builder.
     */
    @Transactional(
        rollbackFor = Exception.class
    )
    public ColorFormulaDetailDto update(
            Integer id,
            ColorFormulaManagementRequestDto request)
            throws ServiceException {

        validateRequest(
            request
        );

        colorFormulaService.update(
            id,
            toFormulaDto(
                request
            )
        );

        colorFormulaItemRepository.deleteAll(
            colorFormulaItemRepository
                .findByColorFormula_Id(
                    id
                )
        );

        /*
         * Forziamo il DELETE prima degli INSERT
         * così l'operazione resta leggibile anche
         * con eventuali vincoli futuri.
         */
        colorFormulaItemRepository.flush();

        saveIngredients(
            id,
            request.getIngredients()
        );

        return findById(
            id
        );
    }

    /**
     * Dettaglio aggregato.
     */
    @Transactional(readOnly = true)
    public ColorFormulaDetailDto findById(
            Integer id)
            throws ServiceException {

        ColorFormulaDto formula =
            colorFormulaService.findById(
                id
            );

        List<ColorFormulaItemDto> ingredients =
            colorFormulaItemService
                .findByFormula(
                    id
                );

        return buildDetail(
            formula,
            ingredients
        );
    }

    /**
     * Elimina formula e ingredienti.
     *
     * Utilizzare soltanto per formule eliminabili.
     * La politica definitiva di archiviazione
     * verrà raffinata con lo storico formule.
     */
    @Transactional(
        rollbackFor = Exception.class
    )
    public void delete(
            Integer id)
            throws ServiceException {

        colorFormulaService.findById(
            id
        );

        colorFormulaItemRepository.deleteAll(
            colorFormulaItemRepository
                .findByColorFormula_Id(
                    id
                )
        );

        colorFormulaItemRepository.flush();

        colorFormulaService.delete(
            id
        );
    }

    /**
     * Regole del Builder:
     *
     * - almeno un ingrediente;
     * - stesso HairDye non duplicato;
     * - quantità > 0;
     * - per questo blocco la composizione
     *   viene pesata in grammi.
     */
    private void validateRequest(
            ColorFormulaManagementRequestDto request)
            throws ServiceException {

        if (
            request == null
            ||
            request.getIngredients() == null
            ||
            request.getIngredients().isEmpty()
        ) {

            throw new ServiceException(
                "Inserisci almeno un ingrediente",
                HttpStatus.BAD_REQUEST
            );
        }

        Set<Integer> productIds =
            new HashSet<>();

        for (
            ColorFormulaIngredientRequestDto item :
            request.getIngredients()
        ) {

            if (
                item.getHairDyeId() == null
            ) {

                throw new ServiceException(
                    "Ogni ingrediente deve avere un prodotto tecnico",
                    HttpStatus.BAD_REQUEST
                );
            }

            if (
                !productIds.add(
                    item.getHairDyeId()
                )
            ) {

                throw new ServiceException(
                    "Lo stesso prodotto tecnico non può comparire due volte nella formula",
                    HttpStatus.CONFLICT
                );
            }

            if (
                item.getQuantity() == null
                ||
                item.getQuantity()
                    .compareTo(
                        BigDecimal.ZERO
                    ) <= 0
            ) {

                throw new ServiceException(
                    "Ogni ingrediente deve avere una quantità maggiore di zero",
                    HttpStatus.BAD_REQUEST
                );
            }

            if (
                item.getUnit() !=
                InventoryUnit.GRAM
            ) {

                throw new ServiceException(
                    "Nel Formula Builder del Blocco 3 gli ingredienti devono essere pesati in grammi",
                    HttpStatus.BAD_REQUEST
                );
            }
        }
    }

    private ColorFormulaDto toFormulaDto(
            ColorFormulaManagementRequestDto request) {

        ColorFormulaDto dto =
            new ColorFormulaDto();

        dto.setCustomerId(
            request.getCustomerId()
        );

        dto.setConsultationId(
            request.getConsultationId()
        );

        dto.setAppointmentItemId(
            request.getAppointmentItemId()
        );

        dto.setName(
            request.getName()
        );

        dto.setTargetResult(
            request.getTargetResult()
        );

        dto.setTargetToneLevel(
            request.getTargetToneLevel()
        );

        dto.setTargetPrimaryReflection(
            request.getTargetPrimaryReflection()
        );

        dto.setTargetSecondaryReflection(
            request.getTargetSecondaryReflection()
        );

        dto.setApplicationType(
            request.getApplicationType()
        );

        dto.setVolumeDeveloper(
            request.getVolumeDeveloper()
        );

        dto.setMixingRatio(
            request.getMixingRatio()
        );

        dto.setCustomDeveloperRatio(
            request.getCustomDeveloperRatio()
        );

        dto.setStatus(
            request.getStatus()
        );

        dto.setNotes(
            request.getNotes()
        );

        return dto;
    }

    private void saveIngredients(
            Integer formulaId,
            List<ColorFormulaIngredientRequestDto> ingredients)
            throws ServiceException {

        for (
            ColorFormulaIngredientRequestDto requestItem :
            ingredients
        ) {

            ColorFormulaItemDto item =
                new ColorFormulaItemDto();

            item.setColorFormulaId(
                formulaId
            );

            item.setHairDyeId(
                requestItem.getHairDyeId()
            );

            item.setQuantity(
                requestItem.getQuantity()
            );

            item.setUnit(
                requestItem.getUnit()
            );

            item.setNotes(
                requestItem.getNotes()
            );

            colorFormulaItemService.insert(
                item
            );
        }
    }

    private ColorFormulaDetailDto buildDetail(
            ColorFormulaDto formula,
            List<ColorFormulaItemDto> ingredients)
            throws ServiceException {

        BigDecimal totalColor =
            ingredients
                .stream()
                .filter(
                    item ->
                        item.getUnit() ==
                        InventoryUnit.GRAM
                )
                .map(
                    ColorFormulaItemDto::getQuantity
                )
                .reduce(
                    BigDecimal.ZERO,
                    BigDecimal::add
                )
                .setScale(
                    2,
                    RoundingMode.HALF_UP
                );

        BigDecimal ratio =
            resolveDeveloperRatio(
                formula
            );

        BigDecimal developer =
            totalColor
                .multiply(
                    ratio
                )
                .setScale(
                    2,
                    RoundingMode.HALF_UP
                );

        ColorFormulaDetailDto detail =
            new ColorFormulaDetailDto();

        detail.setFormula(
            formula
        );

        detail.setIngredients(
            ingredients
        );

        detail.setTotalColorQuantity(
            totalColor
        );

        detail.setDeveloperQuantity(
            developer
        );

        detail.setTotalMixtureQuantity(
            totalColor
                .add(
                    developer
                )
                .setScale(
                    2,
                    RoundingMode.HALF_UP
                )
        );

        return detail;
    }

    private BigDecimal resolveDeveloperRatio(
            ColorFormulaDto formula)
            throws ServiceException {

        MixingRatio ratio =
            formula.getMixingRatio();

        return switch (
            ratio
        ) {

            case RATIO_1_TO_1 ->
                new BigDecimal(
                    "1.00"
                );

            case RATIO_1_TO_1_5 ->
                new BigDecimal(
                    "1.50"
                );

            case RATIO_1_TO_2 ->
                new BigDecimal(
                    "2.00"
                );

            case RATIO_1_TO_3 ->
                new BigDecimal(
                    "3.00"
                );

            case CUSTOM -> {

                BigDecimal custom =
                    formula.getCustomDeveloperRatio();

                if (
                    custom == null
                    ||
                    custom.compareTo(
                        BigDecimal.ZERO
                    ) <= 0
                ) {

                    throw new ServiceException(
                        "Rapporto developer personalizzato non valido",
                        HttpStatus.BAD_REQUEST
                    );
                }

                yield custom;
            }
        };
    }
}
