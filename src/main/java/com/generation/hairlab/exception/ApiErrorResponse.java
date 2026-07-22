package com.generation.hairlab.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Formato standard utilizzato da HairLab
 * per restituire gli errori REST.
 *
 * Tutti gli errori avranno una struttura
 * prevedibile e uniforme.
 *
 * Esempio:
 *
 * {
 *   "timestamp": "2026-07-22T14:30:00",
 *   "status": 404,
 *   "error": "Not Found",
 *   "message": "Cliente non trovato con id: 99",
 *   "path": "/hairlab/api/customer/99"
 * }
 */
@JsonInclude(
    JsonInclude.Include.NON_EMPTY
)
public class ApiErrorResponse {

    /**
     * Data e ora dell'errore.
     */
    private LocalDateTime timestamp;

    /**
     * Codice HTTP numerico.
     */
    private int status;

    /**
     * Nome dello stato HTTP.
     */
    private String error;

    /**
     * Descrizione leggibile dell'errore.
     */
    private String message;

    /**
     * Endpoint che ha generato l'errore.
     */
    private String path;

    /**
     * Errori specifici dei singoli campi.
     *
     * Viene utilizzato soprattutto
     * da Bean Validation.
     *
     * Esempio:
     *
     * {
     *   "email": "Email non valida",
     *   "firstName": "Il nome è obbligatorio"
     * }
     */
    private Map<String, String> fieldErrors =
        new LinkedHashMap<>();

    public ApiErrorResponse() {
    }

    public ApiErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
    ) {

        this.timestamp =
            timestamp;

        this.status =
            status;

        this.error =
            error;

        this.message =
            message;

        this.path =
            path;
    }

    public LocalDateTime getTimestamp() {

        return timestamp;
    }

    public void setTimestamp(
        LocalDateTime timestamp
    ) {

        this.timestamp =
            timestamp;
    }

    public int getStatus() {

        return status;
    }

    public void setStatus(
        int status
    ) {

        this.status =
            status;
    }

    public String getError() {

        return error;
    }

    public void setError(
        String error
    ) {

        this.error =
            error;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(
        String message
    ) {

        this.message =
            message;
    }

    public String getPath() {

        return path;
    }

    public void setPath(
        String path
    ) {

        this.path =
            path;
    }

    public Map<String, String>
        getFieldErrors() {

        return fieldErrors;
    }

    public void setFieldErrors(
        Map<String, String> fieldErrors
    ) {

        this.fieldErrors =
            fieldErrors;
    }

    /**
     * Aggiunge un errore associato
     * a uno specifico campo del DTO.
     */
    public void addFieldError(
        String field,
        String message
    ) {

        fieldErrors.put(
            field,
            message
        );
    }
}