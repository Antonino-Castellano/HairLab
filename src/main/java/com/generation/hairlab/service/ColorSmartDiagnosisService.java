package com.generation.hairlab.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.hairlab.dto.ColorSmartDiagnosisDto;
import com.generation.hairlab.dto.ColorSmartDiagnosisRequestDto;
import com.generation.hairlab.enums.ColorApplicationType;
import com.generation.hairlab.enums.ColorDiagnosisFeasibility;
import com.generation.hairlab.enums.ColorDiagnosisStrategy;
import com.generation.hairlab.enums.HairCondition;
import com.generation.hairlab.enums.PhysicalValue;
import com.generation.hairlab.enums.Reflection;
import com.generation.hairlab.enums.ToneLevel;
import com.generation.hairlab.model.Customer;
import com.generation.hairlab.model.HairProfile;
import com.generation.hairlab.repository.CustomerRepository;
import com.generation.hairlab.repository.HairProfileRepository;

import lombok.RequiredArgsConstructor;

/**
 * Motore diagnostico deterministico del Color Lab.
 *
 * BLOCCO 4 - SMART FORMULA FASE 1
 *
 * Questo Service NON inventa ancora una formula.
 *
 * Analizza:
 *
 * - base attuale;
 * - differenza livelli;
 * - riflesso presente;
 * - target;
 * - condizione;
 * - porosità;
 * - storico chimico;
 * - controindicazioni;
 * - tipo di applicazione.
 *
 * Produce una strategia spiegabile
 * che sarà usata dal motore formula
 * nei blocchi successivi.
 */
@Service
@RequiredArgsConstructor
public class ColorSmartDiagnosisService {

    private final CustomerRepository
        customerRepository;

    private final HairProfileRepository
        hairProfileRepository;

