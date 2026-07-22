package com.generation.hairlab.service;

import org.springframework.http.HttpStatus;

/**
 * Eccezione applicativa del layer Service.
 * Contiene il messaggio e lo stato HTTP corretto da restituire al client.
 */
public class ServiceException extends Exception {

    private final HttpStatus status;

    /** Compatibilità con i Service non ancora migrati agli status specifici. */
    public ServiceException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public ServiceException(
            String message,
            HttpStatus status) {
        super(message);
        this.status = status != null
                ? status
                : HttpStatus.BAD_REQUEST;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
