package com.generation.hairlab.enums;

/**
 * Area o tecnica principale di applicazione
 * di una formula colore.
 *
 * È un dato fondamentale per la stima della grammatura:
 * una ricrescita richiede quantità molto diverse
 * rispetto a una testa completa.
 */
public enum ColorApplicationType {

    /** Applicazione sulla sola ricrescita. */
    ROOT_REGROWTH,

    /** Applicazione completa su radici, lunghezze e punte. */
    FULL_HEAD,

    /** Applicazione concentrata su lunghezze e punte. */
    LENGTHS_AND_ENDS,

    /** Tonalizzazione / gloss. */
    TONING,

    /** Applicazione parziale o localizzata. */
    PARTIAL,

    /** Tecniche di schiaritura come highlights/balayage. */
    HIGHLIGHTS
}
