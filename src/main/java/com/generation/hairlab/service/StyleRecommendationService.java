package com.generation.hairlab.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.generation.hairlab.dto.RecommendationItemDto;
import com.generation.hairlab.dto.StyleRecommendationDto;
import com.generation.hairlab.enums.ColorAnalysisEnums.ColorSeason;
import com.generation.hairlab.enums.ColorAnalysisEnums.ColorSubSeason;
import com.generation.hairlab.enums.FaceProfileEnums.FaceShape;
import com.generation.hairlab.enums.FaceProfileEnums.Level;
import com.generation.hairlab.enums.HairType;
import com.generation.hairlab.enums.PhysicalValue;
import com.generation.hairlab.model.ColorAnalysis;
import com.generation.hairlab.model.FaceProfile;
import com.generation.hairlab.model.HairProfile;
import com.generation.hairlab.repository.ColorAnalysisRepository;
import com.generation.hairlab.repository.FaceProfileRepository;
import com.generation.hairlab.repository.HairProfileRepository;

import lombok.RequiredArgsConstructor;

/**
 * Motore di suggerimento HairLab.
 *
 * Combina:
 *
 * - HairProfile;
 * - FaceProfile;
 * - ColorAnalysis.
 *
 * I risultati sono indicativi e devono essere
 * valutati dal professionista.
 */
@Service
@RequiredArgsConstructor
public class StyleRecommendationService {

    private final CustomerService customerService;

    private final HairProfileRepository hairProfileRepository;

    private final FaceProfileRepository faceProfileRepository;

    private final ColorAnalysisRepository colorAnalysisRepository;

    /**
     * Genera i suggerimenti in tempo reale.
     */
    public StyleRecommendationDto generate(
            Integer customerId)
            throws ServiceException {

        /**
         * Verifica che il cliente esista.
         */
        customerService.getCustomerById(
            customerId
        );

        HairProfile hairProfile =
            hairProfileRepository
                .findByCustomerId(customerId)
                .orElse(null);

        FaceProfile faceProfile =
            faceProfileRepository
                .findByCustomerId(customerId)
                .orElse(null);

        ColorAnalysis colorAnalysis =
            colorAnalysisRepository
                .findByCustomerId(customerId)
                .orElse(null);

        StyleRecommendationDto result =
            new StyleRecommendationDto();

        result.setCustomerId(
            customerId
        );

        result.setGeneratedAt(
            LocalDateTime.now()
        );

        result.setHairProfileAvailable(
            hairProfile != null
        );

        result.setFaceProfileAvailable(
            faceProfile != null
        );

        result.setColorAnalysisAvailable(
            colorAnalysis != null
        );

        /*
         * TAGLIO E FRANGIA.
         */
        if (faceProfile != null) {

            result.setHaircutRecommendations(
                buildHaircuts(
                    faceProfile,
                    hairProfile
                )
            );

            result.setFringeRecommendations(
                buildFringes(
                    faceProfile
                )
            );
        }

        /*
         * COLORI.
         */
        if (colorAnalysis != null) {

            result.setColorRecommendations(
                buildColors(
                    colorAnalysis
                )
            );
        }

        /*
         * STYLING.
         */
        if (
            faceProfile != null ||
            hairProfile != null
        ) {

            result.setStylingRecommendations(
                buildStyling(
                    faceProfile,
                    hairProfile
                )
            );
        }

        /*
         * CAUTELE TECNICHE.
         */
        if (hairProfile != null) {

            result.setTechnicalWarnings(
                buildTechnicalWarnings(
                    hairProfile
                )
            );
        }

        result.getGeneralNotes().add(
            "I suggerimenti HairLab rappresentano indicazioni di supporto alla consulenza e non sostituiscono la valutazione professionale."
        );

        if (
            faceProfile == null
        ) {

            result.getGeneralNotes().add(
                "Completa il profilo del viso per ottenere suggerimenti più precisi su taglio, frangia e styling."
            );
        }

        if (
            colorAnalysis == null
        ) {

            result.getGeneralNotes().add(
                "Completa l'analisi cromatica per ottenere suggerimenti personalizzati sul colore."
            );
        }

        if (
            hairProfile == null
        ) {

            result.getGeneralNotes().add(
                "Completa il profilo capelli per valutare meglio fattibilità e cautele tecniche."
            );
        }

        return result;
    }