    /**
     * Analisi completa.
     */
    @Transactional(readOnly = true)
    public ColorSmartDiagnosisDto analyze(
            ColorSmartDiagnosisRequestDto request)
            throws ServiceException {

        Customer customer =
            getActiveCustomer(
                request.getCustomerId()
            );

        HairProfile profile =
            hairProfileRepository
                .findByCustomerId(
                    customer.getId()
                )
                .orElseThrow(
                    () ->
                        new ServiceException(
                            "Il cliente non possiede ancora un Profilo Capelli. "
                                + "Completa il profilo tecnico prima di usare Smart Formula.",
                            HttpStatus.CONFLICT
                        )
                );

        ColorSmartDiagnosisDto result =
            buildBaseResult(
                profile,
                request
            );

        List<String> reasons =
            new ArrayList<>();

        List<String> warnings =
            new ArrayList<>();

        Set<Reflection> reflectionFamilies =
            new LinkedHashSet<>();

        /*
         * ====================================================
         * DIFFERENZA DI ALTEZZA DI TONO
         * ====================================================
         */

        int currentLevel =
            toneNumber(
                profile.getCurrentTone()
            );

        int targetLevel =
            toneNumber(
                request.getTargetToneLevel()
            );

        int difference =
            targetLevel -
            currentLevel;

        result.setToneDifference(
            difference
        );

        /*
         * ====================================================
         * FATTORI DI PRUDENZA
         * ====================================================
         */

        boolean hasContraindications =
            profile.getContraindications() != null
            &&
            !profile.getContraindications().isEmpty();

        boolean hasSensitivities =
            profile.getSensitivities() != null
            &&
            !profile.getSensitivities().isEmpty();

        boolean hasChemicalHistory =
            profile.getChemicalHistory() != null
            &&
            !profile.getChemicalHistory().isEmpty();

        boolean chemicallyTreated =
            profile.getHairCondition() ==
                HairCondition.CHEMICALLY_TREATED
            ||
            hasChemicalHistory;

        boolean damaged =
            profile.getHairCondition() ==
                HairCondition.DAMAGED;

        boolean highPorosity =
            profile.getPorosity() ==
                PhysicalValue.HIGH;

        /*
         * ====================================================
         * STRATEGIA BASE
         * ====================================================
         */

        ColorDiagnosisFeasibility feasibility;
        ColorDiagnosisStrategy strategy;
        boolean automaticCandidate = true;

        if (
            hasContraindications
        ) {

            feasibility =
                ColorDiagnosisFeasibility
                    .PROFESSIONAL_REVIEW_REQUIRED;

            strategy =
                ColorDiagnosisStrategy
                    .PROFESSIONAL_ASSESSMENT;

            automaticCandidate =
                false;

            reasons.add(
                "Sono presenti controindicazioni tecniche registrate: "
                    + "il motore non deve proporre automaticamente una formula esecutiva."
            );

        } else if (
            difference >= 3
        ) {

            feasibility =
                ColorDiagnosisFeasibility
                    .PRELIGHTENING_LIKELY;

            strategy =
                ColorDiagnosisStrategy
                    .PRELIGHTEN_THEN_TONE;

            automaticCandidate =
                false;

            reasons.add(
                "L'obiettivo è più chiaro di "
                    + difference
                    + " livelli rispetto alla base attuale."
            );

            reasons.add(
                "Una differenza di questa entità richiede normalmente "
                    + "una strategia di schiaritura preliminare prima della tonalizzazione."
            );

        } else if (
            difference > 0
            &&
            chemicallyTreated
        ) {

            feasibility =
                ColorDiagnosisFeasibility
                    .PROFESSIONAL_REVIEW_REQUIRED;

            strategy =
                ColorDiagnosisStrategy
                    .PROFESSIONAL_ASSESSMENT;

            automaticCandidate =
                false;

            reasons.add(
                "La richiesta prevede una schiaritura su capelli con storico chimico."
            );

            reasons.add(
                "Il tono attuale potrebbe derivare da pigmento cosmetico: "
                    + "prima della formula automatica va verificata la reale base di lavoro."
            );

        } else if (
            difference == 1
            ||
            difference == 2
        ) {

            feasibility =
                ColorDiagnosisFeasibility
                    .CONTROLLED_LIFT;

            strategy =
                ColorDiagnosisStrategy
                    .CONTROLLED_LIFT_AND_DEPOSIT;

            reasons.add(
                "L'obiettivo richiede una schiaritura contenuta di "
                    + difference
                    + " livello/i."
            );

        } else if (
            difference <= -3
        ) {

            feasibility =
                ColorDiagnosisFeasibility
                    .DARKENING_FILL_LIKELY;

            strategy =
                ColorDiagnosisStrategy
                    .PREPIGMENT_THEN_DARKEN;

            automaticCandidate =
                false;

            reasons.add(
                "L'obiettivo è più scuro di "
                    + Math.abs(
                        difference
                    )
                    + " livelli."
            );

            reasons.add(
                "Uno scurimento importante può richiedere "
                    + "ricostruzione dei pigmenti mancanti / pre-pigmentazione."
            );

        } else if (
            difference < 0
        ) {

            feasibility =
                ColorDiagnosisFeasibility
                    .DIRECT_OR_LOW_COMPLEXITY;

            strategy =
                ColorDiagnosisStrategy
                    .DARKEN_AND_DEPOSIT;

            reasons.add(
                "L'obiettivo richiede uno scurimento contenuto di "
                    + Math.abs(
                        difference
                    )
                    + " livello/i."
            );

        } else {

            feasibility =
                ColorDiagnosisFeasibility
                    .DIRECT_OR_LOW_COMPLEXITY;

            strategy =
                ColorDiagnosisStrategy
                    .DEPOSIT_OR_TONE;

            reasons.add(
                "Base attuale e target hanno la stessa altezza di tono."
            );

            reasons.add(
                "Il lavoro principale è quindi la gestione del riflesso "
                    + "e del deposito cromatico."
            );
        }

        /*
         * ====================================================
         * APPLICAZIONE
         * ====================================================
         */

        if (
            request.getApplicationType() ==
                ColorApplicationType.TONING
            &&
            difference > 1
        ) {

            warnings.add(
                "È stata scelta una tonalizzazione, ma il target è sensibilmente più chiaro "
                    + "della base: una tonalizzazione da sola non risolve la differenza di livello."
            );

            automaticCandidate =
                false;
        }

        if (
            request.getApplicationType() ==
                ColorApplicationType.FULL_HEAD
            &&
            chemicallyTreated
        ) {

            warnings.add(
                "Su testa completa con storico chimico, radice e lunghezze "
                    + "potrebbero richiedere gestione differenziata."
            );
        }

        /*
         * ====================================================
         * DIREZIONE DEI RIFLESSI
         * ====================================================
         */

        addTargetReflection(
            reflectionFamilies,
            request.getTargetPrimaryReflection()
        );

        addTargetReflection(
            reflectionFamilies,
            request.getTargetSecondaryReflection()
        );

        Reflection correction =
            correctionFor(
                profile.getReflection()
            );

        if (
            correction != null
            &&
            shouldNeutralize(
                profile.getReflection(),
                request.getTargetPrimaryReflection()
            )
        ) {

            reflectionFamilies.add(
                correction
            );

            reasons.add(
                "Il riflesso attuale "
                    + profile.getReflection()
                    + " può richiedere una componente correttiva della famiglia "
                    + correction
                    + " per accompagnare il target."
            );
        }

        /*
         * ====================================================
         * WARNING STRUTTURALI
         * ====================================================
         */

        if (
            damaged
        ) {

            warnings.add(
                "Il capello è registrato come danneggiato: "
                    + "evitare strategie aggressive senza verifica professionale."
            );

            if (
                difference > 0
            ) {

                automaticCandidate =
                    false;

                feasibility =
                    ColorDiagnosisFeasibility
                        .PROFESSIONAL_REVIEW_REQUIRED;

                strategy =
                    ColorDiagnosisStrategy
                        .PROFESSIONAL_ASSESSMENT;
            }
        }

        if (
            highPorosity
        ) {

            warnings.add(
                "Porosità alta: assorbimento e tenuta del pigmento "
                    + "possono essere disomogenei."
            );
        }

        if (
            hasSensitivities
        ) {

            warnings.add(
                "Sono presenti sensibilità registrate nel Profilo Capelli."
            );
        }

        if (
            chemicallyTreated
        ) {

            warnings.add(
                "È presente uno storico chimico: verificare compatibilità "
                    + "e residui cosmetici prima dell'esecuzione."
            );
        }

        if (
            profile.getHairLength() ==
                null
        ) {

            warnings.add(
                "Lunghezza capelli non registrata: "
                    + "la futura grammatura automatica non potrà essere calcolata con precisione."
            );
        }

        /*
         * La prova ciocca è raccomandata quando:
         * - c'è schiaritura;
         * - c'è storico chimico;
         * - capello danneggiato;
         * - alta porosità;
         * - controindicazioni.
         */
        boolean strandTestRecommended =
            difference > 0
            ||
            chemicallyTreated
            ||
            damaged
            ||
            highPorosity
            ||
            hasContraindications;

        result.setFeasibility(
            feasibility
        );

        result.setStrategy(
            strategy
        );

        result.setRecommendedReflectionFamilies(
            new ArrayList<>(
                reflectionFamilies
            )
        );

        result.setReasons(
            reasons
        );

        result.setWarnings(
            warnings
        );

        result.setAutomaticFormulaCandidate(
            automaticCandidate
        );

        result.setStrandTestRecommended(
            strandTestRecommended
        );

        return result;
    }

