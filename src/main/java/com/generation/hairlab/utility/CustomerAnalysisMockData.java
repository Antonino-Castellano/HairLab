package com.generation.hairlab.utility;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.generation.hairlab.enums.ColorAnalysisEnums.Chroma;
import com.generation.hairlab.enums.ColorAnalysisEnums.ColorSeason;
import com.generation.hairlab.enums.ColorAnalysisEnums.ColorSubSeason;
import com.generation.hairlab.enums.ColorAnalysisEnums.ColorValue;
import com.generation.hairlab.enums.ColorAnalysisEnums.ContrastLevel;
import com.generation.hairlab.enums.ColorAnalysisEnums.MetalType;
import com.generation.hairlab.enums.ColorAnalysisEnums.SkinTone;
import com.generation.hairlab.enums.ColorAnalysisEnums.Undertone;
import com.generation.hairlab.enums.FaceProfileEnums.ChinProjection;
import com.generation.hairlab.enums.FaceProfileEnums.ChinShape;
import com.generation.hairlab.enums.FaceProfileEnums.EyeColor;
import com.generation.hairlab.enums.FaceProfileEnums.EyeOrientation;
import com.generation.hairlab.enums.FaceProfileEnums.EyeShape;
import com.generation.hairlab.enums.FaceProfileEnums.EyeSpacing;
import com.generation.hairlab.enums.FaceProfileEnums.EyebrowShape;
import com.generation.hairlab.enums.FaceProfileEnums.FaceShape;
import com.generation.hairlab.enums.FaceProfileEnums.HairlineShape;
import com.generation.hairlab.enums.FaceProfileEnums.JawShape;
import com.generation.hairlab.enums.FaceProfileEnums.Length;
import com.generation.hairlab.enums.FaceProfileEnums.Level;
import com.generation.hairlab.enums.FaceProfileEnums.LipBalance;
import com.generation.hairlab.enums.FaceProfileEnums.LipFullness;
import com.generation.hairlab.enums.FaceProfileEnums.LipShape;
import com.generation.hairlab.enums.FaceProfileEnums.NoseProfile;
import com.generation.hairlab.enums.FaceProfileEnums.NoseTip;
import com.generation.hairlab.enums.FaceProfileEnums.Size;
import com.generation.hairlab.enums.FaceProfileEnums.Thickness;
import com.generation.hairlab.enums.FaceProfileEnums.Width;
import com.generation.hairlab.model.ColorAnalysis;
import com.generation.hairlab.model.Customer;
import com.generation.hairlab.model.FaceProfile;
import com.generation.hairlab.repository.ColorAnalysisRepository;
import com.generation.hairlab.repository.CustomerRepository;
import com.generation.hairlab.repository.FaceProfileRepository;

import lombok.RequiredArgsConstructor;

/**
 * Aggiunge dati mock relativi a:
 *
 * - profilo morfologico del viso;
 * - analisi cromatica;
 *
 * per le clienti mock già presenti nel database.
 *
 * Questa classe è separata da MockDataDb perché il database
 * potrebbe essere già popolato.
 *
 * I dati vengono creati solamente se il cliente non possiede
 * già FaceProfile o ColorAnalysis.
 *
 * In questo modo:
 *
 * - non vengono creati duplicati;
 * - eventuali dati inseriti manualmente non vengono sovrascritti;
 * - possiamo aggiungere nuovi mock anche a database già popolato.
 */
@Component
@RequiredArgsConstructor
public class CustomerAnalysisMockData {

    private final CustomerRepository customerRepository;

    private final FaceProfileRepository faceProfileRepository;

    private final ColorAnalysisRepository colorAnalysisRepository;

