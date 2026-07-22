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
 * di una cliente HairLab.
 *
 * Non salva nuovi dati.
 *
 * Raccoglie:
 *
 * Customer
 * HairProfile
 * FaceProfile
 * ColorAnalysis
 * StyleRecommendation
 *
 * e restituisce tutto tramite
 * un singolo CustomerAnalysisDto.
 */
@Service
@RequiredArgsConstructor
public class CustomerAnalysisService {

    /**
     * Service clienti.
     */
    private final CustomerService customerService;

    /**
     * Repository HairProfile.
     */
    private final HairProfileRepository hairProfileRepository;

    /**
     * Repository FaceProfile.
     */
    private final FaceProfileRepository faceProfileRepository;

    /**
     * Repository ColorAnalysis.
     */
    private final ColorAnalysisRepository colorAnalysisRepository;

    /**
     * Mapper HairProfile.
     */
    private final HairProfileMapper hairProfileMapper;

    /**
     * Mapper FaceProfile.
     */
    private final FaceProfileMapper faceProfileMapper;

    /**
     * Mapper ColorAnalysis.
     */
    private final ColorAnalysisMapper colorAnalysisMapper;

    /**
     * Motore dei suggerimenti HairLab.
     */
    private final StyleRecommendationService
        styleRecommendationService;

    /**
     * Costruisce l'intera analisi
     * di una cliente.
     *
     * I profili mancanti vengono restituiti come null.
     *
     * Questo permette alla pagina cliente
     * di essere caricata anche quando alcune
     * sezioni non sono ancora state compilate.
     */
    @Transactional(readOnly = true)
    public CustomerAnalysisDto findByCustomerId(
            Integer customerId)
            throws ServiceException {

        /*
         * ========================================================
         * CUSTOMER
         * ========================================================
         */

        CustomerDto customer =
            customerService.findById(
                customerId
            );

        /*
         * ========================================================
         * HAIR PROFILE
         * ========================================================
         *
         * Utilizziamo direttamente
         * findByCustomerId().
         *
         * Evitiamo:
         *
         * findAll()
         * -> stream()
         * -> filter()
         *
         * che caricherebbe inutilmente
         * tutte le HairProfile dal database.
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
         * ========================================================
         * FACE PROFILE
         * ========================================================
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
         * ========================================================
         * COLOR ANALYSIS
         * ========================================================
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
         * ========================================================
         * STYLE RECOMMENDATIONS
         * ========================================================
         *
         * Il motore è dinamico e non viene salvato.
         *
         * Può produrre suggerimenti anche
         * con profili parzialmente compilati.
         */

        StyleRecommendationDto recommendations =
            styleRecommendationService.generate(
                customerId
            );

        /*
         * ========================================================
         * DTO AGGREGATO
         * ========================================================
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
         * ========================================================
         * CONTROLLO COMPLETEZZA
         * ========================================================
         */

        if (hairProfile == null) {

            result
                .getMissingSections()
                .add(
                    "HAIR_PROFILE"
                );
        }

        if (faceProfile == null) {

            result
                .getMissingSections()
                .add(
                    "FACE_PROFILE"
                );
        }

        if (colorAnalysis == null) {

            result
                .getMissingSections()
                .add(
                    "COLOR_ANALYSIS"
                );
        }

        /*
         * Il profilo è completo solamente
         * se non manca nessuna sezione.
         */
        result.setCompleteProfile(
            result
                .getMissingSections()
                .isEmpty()
        );

        return result;
    }
}