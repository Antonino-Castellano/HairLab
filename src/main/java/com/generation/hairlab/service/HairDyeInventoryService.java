package com.generation.hairlab.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.hairlab.dto.HairDyeInventoryDto;
import com.generation.hairlab.mapper.HairDyeInventoryMapper;
import com.generation.hairlab.model.HairDye;
import com.generation.hairlab.model.HairDyeInventory;
import com.generation.hairlab.repository.HairDyeInventoryRepository;
import com.generation.hairlab.repository.HairDyeRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service del magazzino Color Lab.
 *
 * In questo blocco gestiamo la giacenza corrente.
 *
 * I movimenti di carico/scarico automatici
 * verranno introdotti nel blocco dedicato
 * all'utilizzo reale delle formule.
 */
@Service
@RequiredArgsConstructor
public class HairDyeInventoryService {

    private final HairDyeInventoryRepository
        inventoryRepository;

    private final HairDyeRepository
        hairDyeRepository;

    private final HairDyeInventoryMapper
        inventoryMapper;

    @Transactional(readOnly = true)
    public List<HairDyeInventoryDto> findAll() {

        return inventoryMapper.toDtoList(
            inventoryRepository.findAll()
        );
    }

    @Transactional(readOnly = true)
    public HairDyeInventoryDto findById(
            Integer id)
            throws ServiceException {

        return inventoryMapper.toDto(
            getInventoryById(
                id
            )
        );
    }

    @Transactional(readOnly = true)
    public HairDyeInventoryDto findByHairDyeId(
            Integer hairDyeId)
            throws ServiceException {

        return inventoryMapper.toDto(
            inventoryRepository
                .findByHairDye_Id(
                    hairDyeId
                )
                .orElseThrow(
                    () ->
                        new ServiceException(
                            "Nessuna giacenza trovata per HairDye id: "
                                + hairDyeId,
                            HttpStatus.NOT_FOUND
                        )
                )
        );
    }

    @Transactional
    public HairDyeInventoryDto insert(
            HairDyeInventoryDto dto)
            throws ServiceException {

        validateValues(
            dto
        );

        if (
            inventoryRepository.existsByHairDye_Id(
                dto.getHairDyeId()
            )
        ) {

            throw new ServiceException(
                "Il prodotto possiede già una posizione di magazzino",
                HttpStatus.CONFLICT
            );
        }

        HairDyeInventory inventory =
            inventoryMapper.toEntity(
                dto
            );

        inventory.setHairDye(
            getHairDye(
                dto.getHairDyeId()
            )
        );

        inventory.setUpdatedAt(
            LocalDateTime.now()
        );

        return inventoryMapper.toDto(
            inventoryRepository.save(
                inventory
            )
        );
    }

    @Transactional
    public HairDyeInventoryDto update(
            Integer id,
            HairDyeInventoryDto dto)
            throws ServiceException {

        validateValues(
            dto
        );

        HairDyeInventory inventory =
            getInventoryById(
                id
            );

        HairDye targetHairDye =
            getHairDye(
                dto.getHairDyeId()
            );

        HairDyeInventory sameProduct =
            inventoryRepository
                .findByHairDye_Id(
                    dto.getHairDyeId()
                )
                .orElse(
                    null
                );

        if (
            sameProduct != null
            &&
            !sameProduct.getId()
                .equals(
                    id
                )
        ) {

            throw new ServiceException(
                "Il prodotto possiede già un'altra posizione di magazzino",
                HttpStatus.CONFLICT
            );
        }

        inventory.setHairDye(
            targetHairDye
        );

        inventory.setQuantityAvailable(
            dto.getQuantityAvailable()
        );

        inventory.setUnit(
            dto.getUnit()
        );

        inventory.setLowStockThreshold(
            dto.getLowStockThreshold()
        );

        inventory.setUpdatedAt(
            LocalDateTime.now()
        );

        return inventoryMapper.toDto(
            inventoryRepository.save(
                inventory
            )
        );
    }

    private HairDyeInventory getInventoryById(
            Integer id)
            throws ServiceException {

        return inventoryRepository
            .findById(
                id
            )
            .orElseThrow(
                () ->
                    new ServiceException(
                        "Giacenza Color Lab non trovata con id: "
                            + id,
                        HttpStatus.NOT_FOUND
                    )
            );
    }

    private HairDye getHairDye(
            Integer id)
            throws ServiceException {

        return hairDyeRepository
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
    }

    private void validateValues(
            HairDyeInventoryDto dto)
            throws ServiceException {

        if (
            dto.getQuantityAvailable() == null
            ||
            dto.getQuantityAvailable()
                .compareTo(
                    BigDecimal.ZERO
                ) < 0
        ) {

            throw new ServiceException(
                "La quantità disponibile non può essere negativa",
                HttpStatus.BAD_REQUEST
            );
        }

        if (
            dto.getLowStockThreshold() == null
            ||
            dto.getLowStockThreshold()
                .compareTo(
                    BigDecimal.ZERO
                ) < 0
        ) {

            throw new ServiceException(
                "La soglia minima non può essere negativa",
                HttpStatus.BAD_REQUEST
            );
        }
    }
}