    private ColorSmartDiagnosisDto buildBaseResult(
            HairProfile profile,
            ColorSmartDiagnosisRequestDto request) {

        ColorSmartDiagnosisDto result =
            new ColorSmartDiagnosisDto();

        result.setCustomerId(
            profile.getCustomer().getId()
        );

        result.setHairProfileId(
            profile.getId()
        );

        result.setNaturalTone(
            profile.getNaturalTone()
        );

        result.setCurrentTone(
            profile.getCurrentTone()
        );

        result.setCurrentReflection(
            profile.getReflection()
        );

        result.setHairLength(
            profile.getHairLength()
        );

        result.setTexture(
            profile.getTexture()
        );

        result.setDensity(
            profile.getDensity()
        );

        result.setPorosity(
            profile.getPorosity()
        );

        result.setHairCondition(
            profile.getHairCondition()
        );

        result.setTargetToneLevel(
            request.getTargetToneLevel()
        );

        result.setTargetPrimaryReflection(
            request.getTargetPrimaryReflection()
        );

        result.setTargetSecondaryReflection(
            request.getTargetSecondaryReflection()
        );

        result.setApplicationType(
            request.getApplicationType()
        );

        result.setTargetResult(
            normalizeNullable(
                request.getTargetResult()
            )
        );

        return result;
    }

