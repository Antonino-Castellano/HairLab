package com.generation.hairlab.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.hairlab.dto.ColorAnalysisDto;
import com.generation.hairlab.dto.CustomerAnalysisDto;
import com.generation.hairlab.dto.CustomerDto;
import com.generation.hairlab.dto.FaceProfileDto;
import com.generation.hairlab.dto.HairProfileDto;
import com.generation.hairlab.dto.StyleRecommendationDto;
import com.generation.hairlab.mapper.ColorAnalysisMapper;
import com.generation.hairlab.mapper.FaceProfileMapper;
import com.generation.hairlab.mapper.HairProfileMapper;
import com.generation.hairlab.repository.ColorAnalysisRepository;
import com.generation.hairlab.repository.FaceProfileRepository;
import com.generation.hairlab.repository.HairProfileRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service aggregatore della scheda tecnica completa
 * di una cliente.
 *
 * Non salva nuovi dati.
 *
 * Il suo compito è raccogliere informazioni
 * provenienti da più moduli:
 *
 * Customer
 * HairProfile
 * FaceProfile
 * ColorAnalysis
 * StyleRecommendation
 *
 * e restituirle in un unico DTO.
 */
@Service
@RequiredArgsConstructor
public class CustomerAnalysisService {

    private final CustomerService customerService;

    private final HairProfileRepository hairProfileRepository;

    private final FaceProfileRepository faceProfileRepository;

    private final ColorAnalysisRepository colorAnalysisRepository;

    private final HairProfileMapper hairProfileMapper;

    private final FaceProfileMapper faceProfileMapper;

    private final ColorAnalysisMapper colorAnalysisMapper;

    private final StyleRecommendationService
        styleRecommendationService;

    /**
     * Costruisce la scheda completa
     * di una cliente.
     *
     * I profili mancanti vengono restituiti
     * come null.
     *
     * Questo è importante perché l'assenza
     * di un profilo non deve impedire
     * l'apertura della pagina cliente.
     *
     * @param customerId ID della cliente
     * @return analisi aggregata
     * @throws ServiceException se il cliente non esiste
     */
    @Transactional(readOnly = true)
    public CustomerAnalysisDto findByCustomerId(
            Integer customerId)
            throws ServiceException {

        /*
         * ============================================================
         * CUSTOMER
         * ============================================================
         */

        CustomerDto customer =
            customerService.findById(
                customerId
            );

        /*
         * ============================================================
         * HAIR PROFILE
         * ============================================================
         *
         * Recuperiamo direttamente il profilo tramite customerId,
         * evitando di caricare tutte le HairProfile in memoria.
         */

        HairProfileDto hairProfile =
            hairProfileRepository
                .findByCustomerId(
                    customerId
                )
                .map(
                    hairProfileMapper::toDto
                )
                .orElse(null);

        /*
         * ============================================================
         * FACE PROFILE
         * ============================================================
         */

        FaceProfileDto faceProfile =
            faceProfileRepository
                .findByCustomerId(
                    customerId
                )
                .map(
                    faceProfileMapper::toDto
                )
                .orElse(null);

        /*
         * ============================================================
         * COLOR ANALYSIS
         * ============================================================
         */

        ColorAnalysisDto colorAnalysis =
            colorAnalysisRepository
                .findByCustomerId(
                    customerId
                )
                .map(
                    colorAnalysisMapper::toDto
                )
                .orElse(null);

        /*
         * ============================================================
         * STYLE RECOMMENDATION
         * ============================================================
         *
         * Il motore lavora anche con profili mancanti.
         *
         * Esempio:
         *
         * FaceProfile presente
         * ColorAnalysis assente
         *
         * può comunque fornire suggerimenti
         * relativi al taglio.
         */

        StyleRecommendationDto recommendations =
            styleRecommendationService.generate(
                customerId
            );

        /*
         * ============================================================
         * DTO FINALE
         * ============================================================
         */

        CustomerAnalysisDto result =
            new CustomerAnalysisDto();

        result.setCustomer(
            customer
        );

        result.setHairProfile(
            hairProfile
        );

        result.setFaceProfile(
            faceProfile
        );

        result.setColorAnalysis(
            colorAnalysis
        );

        result.setRecommendations(
            recommendations
        );

        /*
         * Verifichiamo quali sezioni
         * sono ancora incomplete.
         */

        if (hairProfile == null) {

            result.getMissingSections()
                .add(
                    "HAIR_PROFILE"
                );
        }

        if (faceProfile == null) {

            result.getMissingSections()
                .add(
                    "FACE_PROFILE"
                );
        }

        if (colorAnalysis == null) {

            result.getMissingSections()
                .add(
                    "COLOR_ANALYSIS"
                );
        }

        result.setCompleteProfile(
            result.getMissingSections()
                .isEmpty()
        );

        return result;
    }
}