    /**
     * Suggerimenti taglio.
     */
    private List<RecommendationItemDto>
            buildHaircuts(
                FaceProfile face,
                HairProfile hair) {

        List<RecommendationItemDto> result =
            new ArrayList<>();

        FaceShape shape =
            face.getFaceShape();

        if (shape == null) {
            return result;
        }

        switch (shape) {

            case OVAL -> {

                result.add(
                    recommendation(
                        "Long bob",
                        "Taglio versatile che mantiene equilibrio e proporzioni armoniche.",
                        92,
                        "La forma ovale permette una grande varietà di lunghezze.",
                        "Il long bob valorizza il volto senza appesantirlo."
                    )
                );

                result.add(
                    recommendation(
                        "Bob medio texturizzato",
                        "Bob con movimento e texture controllata.",
                        88,
                        "Valorizza la struttura equilibrata del viso.",
                        "Permette di enfatizzare zigomi e linea mandibolare."
                    )
                );
            }

            case ROUND -> {

                result.add(
                    recommendation(
                        "Long bob sotto il mento",
                        "Lunghezza leggermente verticale che aiuta ad allungare visivamente il volto.",
                        91,
                        "La lunghezza sotto il mento evita di enfatizzare la larghezza centrale.",
                        "Le linee verticali aumentano la percezione di slancio."
                    )
                );

                result.add(
                    recommendation(
                        "Scalatura lunga",
                        "Scalatura morbida con volume controllato.",
                        85,
                        "Evita eccessivo volume laterale.",
                        "Crea movimento verticale."
                    )
                );
            }

            case SQUARE,
                 RECTANGULAR -> {

                result.add(
                    recommendation(
                        "Taglio medio con linee morbide",
                        "Lunghezza media con scalature e movimento.",
                        90,
                        "Le linee morbide contrastano delicatamente una mandibola definita.",
                        "Il movimento evita un effetto eccessivamente geometrico."
                    )
                );

                result.add(
                    recommendation(
                        "Long bob morbido",
                        "Bob allungato con punte leggere.",
                        86,
                        "Ammorbidisce visivamente gli angoli del volto."
                    )
                );
            }

            case OBLONG -> {

                result.add(
                    recommendation(
                        "Bob medio",
                        "Taglio che crea maggiore presenza laterale.",
                        93,
                        "Aiuta a bilanciare la verticalità del volto.",
                        "Può creare volume nella zona laterale."
                    )
                );

                result.add(
                    recommendation(
                        "Long bob alla clavicola",
                        "Lunghezza media con movimento laterale.",
                        90,
                        "Evita di accentuare eccessivamente la lunghezza del volto."
                    )
                );
            }

            case HEART,
                 INVERTED_TRIANGLE -> {

                result.add(
                    recommendation(
                        "Bob alla mandibola",
                        "Taglio che introduce volume nella zona inferiore.",
                        90,
                        "Bilancia una parte superiore del volto visivamente più ampia.",
                        "Valorizza la linea del mento."
                    )
                );

                result.add(
                    recommendation(
                        "Lunghezze medie ondulate",
                        "Movimento morbido sulle lunghezze.",
                        86,
                        "Crea equilibrio tra parte superiore e inferiore del volto."
                    )
                );
            }

            case TRIANGULAR -> {

                result.add(
                    recommendation(
                        "Taglio medio con volume superiore",
                        "Volume controllato nella parte alta e linee più leggere verso la mandibola.",
                        89,
                        "Bilancia una mandibola visivamente più ampia."
                    )
                );
            }

            case DIAMOND -> {

                result.add(
                    recommendation(
                        "Bob morbido con movimento",
                        "Taglio che valorizza gli zigomi mantenendo equilibrio.",
                        92,
                        "Gli zigomi rappresentano spesso il punto dominante del viso diamante.",
                        "Le linee morbide armonizzano fronte e mandibola."
                    )
                );
            }
        }

        /*
         * Adattamento alla texture naturale.
         */
        if (
            hair != null &&
            hair.getHairType() == HairType.WAVY
        ) {

            result.add(
                recommendation(
                    "Scalatura per capelli mossi",
                    "Scalatura progettata per valorizzare il movimento naturale.",
                    88,
                    "La texture mossa beneficia di una distribuzione controllata dei volumi."
                )
            );
        }

        if (
            hair != null &&
            hair.getHairType() == HairType.CURLY
        ) {

            result.add(
                recommendation(
                    "Taglio strutturato per ricci",
                    "Forma costruita rispettando volume e pattern naturale.",
                    90,
                    "La struttura riccia richiede una gestione specifica dei volumi."
                )
            );
        }

        return result;
    }

