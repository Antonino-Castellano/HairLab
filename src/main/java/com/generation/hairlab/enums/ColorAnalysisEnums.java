package com.generation.hairlab.enums;

/**
 * Contiene gli Enum utilizzati per l'analisi cromatica
 * e l'armocromia della cliente.
 */
public final class ColorAnalysisEnums {

    /**
     * Costruttore privato.
     */
    private ColorAnalysisEnums() {
    }

    /**
     * Profondità visiva del tono della pelle.
     */
    public enum SkinTone {
        VERY_LIGHT,
        LIGHT,
        LIGHT_MEDIUM,
        MEDIUM,
        MEDIUM_DARK,
        DARK,
        VERY_DARK
    }

    /**
     * Sottotono cromatico della pelle.
     */
    public enum Undertone {
        COOL,
        NEUTRAL_COOL,
        NEUTRAL,
        NEUTRAL_WARM,
        WARM,
        OLIVE
    }

    /**
     * Macro stagione armocromatica.
     */
    public enum ColorSeason {
        SPRING,
        SUMMER,
        AUTUMN,
        WINTER
    }

    /**
     * Dodici sottostagioni armocromatiche.
     */
    public enum ColorSubSeason {
        LIGHT_SPRING,
        WARM_SPRING,
        BRIGHT_SPRING,

        LIGHT_SUMMER,
        COOL_SUMMER,
        SOFT_SUMMER,

        SOFT_AUTUMN,
        WARM_AUTUMN,
        DEEP_AUTUMN,

        BRIGHT_WINTER,
        COOL_WINTER,
        DEEP_WINTER
    }

    /**
     * Profondità cromatica complessiva.
     */
    public enum ColorValue {
        LIGHT,
        MEDIUM,
        DEEP
    }

    /**
     * Livello di contrasto naturale.
     */
    public enum ContrastLevel {
        LOW,
        MEDIUM,
        HIGH
    }

    /**
     * Intensità o saturazione cromatica.
     */
    public enum Chroma {
        SOFT,
        MEDIUM,
        BRIGHT
    }

    /**
     * Metalli generalmente armonici
     * con la palette della cliente.
     */
    public enum MetalType {
        SILVER,
        WHITE_GOLD,
        YELLOW_GOLD,
        ROSE_GOLD,
        BRONZE,
        COPPER
    }
}