    /**
     * ApplicationReadyEvent viene eseguito quando
     * l'applicazione Spring ha completato l'avvio.
     *
     * A quel punto MockDataDb ha già avuto la possibilità
     * di creare Maria, Giulia e Sara.
     */
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void loadCustomerAnalysisMockData() {

        Customer maria = findCustomerByEmail(
            "maria.esposito@mock.it"
        );

        Customer giulia = findCustomerByEmail(
            "giulia.romano@mock.it"
        );

        Customer sara = findCustomerByEmail(
            "sara.ferrari@mock.it"
        );

        /*
         * ============================================================
         * MARIA ESPOSITO
         * ============================================================
         */

        if (maria != null) {

            createMariaFaceProfile(maria);

            createMariaColorAnalysis(maria);
        }

        /*
         * ============================================================
         * GIULIA ROMANO
         * ============================================================
         */

        if (giulia != null) {

            createGiuliaFaceProfile(giulia);

            createGiuliaColorAnalysis(giulia);
        }

        /*
         * ============================================================
         * SARA FERRARI
         * ============================================================
         */

        if (sara != null) {

            createSaraFaceProfile(sara);

            createSaraColorAnalysis(sara);
        }

        System.out.println(
            "HairLab CustomerAnalysisMockData: controllo profili completato."
        );
    }

    /**
     * Cerca una cliente tramite email.
     *
     * Utilizziamo findAll() per non dipendere
     * da eventuali metodi personalizzati del Repository.
     */
    private Customer findCustomerByEmail(
            String email) {

        return customerRepository
            .findAll()
            .stream()
            .filter(
                customer ->
                    email.equalsIgnoreCase(
                        customer.getEmail()
                    )
            )
            .findFirst()
            .orElse(null);
    }

    /*
     * ================================================================
     * MARIA - FACE PROFILE
     * ================================================================
     */

    private void createMariaFaceProfile(
            Customer maria) {

        if (
            faceProfileRepository
                .existsByCustomerId(
                    maria.getId()
                )
        ) {

            return;
        }

        FaceProfile profile =
            new FaceProfile();

        profile.setCustomer(maria);

        profile.setFaceShape(
            FaceShape.OVAL
        );

        profile.setForeheadHeight(
            Level.HIGH
        );

        profile.setForeheadWidth(
            Width.MEDIUM
        );

        profile.setHairlineShape(
            HairlineShape.ROUNDED
        );

        profile.setEyeShape(
            EyeShape.ALMOND
        );

        profile.setEyeOrientation(
            EyeOrientation.UPTURNED
        );

        profile.setEyeSpacing(
            EyeSpacing.PROPORTIONATE
        );

        profile.setEyeSize(
            Size.MEDIUM
        );

        profile.setEyeColor(
            EyeColor.DARK_BROWN
        );

        profile.setEyeColorNotes(
            "Castano scuro uniforme."
        );

        profile.setEyebrowShape(
            EyebrowShape.SOFT_ARCH
        );

        profile.setEyebrowThickness(
            Thickness.MEDIUM
        );

        profile.setNoseLength(
            Length.MEDIUM
        );

        profile.setNoseWidth(
            Width.MEDIUM
        );

        profile.setNoseProfile(
            NoseProfile.STRAIGHT
        );

        profile.setNoseTip(
            NoseTip.ROUNDED
        );

        profile.setCheekboneWidth(
            Width.WIDE
        );

        profile.setCheekboneProminence(
            Level.HIGH
        );

        profile.setJawWidth(
            Width.MEDIUM
        );

        profile.setJawDefinition(
            Level.LOW
        );

        profile.setJawShape(
            JawShape.ROUNDED
        );

        profile.setChinShape(
            ChinShape.ROUNDED
        );

        profile.setChinProjection(
            ChinProjection.BALANCED
        );

        profile.setMouthWidth(
            Width.MEDIUM
        );

        profile.setLipFullness(
            LipFullness.FULL
        );

        profile.setLipBalance(
            LipBalance.LOWER_FULLER
        );

        profile.setLipShape(
            LipShape.DEFINED_CUPIDS_BOW
        );

        profile.setNotes(
            "Viso ovale leggermente allungato con zigomi evidenti e lineamenti armonici."
        );

        profile.setStylingGoals(
            "Valorizzare gli zigomi, riequilibrare visivamente la fronte e mantenere morbidezza nei lineamenti."
        );

        profile.setCreatedAt(
            LocalDateTime.now()
        );

        profile.setUpdatedAt(
            LocalDateTime.now()
        );

        faceProfileRepository.save(
            profile
        );
    }