    /**
     * Suggerimenti frangia.
     */
    private List<RecommendationItemDto>
            buildFringes(
                FaceProfile face) {

        List<RecommendationItemDto> result =
            new ArrayList<>();

        if (
            face.getForeheadHeight() ==
            Level.HIGH
        ) {

            result.add(
                recommendation(
                    "Curtain bangs",
                    "Frangia aperta e morbida.",
                    92,
                    "Può riequilibrare visivamente una fronte alta.",
                    "Mantiene movimento e versatilità."
                )
            );

            result.add(
                recommendation(
                    "Frangia morbida piena",
                    "Frangia non eccessivamente geometrica.",
                    84,
                    "Riduce visivamente l'altezza percepita della fronte."
                )
            );
        }

        if (
            face.getFaceShape() ==
                FaceShape.ROUND
        ) {

            result.add(
                recommendation(
                    "Ciuffo laterale lungo",
                    "Ciuffo diagonale leggero.",
                    86,
                    "Introduce linee diagonali e verticali.",
                    "Evita di aumentare la larghezza centrale del volto."
                )
            );
        }

        if (
            face.getFaceShape() ==
                FaceShape.OBLONG
        ) {

            result.add(
                recommendation(
                    "Curtain bangs ampie",
                    "Frangia aperta che introduce movimento orizzontale.",
                    93,
                    "Aiuta a bilanciare un volto allungato."
                )
            );
        }

        return result;
    }

