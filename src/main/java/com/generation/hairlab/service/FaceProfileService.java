package com.generation.hairlab.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.generation.hairlab.dto.FaceProfileDto;
import com.generation.hairlab.mapper.FaceProfileMapper;
import com.generation.hairlab.model.FaceProfile;
import com.generation.hairlab.repository.FaceProfileRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato al profilo morfologico del viso.
 */
@Service
@RequiredArgsConstructor
public class FaceProfileService {

    private final FaceProfileRepository faceProfileRepository;

    private final FaceProfileMapper faceProfileMapper;

    private final CustomerService customerService;

    /**
     * Restituisce tutti i profili.
     */
    public List<FaceProfileDto> findAll() {

        return faceProfileMapper.toDtoList(
            faceProfileRepository.findAll()
        );
    }

    /**
     * Ricerca per ID del profilo.
     */
    public FaceProfileDto findById(
            Integer id)
            throws ServiceException {

        return faceProfileMapper.toDto(
            getFaceProfileById(id)
        );
    }

    /**
     * Ricerca il profilo appartenente
     * a uno specifico cliente.
     */
    public FaceProfileDto findByCustomerId(
            Integer customerId)
            throws ServiceException {

        return faceProfileMapper.toDto(
            faceProfileRepository
                .findByCustomerId(customerId)
                .orElseThrow(
                    () -> new ServiceException(
                        "Profilo viso non trovato per il cliente: "
                        + customerId
                    )
                )
        );
    }

    /**
     * Inserisce un nuovo FaceProfile.
     */
    public FaceProfileDto insert(
            FaceProfileDto dto)
            throws ServiceException {

        if (dto.getCustomerId() == null) {
            throw new ServiceException(
                "CustomerId obbligatorio"
            );
        }

        /**
         * Un cliente può possedere
         * solamente un FaceProfile.
         */
        if (
            faceProfileRepository
                .existsByCustomerId(
                    dto.getCustomerId()
                )
        ) {
            throw new ServiceException(
                "Il cliente possiede già un profilo del viso"
            );
        }

        FaceProfile entity =
            faceProfileMapper.toEntity(dto);

        entity.setCustomer(
            customerService.getCustomerById(
                dto.getCustomerId()
            )
        );

        entity.setCreatedAt(
            LocalDateTime.now()
        );

        entity.setUpdatedAt(
            LocalDateTime.now()
        );

        return faceProfileMapper.toDto(
            faceProfileRepository.save(entity)
        );
    }

    /**
     * Aggiorna un profilo esistente.
     *
     * Seguiamo lo stesso stile di CustomerService:
     * recuperiamo prima la Entity e aggiorniamo
     * esplicitamente i campi.
     */
    public FaceProfileDto update(
            Integer id,
            FaceProfileDto dto)
            throws ServiceException {

        FaceProfile entity =
            getFaceProfileById(id);

        entity.setFaceShape(
            dto.getFaceShape()
        );

        entity.setForeheadHeight(
            dto.getForeheadHeight()
        );

        entity.setForeheadWidth(
            dto.getForeheadWidth()
        );

        entity.setHairlineShape(
            dto.getHairlineShape()
        );

        entity.setEyeShape(
            dto.getEyeShape()
        );

        entity.setEyeOrientation(
            dto.getEyeOrientation()
        );

        entity.setEyeSpacing(
            dto.getEyeSpacing()
        );

        entity.setEyeSize(
            dto.getEyeSize()
        );

        entity.setEyeColor(
            dto.getEyeColor()
        );

        entity.setEyeColorNotes(
            dto.getEyeColorNotes()
        );

        entity.setEyebrowShape(
            dto.getEyebrowShape()
        );

        entity.setEyebrowThickness(
            dto.getEyebrowThickness()
        );

        entity.setNoseLength(
            dto.getNoseLength()
        );

        entity.setNoseWidth(
            dto.getNoseWidth()
        );

        entity.setNoseProfile(
            dto.getNoseProfile()
        );

        entity.setNoseTip(
            dto.getNoseTip()
        );

        entity.setCheekboneWidth(
            dto.getCheekboneWidth()
        );

        entity.setCheekboneProminence(
            dto.getCheekboneProminence()
        );

        entity.setJawWidth(
            dto.getJawWidth()
        );

        entity.setJawDefinition(
            dto.getJawDefinition()
        );

        entity.setJawShape(
            dto.getJawShape()
        );

        entity.setChinShape(
            dto.getChinShape()
        );

        entity.setChinProjection(
            dto.getChinProjection()
        );

        entity.setMouthWidth(
            dto.getMouthWidth()
        );

        entity.setLipFullness(
            dto.getLipFullness()
        );

        entity.setLipBalance(
            dto.getLipBalance()
        );

        entity.setLipShape(
            dto.getLipShape()
        );

        entity.setNotes(
            dto.getNotes()
        );

        entity.setStylingGoals(
            dto.getStylingGoals()
        );

        entity.setUpdatedAt(
            LocalDateTime.now()
        );

        return faceProfileMapper.toDto(
            faceProfileRepository.save(entity)
        );
    }

    /**
     * Elimina il profilo.
     */
    public void delete(
            Integer id)
            throws ServiceException {

        faceProfileRepository.delete(
            getFaceProfileById(id)
        );
    }

    /**
     * Restituisce la Entity reale.
     */
    public FaceProfile getFaceProfileById(
            Integer id)
            throws ServiceException {

        return faceProfileRepository
            .findById(id)
            .orElseThrow(
                () -> new ServiceException(
                    "Profilo viso non trovato con id: "
                    + id
                )
            );
    }
}