    /*
     * ================================================================
     * MARIA - COLOR ANALYSIS
     * ================================================================
     */

    private void createMariaColorAnalysis(
            Customer maria) {

        if (
            colorAnalysisRepository
                .existsByCustomerId(
                    maria.getId()
                )
        ) {

            return;
        }

        ColorAnalysis analysis =
            new ColorAnalysis();

        analysis.setCustomer(maria);

        analysis.setSkinTone(
            SkinTone.LIGHT_MEDIUM
        );

        analysis.setUndertone(
            Undertone.COOL
        );

        analysis.setSeason(
            ColorSeason.WINTER
        );

        analysis.setSubSeason(
            ColorSubSeason.DEEP_WINTER
        );

        analysis.setColorValue(
            ColorValue.DEEP
        );

        analysis.setContrastLevel(
            ContrastLevel.HIGH
        );

        analysis.setChroma(
            Chroma.BRIGHT
        );

        analysis.setBestColors(
            new HashMap<>(
                Map.of(
                    "Borgogna",
                    "#6D213C",

                    "Blu notte",
                    "#172A46",

                    "Verde smeraldo",
                    "#006B54",

                    "Viola freddo",
                    "#563D7C",

                    "Rosso rubino",
                    "#9B111E"
                )
            )
        );

        analysis.setAvoidColors(
            new HashMap<>(
                Map.of(
                    "Senape",
                    "#D4A017",

                    "Camel",
                    "#C19A6B",

                    "Arancio caldo",
                    "#D96C32"
                )
            )
        );

        analysis.setBestMetals(
            new HashSet<>(
                Set.of(
                    MetalType.SILVER,
                    MetalType.WHITE_GOLD
                )
            )
        );

        analysis.setNotes(
            "Elevato contrasto naturale. Valorizzata da colori profondi, freddi e relativamente saturi."
        );

        analysis.setCreatedAt(
            LocalDateTime.now()
        );

        analysis.setUpdatedAt(
            LocalDateTime.now()
        );

        colorAnalysisRepository.save(
            analysis
        );
    }

    /*
     * ================================================================
     * GIULIA - FACE PROFILE
     * ================================================================
     */

    private void createGiuliaFaceProfile(
            Customer giulia) {

        if (
            faceProfileRepository
                .existsByCustomerId(
                    giulia.getId()
                )
        ) {

            return;
        }

        FaceProfile profile =
            new FaceProfile();

        profile.setCustomer(giulia);

        profile.setFaceShape(
            FaceShape.HEART
        );

        profile.setForeheadHeight(
            Level.MEDIUM
        );

        profile.setForeheadWidth(
            Width.WIDE
        );

        profile.setHairlineShape(
            HairlineShape.WIDOW_PEAK
        );

        profile.setEyeShape(
            EyeShape.ROUND
        );

        profile.setEyeOrientation(
            EyeOrientation.NEUTRAL
        );

        profile.setEyeSpacing(
            EyeSpacing.PROPORTIONATE
        );

        profile.setEyeSize(
            Size.LARGE
        );

        profile.setEyeColor(
            EyeColor.HAZEL
        );

        profile.setEyeColorNotes(
            "Nocciola caldo con leggere sfumature verdi."
        );

        profile.setEyebrowShape(
            EyebrowShape.SOFT_ARCH
        );

        profile.setEyebrowThickness(
            Thickness.THICK
        );

        profile.setNoseLength(
            Length.SHORT
        );

        profile.setNoseWidth(
            Width.MEDIUM
        );

        profile.setNoseProfile(
            NoseProfile.CONCAVE
        );

        profile.setNoseTip(
            NoseTip.UPTURNED
        );

        profile.setCheekboneWidth(
            Width.MEDIUM
        );

        profile.setCheekboneProminence(
            Level.HIGH
        );

        profile.setJawWidth(
            Width.NARROW
        );

        profile.setJawDefinition(
            Level.MEDIUM
        );

        profile.setJawShape(
            JawShape.ROUNDED
        );

        profile.setChinShape(
            ChinShape.POINTED
        );

        profile.setChinProjection(
            ChinProjection.BALANCED
        );

        profile.setMouthWidth(
            Width.MEDIUM
        );

        profile.setLipFullness(
            LipFullness.MEDIUM
        );

        profile.setLipBalance(
            LipBalance.BALANCED
        );

        profile.setLipShape(
            LipShape.HEART_SHAPED
        );

        profile.setNotes(
            "Viso a cuore con zona superiore più ampia, zigomi evidenti e mento più sottile."
        );

        profile.setStylingGoals(
            "Creare equilibrio tra fronte e zona inferiore del volto e valorizzare gli zigomi."
        );

        profile.setCreatedAt(
            LocalDateTime.now()
        );

        profile.setUpdatedAt(
            LocalDateTime.now()
        );

        faceProfileRepository.save(
            profile
        );
    }

