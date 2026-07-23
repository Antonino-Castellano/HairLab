package com.generation.hairlab.enums;

/**
 * Fascia di lunghezza dei capelli.
 *
 * Viene utilizzata dal futuro motore Color Lab
 * per stimare la quantità di miscela necessaria.
 *
 * Non utilizziamo direttamente i centimetri come unico dato
 * perché, nell'uso professionale quotidiano, una fascia tecnica
 * è più rapida da compilare e più stabile.
 */
public enum HairLength {

    /** Pixie, crop e tagli molto corti. */
    VERY_SHORT,

    /** Capelli corti fino circa alla mandibola. */
    SHORT,

    /** Lunghezze medie, indicativamente fino alle spalle. */
    MEDIUM,

    /** Capelli oltre le spalle. */
    LONG,

    /** Capelli molto lunghi. */
    VERY_LONG
}
