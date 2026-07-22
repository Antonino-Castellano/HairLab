package com.generation.hairlab.enums;

/**
 * Contiene gli Enum utilizzati dal profilo morfologico del viso.
 *
 * Gli Enum vengono salvati nel database come STRING tramite
 * @Enumerated(EnumType.STRING).
 */
public final class FaceProfileEnums {

    /**
     * Costruttore privato.
     *
     * La classe contiene solamente Enum
     * e non deve essere istanziata.
     */
    private FaceProfileEnums() {
    }

    /**
     * Forma generale del viso.
     */
    public enum FaceShape {
        OVAL,
        ROUND,
        SQUARE,
        RECTANGULAR,
        OBLONG,
        HEART,
        INVERTED_TRIANGLE,
        TRIANGULAR,
        DIAMOND
    }

    /**
     * Livello generico basso / medio / alto.
     *
     * Utilizzato per:
     * - altezza fronte;
     * - prominenza zigomi;
     * - definizione mandibola.
     */
    public enum Level {
        LOW,
        MEDIUM,
        HIGH
    }

    /**
     * Larghezza.
     */
    public enum Width {
        NARROW,
        MEDIUM,
        WIDE
    }

    /**
     * Lunghezza.
     */
    public enum Length {
        SHORT,
        MEDIUM,
        LONG
    }

    /**
     * Dimensione.
     */
    public enum Size {
        SMALL,
        MEDIUM,
        LARGE
    }

    /**
     * Spessore.
     */
    public enum Thickness {
        THIN,
        MEDIUM,
        THICK
    }

    /**
     * Forma dell'attaccatura dei capelli.
     */
    public enum HairlineShape {
        STRAIGHT,
        ROUNDED,
        WIDOW_PEAK,
        M_SHAPED,
        IRREGULAR
    }

    /**
     * Forma principale degli occhi.
     */
    public enum EyeShape {
        ALMOND,
        ROUND,
        MONOLID,
        HOODED,
        DEEP_SET,
        PROTRUDING
    }

    /**
     * Orientamento degli occhi.
     */
    public enum EyeOrientation {
        UPTURNED,
        NEUTRAL,
        DOWNTURNED
    }

    /**
     * Distanza tra gli occhi.
     */
    public enum EyeSpacing {
        CLOSE_SET,
        PROPORTIONATE,
        WIDE_SET
    }

    /**
     * Colore degli occhi.
     */
    public enum EyeColor {
        VERY_DARK_BROWN,
        DARK_BROWN,
        BROWN,
        LIGHT_BROWN,
        HAZEL,
        AMBER,
        GREEN,
        BLUE_GREEN,
        BLUE,
        GREY,
        GREY_BLUE,
        OTHER
    }

    /**
     * Forma delle sopracciglia.
     */
    public enum EyebrowShape {
        STRAIGHT,
        SOFT_ARCH,
        HIGH_ARCH,
        ROUNDED,
        ANGULAR
    }

    /**
     * Profilo laterale del naso.
     */
    public enum NoseProfile {
        STRAIGHT,
        CONVEX,
        CONCAVE,
        AQUILINE,
        UPTURNED
    }

    /**
     * Forma della punta del naso.
     */
    public enum NoseTip {
        FINE,
        ROUNDED,
        WIDE,
        UPTURNED,
        DOWNWARD
    }

    /**
     * Forma della linea mandibolare.
     */
    public enum JawShape {
        ROUNDED,
        STRAIGHT,
        ANGULAR
    }

    /**
     * Forma del mento.
     */
    public enum ChinShape {
        ROUNDED,
        SQUARE,
        POINTED,
        FLAT
    }

    /**
     * Proiezione del mento vista di profilo.
     */
    public enum ChinProjection {
        RETRUSIVE,
        BALANCED,
        PROMINENT
    }

    /**
     * Volume generale delle labbra.
     */
    public enum LipFullness {
        THIN,
        MEDIUM,
        FULL
    }

    /**
     * Rapporto tra labbro superiore e inferiore.
     */
    public enum LipBalance {
        BALANCED,
        UPPER_FULLER,
        LOWER_FULLER
    }

    /**
     * Forma generale della bocca/labbra.
     */
    public enum LipShape {
        BALANCED,
        HEART_SHAPED,
        DEFINED_CUPIDS_BOW,
        ROUNDED,
        WIDE,
        UPTURNED,
        DOWNTURNED
    }
}