    /*
     * ================================================================
     * GIULIA - COLOR ANALYSIS
     * ================================================================
     */

    private void createGiuliaColorAnalysis(
            Customer giulia) {

        if (
            colorAnalysisRepository
                .existsByCustomerId(
                    giulia.getId()
                )
        ) {

            return;
        }

        ColorAnalysis analysis =
            new ColorAnalysis();

        analysis.setCustomer(giulia);

        analysis.setSkinTone(
            SkinTone.MEDIUM
        );

        analysis.setUndertone(
            Undertone.WARM
        );

        analysis.setSeason(
            ColorSeason.AUTUMN
        );

        analysis.setSubSeason(
            ColorSubSeason.WARM_AUTUMN
        );

        analysis.setColorValue(
            ColorValue.MEDIUM
        );

        analysis.setContrastLevel(
            ContrastLevel.MEDIUM
        );

        analysis.setChroma(
            Chroma.MEDIUM
        );

        analysis.setBestColors(
            new HashMap<>(
                Map.of(
                    "Terracotta",
                    "#C96A4A",

                    "Verde oliva",
                    "#6B7045",

                    "Chocolate",
                    "#5A3825",

                    "Petrolio caldo",
                    "#35605A",

                    "Ruggine",
                    "#A64B2A"
                )
            )
        );

        analysis.setAvoidColors(
            new HashMap<>(
                Map.of(
                    "Fucsia freddo",
                    "#D1007F",

                    "Grigio ghiaccio",
                    "#DCE1E5",

                    "Viola freddo",
                    "#603F83"
                )
            )
        );

        analysis.setBestMetals(
            new HashSet<>(
                Set.of(
                    MetalType.YELLOW_GOLD,
                    MetalType.BRONZE,
                    MetalType.COPPER
                )
            )
        );

        analysis.setNotes(
            "Dominante calda e naturale. Armonica con tonalità terrose, ricche e avvolgenti."
        );

        analysis.setCreatedAt(
            LocalDateTime.now()
        );

        analysis.setUpdatedAt(
            LocalDateTime.now()
        );

        colorAnalysisRepository.save(
            analysis
        );
    }

    /*
     * ================================================================
     * SARA - FACE PROFILE
     * ================================================================
     */