    private Customer getActiveCustomer(
            Integer customerId)
            throws ServiceException {

        Customer customer =
            customerRepository
                .findById(
                    customerId
                )
                .orElseThrow(
                    () ->
                        new ServiceException(
                            "Cliente non trovato con id: "
                                + customerId,
                            HttpStatus.NOT_FOUND
                        )
                );

        if (
            !customer.isActive()
        ) {

            throw new ServiceException(
                "Il cliente selezionato non è attivo",
                HttpStatus.CONFLICT
            );
        }

        return customer;
    }

    /**
     * Mapping esplicito enum -> numero tecnico.
     */
    private int toneNumber(
            ToneLevel tone) {

        return switch (
            tone
        ) {

            case LEVEL_1_BLACK -> 1;
            case LEVEL_2_VERY_DARK_BROWN -> 2;
            case LEVEL_3_DARK_BROWN -> 3;
            case LEVEL_4_MEDIUM_BROWN -> 4;
            case LEVEL_5_LIGHT_BROWN -> 5;
            case LEVEL_6_DARK_BLONDE -> 6;
            case LEVEL_7_MEDIUM_BLONDE -> 7;
            case LEVEL_8_LIGHT_BLONDE -> 8;
            case LEVEL_9_VERY_LIGHT_BLONDE -> 9;
            case LEVEL_10_LIGHTEST_BLONDE -> 10;
        };
    }

    /**
     * Famiglia correttiva cromatica indicativa.
     *
     * Non equivale ancora alla quantità
     * di correttore da inserire.
     */
    private Reflection correctionFor(
            Reflection currentReflection) {

        if (
            currentReflection ==
            null
        ) {

            return null;
        }

        return switch (
            currentReflection
        ) {

            case GOLD ->
                Reflection.VIOLET;

            case COPPER ->
                Reflection.BLUE;

            case RED,
                 RED_VIOLET,
                 MAHOGANY ->
                Reflection.GREEN;

            default ->
                null;
        };
    }

    /**
     * Evita di suggerire neutralizzazione
     * quando il target vuole mantenere
     * chiaramente la stessa famiglia calda.
     */
    private boolean shouldNeutralize(
            Reflection current,
            Reflection target) {

        if (
            current == null
            ||
            target == null
        ) {

            return false;
        }

        if (
            current == target
        ) {

            return false;
        }

        boolean targetWarm =
            target ==
                Reflection.GOLD
            ||
            target ==
                Reflection.COPPER
            ||
            target ==
                Reflection.RED
            ||
            target ==
                Reflection.RED_VIOLET
            ||
            target ==
                Reflection.MAHOGANY;

        return !targetWarm;
    }

    private void addTargetReflection(
            Set<Reflection> reflections,
            Reflection reflection) {

        if (
            reflection != null
            &&
            reflection !=
                Reflection.OTHER
        ) {

            reflections.add(
                reflection
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
