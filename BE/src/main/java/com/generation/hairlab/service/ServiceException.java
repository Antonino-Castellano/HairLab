package com.generation.hairlab.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Eccezione applicativa utilizzata dal layer Service.
 *
 * Permette di distinguere gli errori dovuti alle regole dell'applicazione
 * dagli errori tecnici del framework o del database.
 *
 * I Controller potranno intercettare questa eccezione e trasformarla
 * in una risposta HTTP comprensibile dal frontend.
 */
public class ServiceException extends Exception {

    /**
     * Costruisce una ServiceException con un messaggio descrittivo.
     *
     * @param message descrizione dell'errore
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Costruisce una mappa semplice utilizzabile come body di una
     * ResponseEntity nei Controller.
     *
     * @param message descrizione generale dell'operazione fallita
     * @return mappa contenente messaggio generale e dettaglio dell'errore
     */
    public Map<String, String> toMap(String message) {
        Map<String, String> result = new HashMap<>();
        result.put("message", message);
        result.put("error", getMessage());
        return result;
    }
}
