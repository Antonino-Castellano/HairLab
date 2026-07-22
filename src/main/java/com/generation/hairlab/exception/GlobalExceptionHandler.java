package com.generation.hairlab.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.generation.hairlab.service.ServiceException;

import jakarta.servlet.http.HttpServletRequest;

/** Gestore centralizzato degli errori REST di HairLab. */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiErrorResponse> handleServiceException(
            ServiceException exception,
            HttpServletRequest request) {

        HttpStatus status = exception.getStatus();

        return ResponseEntity
                .status(status)
                .body(buildResponse(
                        status,
                        exception.getMessage(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiErrorResponse response = buildResponse(
                status,
                "I dati inviati non sono validi",
                request.getRequestURI());

        for (FieldError fieldError :
                exception.getBindingResult().getFieldErrors()) {
            response.addFieldError(
                    fieldError.getField(),
                    fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException exception,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(buildResponse(
                        status,
                        "Il parametro '" + exception.getName()
                                + "' non contiene un valore valido",
                        request.getRequestURI()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingParameter(
            MissingServletRequestParameterException exception,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(buildResponse(
                        status,
                        "Parametro obbligatorio mancante: "
                                + exception.getParameterName(),
                        request.getRequestURI()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleUnreadableBody(
            HttpMessageNotReadableException exception,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
                .body(buildResponse(
                        status,
                        "Il corpo della richiesta contiene dati non validi o non leggibili",
                        request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(
            Exception exception,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(status)
                .body(buildResponse(
                        status,
                        "Si è verificato un errore interno",
                        request.getRequestURI()));
    }

    private ApiErrorResponse buildResponse(
            HttpStatus status,
            String message,
            String path) {

        return new ApiErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path);
    }
}