    /**
     * Suggerimenti colore basati
     * principalmente sull'armocromia.
     */
    private List<RecommendationItemDto>
            buildColors(
                ColorAnalysis analysis) {

        List<RecommendationItemDto> result =
            new ArrayList<>();

        ColorSubSeason subSeason =
            analysis.getSubSeason();

        if (subSeason != null) {

            switch (subSeason) {

                case DEEP_WINTER -> {

                    result.add(
                        recommendation(
                            "Espresso freddo",
                            "Castano molto profondo con direzione fredda.",
                            95,
                            "Compatibile con una palette Inverno Profondo.",
                            "Mantiene profondità e contrasto."
                        )
                    );

                    result.add(
                        recommendation(
                            "Chocolate fondente freddo",
                            "Cioccolato profondo senza eccesso di riflessi dorati.",
                            92,
                            "Mantiene profondità e ricchezza cromatica."
                        )
                    );

                    result.add(
                        recommendation(
                            "Cherry cola freddo",
                            "Rosso-vino profondo con componente fredda.",
                            88,
                            "Valorizza palette profonde e contrastate."
                        )
                    );
                }

                case COOL_WINTER -> {

                    result.add(
                        recommendation(
                            "Castano cenere intenso",
                            "Castano freddo pulito.",
                            94,
                            "Rispetta la dominante fredda della palette."
                        )
                    );

                    result.add(
                        recommendation(
                            "Borgogna freddo",
                            "Rosso-viola intenso.",
                            90,
                            "Mantiene una direzione cromatica fredda."
                        )
                    );
                }

                case BRIGHT_WINTER -> {

                    result.add(
                        recommendation(
                            "Nero brillante",
                            "Colore profondo e ad alto contrasto.",
                            91,
                            "Supporta il contrasto tipico dell'Inverno Brillante."
                        )
                    );

                    result.add(
                        recommendation(
                            "Rosso rubino freddo",
                            "Rosso intenso e brillante.",
                            89,
                            "La saturazione elevata è compatibile con palette brillanti."
                        )
                    );
                }

                case WARM_AUTUMN -> {

                    result.add(
                        recommendation(
                            "Chocolate caldo",
                            "Castano ricco con riflessi caldi.",
                            94,
                            "Armonico con una dominante calda."
                        )
                    );

                    result.add(
                        recommendation(
                            "Rame naturale",
                            "Rame caldo e profondo.",
                            90,
                            "Valorizza tonalità autunnali calde."
                        )
                    );
                }

                case DEEP_AUTUMN -> {

                    result.add(
                        recommendation(
                            "Castano cioccolato profondo",
                            "Castano ricco e caldo-scuro.",
                            94,
                            "Mantiene profondità e calore."
                        )
                    );

                    result.add(
                        recommendation(
                            "Moka caldo",
                            "Castano profondo leggermente caldo.",
                            89,
                            "Compatibile con palette profonde."
                        )
                    );
                }

                case SOFT_AUTUMN -> {

                    result.add(
                        recommendation(
                            "Moka morbido",
                            "Castano medio smorzato.",
                            92,
                            "La saturazione controllata rispetta una palette soft."
                        )
                    );
                }

                case LIGHT_SPRING -> {

                    result.add(
                        recommendation(
                            "Biondo beige caldo chiaro",
                            "Biondo luminoso e leggermente caldo.",
                            92,
                            "Mantiene leggerezza e luminosità."
                        )
                    );
                }

                case WARM_SPRING -> {

                    result.add(
                        recommendation(
                            "Biondo dorato caldo",
                            "Biondo caldo e luminoso.",
                            94,
                            "Valorizza una dominante calda e brillante."
                        )
                    );

                    result.add(
                        recommendation(
                            "Rame luminoso",
                            "Rame chiaro e vivace.",
                            90,
                            "Compatibile con palette Primavera calda."
                        )
                    );
                }

                case BRIGHT_SPRING -> {

                    result.add(
                        recommendation(
                            "Biondo miele brillante",
                            "Biondo caldo con elevata luminosità.",
                            91,
                            "Supporta contrasto e brillantezza."
                        )
                    );
                }

                case LIGHT_SUMMER -> {

                    result.add(
                        recommendation(
                            "Biondo beige freddo",
                            "Biondo chiaro delicato e freddo.",
                            93,
                            "Mantiene leggerezza e temperatura fredda."
                        )
                    );
                }

                case COOL_SUMMER -> {

                    result.add(
                        recommendation(
                            "Biondo cenere",
                            "Biondo freddo senza eccesso di calore.",
                            94,
                            "Compatibile con una palette Estate fredda."
                        )
                    );

                    result.add(
                        recommendation(
                            "Castano cenere chiaro",
                            "Castano freddo e morbido.",
                            89,
                            "Mantiene una saturazione controllata."
                        )
                    );
                }

                case SOFT_SUMMER -> {

                    result.add(
                        recommendation(
                            "Mushroom brown",
                            "Castano-beige freddo e smorzato.",
                            95,
                            "Rispetta morbidezza e bassa saturazione."
                        )
                    );
                }
            }
        } else if (
            analysis.getSeason() != null
        ) {

            addGenericSeasonColors(
                result,
                analysis.getSeason()
            );
        }

        return result;
    }

    /**
     * Suggerimenti generici quando
     * non è stata impostata la sottostagione.
     */
    private void addGenericSeasonColors(
            List<RecommendationItemDto> result,
            ColorSeason season) {

        switch (season) {

            case SPRING ->
                result.add(
                    recommendation(
                        "Tonalità calde e luminose",
                        "Beige caldo, miele, rame luminoso e dorati controllati.",
                        80,
                        "Indicazione basata sulla macro stagione Primavera."
                    )
                );

            case SUMMER ->
                result.add(
                    recommendation(
                        "Tonalità fredde e morbide",
                        "Cenere, beige freddo e castani desaturati.",
                        80,
                        "Indicazione basata sulla macro stagione Estate."
                    )
                );

            case AUTUMN ->
                result.add(
                    recommendation(
                        "Tonalità calde e profonde",
                        "Chocolate, moka caldo, rame e castani ricchi.",
                        80,
                        "Indicazione basata sulla macro stagione Autunno."
                    )
                );

            case WINTER ->
                result.add(
                    recommendation(
                        "Tonalità fredde e contrastate",
                        "Espresso, nero, borgogna e castani freddi.",
                        80,
                        "Indicazione basata sulla macro stagione Inverno."
                    )
                );
        }
    }

