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
 * Service dedicato alla gestione
 * dei profili morfologici del viso.
 */
@Service
@RequiredArgsConstructor
public class FaceProfileService {

    private final FaceProfileRepository
        faceProfileRepository;

    private final FaceProfileMapper
        faceProfileMapper;

    private final CustomerService
        customerService;

    /**
     * Recupera tutti i profili.
     */
    public List<FaceProfileDto> findAll() {

        return faceProfileMapper.toDtoList(
            faceProfileRepository.findAll()
        );
    }

    /**
     * Recupera un profilo tramite ID.
     */
    public FaceProfileDto findById(
            Integer id)
            throws ServiceException {

        return faceProfileMapper.toDto(
            getFaceProfileById(id)
        );
    }

    /**
     * Recupera il profilo associato
     * a una specifica cliente.
     */
    public FaceProfileDto findByCustomerId(
            Integer customerId)
            throws ServiceException {

        FaceProfile profile =
            faceProfileRepository
                .findByCustomerId(
                    customerId
                )
                .orElseThrow(
                    () ->
                        new ServiceException(
                            "Face profile not found"
                        )
                );

        return faceProfileMapper.toDto(
            profile
        );
    }

    /**
     * Crea un nuovo profilo.
     */
    public FaceProfileDto insert(
            FaceProfileDto dto)
            throws ServiceException {

        if (
            dto.getCustomerId() == null
        ) {

            throw new ServiceException(
                "Customer id is required"
            );
        }

        if (
            faceProfileRepository
                .existsByCustomerId(
                    dto.getCustomerId()
                )
        ) {

            throw new ServiceException(
                "Customer already has a face profile"
            );
        }

        validateReferenceColor(
            dto.getEyeReferenceColor(),
            "eye reference color"
        );

        FaceProfile profile =
            faceProfileMapper.toEntity(
                dto
            );

        profile.setCustomer(
            customerService
                .getCustomerById(
                    dto.getCustomerId()
                )
        );

        LocalDateTime now =
            LocalDateTime.now();

        profile.setCreatedAt(
            now
        );

        profile.setUpdatedAt(
            now
        );

        return faceProfileMapper.toDto(
            faceProfileRepository.save(
                profile
            )
        );
    }

    /**
     * Aggiorna un profilo esistente.
     */
    public FaceProfileDto update(
            Integer id,
            FaceProfileDto dto)
            throws ServiceException {

        FaceProfile profile =
            getFaceProfileById(id);

        validateReferenceColor(
            dto.getEyeReferenceColor(),
            "eye reference color"
        );

        /*
         * FORMA GENERALE.
         */
        profile.setFaceShape(
            dto.getFaceShape()
        );

        /*
         * FRONTE.
         */
        profile.setForeheadHeight(
            dto.getForeheadHeight()
        );

        profile.setForeheadWidth(
            dto.getForeheadWidth()
        );

        profile.setHairlineShape(
            dto.getHairlineShape()
        );

        /*
         * OCCHI.
         */
        profile.setEyeShape(
            dto.getEyeShape()
        );

        profile.setEyeOrientation(
            dto.getEyeOrientation()
        );

        profile.setEyeSpacing(
            dto.getEyeSpacing()
        );

        profile.setEyeSize(
            dto.getEyeSize()
        );

        profile.setEyeColor(
            dto.getEyeColor()
        );

        profile.setEyeReferenceColor(
            normalizeHex(
                dto.getEyeReferenceColor()
            )
        );

        profile.setEyeColorNotes(
            dto.getEyeColorNotes()
        );

        /*
         * SOPRACCIGLIA.
         */
        profile.setEyebrowShape(
            dto.getEyebrowShape()
        );

        profile.setEyebrowThickness(
            dto.getEyebrowThickness()
        );

        /*
         * NASO.
         */
        profile.setNoseLength(
            dto.getNoseLength()
        );

        profile.setNoseWidth(
            dto.getNoseWidth()
        );

        profile.setNoseProfile(
            dto.getNoseProfile()
        );

        profile.setNoseTip(
            dto.getNoseTip()
        );

        /*
         * ZIGOMI.
         */
        profile.setCheekboneWidth(
            dto.getCheekboneWidth()
        );

        profile.setCheekboneProminence(
            dto.getCheekboneProminence()
        );

        /*
         * MASCELLA.
         */
        profile.setJawWidth(
            dto.getJawWidth()
        );

        profile.setJawDefinition(
            dto.getJawDefinition()
        );

        profile.setJawShape(
            dto.getJawShape()
        );

        /*
         * MENTO.
         */
        profile.setChinShape(
            dto.getChinShape()
        );

        profile.setChinProjection(
            dto.getChinProjection()
        );

        /*
         * BOCCA E LABBRA.
         */
        profile.setMouthWidth(
            dto.getMouthWidth()
        );

        profile.setLipFullness(
            dto.getLipFullness()
        );

        profile.setLipBalance(
            dto.getLipBalance()
        );

        profile.setLipShape(
            dto.getLipShape()
        );

        /*
         * NOTE.
         */
        profile.setNotes(
            dto.getNotes()
        );

        profile.setStylingGoals(
            dto.getStylingGoals()
        );

        profile.setUpdatedAt(
            LocalDateTime.now()
        );

        return faceProfileMapper.toDto(
            faceProfileRepository.save(
                profile
            )
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
     * Recupera internamente l'Entity.
     */
    public FaceProfile getFaceProfileById(
            Integer id)
            throws ServiceException {

        return faceProfileRepository
            .findById(id)
            .orElseThrow(
                () ->
                    new ServiceException(
                        "Face profile not found"
                    )
            );
    }

    /**
     * Valida un colore HEX.
     *
     * null e stringa vuota sono consentiti
     * perché il riferimento è opzionale.
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
     * Uniforma i codici HEX.
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