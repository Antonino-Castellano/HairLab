package com.generation.hairlab.enums;

/**
 * Strategia tecnica generale proposta
 * dal motore diagnostico Color Lab.
 *
 * Nel Blocco 4 non vengono ancora scelti
 * ingredienti o grammature automatiche.
 */
public enum ColorDiagnosisStrategy {

    /** Deposito / tonalizzazione. */
    DEPOSIT_OR_TONE,

    /** Schiaritura contenuta con deposito colore. */
    CONTROLLED_LIFT_AND_DEPOSIT,

    /** Schiaritura preliminare seguita da tonalizzazione. */
    PRELIGHTEN_THEN_TONE,

    /** Scurimento diretto / deposito. */
    DARKEN_AND_DEPOSIT,

    /** Pre-pigmentazione seguita da scurimento. */
    PREPIGMENT_THEN_DARKEN,

    /** Valutazione professionale prima di procedere. */
    PROFESSIONAL_ASSESSMENT
}