    private void createSaraFaceProfile(
            Customer sara) {

        if (
            faceProfileRepository
                .existsByCustomerId(
                    sara.getId()
                )
        ) {

            return;
        }

        FaceProfile profile =
            new FaceProfile();

        profile.setCustomer(sara);

        profile.setFaceShape(
            FaceShape.ROUND
        );

        profile.setForeheadHeight(
            Level.MEDIUM
        );

        profile.setForeheadWidth(
            Width.MEDIUM
        );

        profile.setHairlineShape(
            HairlineShape.ROUNDED
        );

        profile.setEyeShape(
            EyeShape.ALMOND
        );

        profile.setEyeOrientation(
            EyeOrientation.DOWNTURNED
        );

        profile.setEyeSpacing(
            EyeSpacing.WIDE_SET
        );

        profile.setEyeSize(
            Size.MEDIUM
        );

        profile.setEyeColor(
            EyeColor.GREY_BLUE
        );

        profile.setEyeColorNotes(
            "Grigio-blu delicato."
        );

        profile.setEyebrowShape(
            EyebrowShape.STRAIGHT
        );

        profile.setEyebrowThickness(
            Thickness.MEDIUM
        );

        profile.setNoseLength(
            Length.MEDIUM
        );

        profile.setNoseWidth(
            Width.NARROW
        );

        profile.setNoseProfile(
            NoseProfile.STRAIGHT
        );

        profile.setNoseTip(
            NoseTip.FINE
        );

        profile.setCheekboneWidth(
            Width.WIDE
        );

        profile.setCheekboneProminence(
            Level.MEDIUM
        );

        profile.setJawWidth(
            Width.MEDIUM
        );

        profile.setJawDefinition(
            Level.LOW
        );

        profile.setJawShape(
            JawShape.ROUNDED
        );

        profile.setChinShape(
            ChinShape.ROUNDED
        );

        profile.setChinProjection(
            ChinProjection.BALANCED
        );

        profile.setMouthWidth(
            Width.WIDE
        );

        profile.setLipFullness(
            LipFullness.MEDIUM
        );

        profile.setLipBalance(
            LipBalance.BALANCED
        );

        profile.setLipShape(
            LipShape.ROUNDED
        );

        profile.setNotes(
            "Viso rotondo con lineamenti morbidi e predominanza della larghezza centrale."
        );

        profile.setStylingGoals(
            "Creare maggiore slancio verticale e limitare eccessivo volume nella zona delle guance."
        );

        profile.setCreatedAt(
            LocalDateTime.now()
        );

        profile.setUpdatedAt(
            LocalDateTime.now()
        );

        faceProfileRepository.save(
            profile
        );
    }

    /*
     * ================================================================
     * SARA - COLOR ANALYSIS
     * ================================================================
     */

    private void createSaraColorAnalysis(
            Customer sara) {

        if (
            colorAnalysisRepository
                .existsByCustomerId(
                    sara.getId()
                )
        ) {

            return;
        }

        ColorAnalysis analysis =
            new ColorAnalysis();

        analysis.setCustomer(sara);

        analysis.setSkinTone(
            SkinTone.LIGHT
        );

        analysis.setUndertone(
            Undertone.NEUTRAL_COOL
        );

        analysis.setSeason(
            ColorSeason.SUMMER
        );

        analysis.setSubSeason(
            ColorSubSeason.SOFT_SUMMER
        );

        analysis.setColorValue(
            ColorValue.LIGHT
        );

        analysis.setContrastLevel(
            ContrastLevel.LOW
        );

        analysis.setChroma(
            Chroma.SOFT
        );

        analysis.setBestColors(
            new HashMap<>(
                Map.of(
                    "Rosa polvere",
                    "#C99BA3",

                    "Malva",
                    "#A98A9C",

                    "Blu polvere",
                    "#8297A8",

                    "Verde salvia",
                    "#9BAA96",

                    "Mushroom",
                    "#93877C"
                )
            )
        );

        analysis.setAvoidColors(
            new HashMap<>(
                Map.of(
                    "Arancio acceso",
                    "#F36B21",

                    "Nero assoluto",
                    "#000000",

                    "Giallo brillante",
                    "#FFD700"
                )
            )
        );

        analysis.setBestMetals(
            new HashSet<>(
                Set.of(
                    MetalType.SILVER,
                    MetalType.WHITE_GOLD,
                    MetalType.ROSE_GOLD
                )
            )
        );

        analysis.setNotes(
            "Contrasto delicato e cromie morbide. Valorizzata da tonalità fredde, polverose e poco sature."
        );

        analysis.setCreatedAt(
            LocalDateTime.now()
        );

        analysis.setUpdatedAt(
            LocalDateTime.now()
        );

        colorAnalysisRepository.save(
            analysis
        );
    }
}