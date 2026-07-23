package com.generation.hairlab.enums;

/**
 * Classificazione tecnica preliminare
 * della fattibilità di un obiettivo colore.
 *
 * Non rappresenta una diagnosi medica
 * né sostituisce la valutazione professionale.
 *
 * Serve a guidare il futuro Smart Formula Engine
 * verso il corretto percorso tecnico.
 */
public enum ColorDiagnosisFeasibility {

    /**
     * Obiettivo vicino alla base:
     * deposito, tonalizzazione o modifica lieve.
     */
    DIRECT_OR_LOW_COMPLEXITY,

    /**
     * Schiaritura contenuta che può richiedere
     * una strategia controllata.
     */
    CONTROLLED_LIFT,

    /**
     * Differenza importante di altezza di tono:
     * probabile fase di schiaritura preliminare.
     */
    PRELIGHTENING_LIKELY,

    /**
     * Scurimento importante:
     * probabile necessità di ricostruzione
     * dei pigmenti mancanti / pre-pigmentazione.
     */
    DARKENING_FILL_LIKELY,

    /**
     * I dati tecnici richiedono
     * una valutazione professionale prima
     * di proporre automaticamente una formula.
     */
    PROFESSIONAL_REVIEW_REQUIRED
}