    /**
     * Styling.
     */
    private List<RecommendationItemDto>
            buildStyling(
                FaceProfile face,
                HairProfile hair) {

        List<RecommendationItemDto> result =
            new ArrayList<>();

        if (
            face != null &&
            face.getFaceShape() ==
                FaceShape.OBLONG
        ) {

            result.add(
                recommendation(
                    "Volume laterale controllato",
                    "Concentrare parte del volume sui lati evitando eccessivo sviluppo verticale.",
                    90,
                    "Aiuta a riequilibrare un volto allungato."
                )
            );
        }

        if (
            face != null &&
            face.getFaceShape() ==
                FaceShape.ROUND
        ) {

            result.add(
                recommendation(
                    "Volume verticale leggero",
                    "Evitare eccessivo volume all'altezza delle guance.",
                    87,
                    "Può creare maggiore slancio visivo."
                )
            );
        }

        if (
            hair != null &&
            hair.getHairType() ==
                HairType.WAVY
        ) {

            result.add(
                recommendation(
                    "Styling naturale mosso",
                    "Valorizzare il movimento naturale senza irrigidire eccessivamente la forma.",
                    92,
                    "Rispetta la texture naturale del capello."
                )
            );
        }

        if (
            hair != null &&
            hair.getHairType() ==
                HairType.CURLY
        ) {

            result.add(
                recommendation(
                    "Definizione del riccio",
                    "Styling orientato alla definizione mantenendo volume e movimento.",
                    94,
                    "Valorizza la struttura riccia naturale."
                )
            );
        }

        return result;
    }

    /**
     * Costruisce gli avvisi tecnici.
     */
    private List<String>
            buildTechnicalWarnings(
                HairProfile hair) {

        List<String> warnings =
            new ArrayList<>();

        if (
            hair.getPorosity() ==
                PhysicalValue.HIGH
        ) {

            warnings.add(
                "Porosità elevata: valutare assorbimento irregolare del colore e controllare attentamente lunghezze e punte."
            );
        }

        /**
         * Evitiamo di dipendere direttamente
         * dal tipo Enum HairCondition nel Service.
         *
         * Leggiamo il nome del valore,
         * mantenendo comunque il dato tipizzato
         * nella Entity.
         */
        if (
            hair.getHairCondition() != null
        ) {

            String condition =
                hair.getHairCondition()
                    .name();

            if (
                condition.equals("DAMAGED")
            ) {

                warnings.add(
                    "Capello danneggiato: evitare procedure aggressive senza una valutazione tecnica approfondita."
                );
            }

            if (
                condition.equals(
                    "CHEMICALLY_TREATED"
                )
            ) {

                warnings.add(
                    "Capello trattato chimicamente: considerare storico e compatibilità prima di nuove trasformazioni."
                );
            }
        }

        if (
            hair.getChemicalHistory() != null
        ) {

            boolean previousLightening =
                hair.getChemicalHistory()
                    .stream()
                    .anyMatch(
                        value -> {
                            String text =
                                value.toUpperCase();

                            return
                                text.contains("SCHIAR") ||
                                text.contains("DECOLOR") ||
                                text.contains("BLEACH");
                        }
                    );

            if (previousLightening) {

                warnings.add(
                    "Sono presenti schiariture/decolorazioni precedenti: valutare test della ciocca prima di ulteriori schiariture."
                );
            }
        }

        if (
            hair.getContraindications() != null &&
            !hair.getContraindications()
                .isEmpty()
        ) {

            warnings.add(
                "Il profilo contiene controindicazioni tecniche: verificarle prima di formulare il servizio."
            );
        }

        return warnings;
    }

    /**
     * Factory per creare rapidamente
     * un RecommendationItemDto.
     */
    private RecommendationItemDto recommendation(
            String title,
            String description,
            Integer score,
            String... reasons) {

        RecommendationItemDto item =
            new RecommendationItemDto();

        item.setTitle(title);

        item.setDescription(
            description
        );

        item.setCompatibilityScore(
            Math.max(
                0,
                Math.min(
                    100,
                    score
                )
            )
        );

        item.setReasons(
            new ArrayList<>(
                List.of(reasons)
            )
        );

        return item;
    }
}