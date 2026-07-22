package com.generation.hairlab.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

/**
 * DTO utilizzato per trasferire
 * i dati di un cliente HairLab.
 *
 * Le annotazioni Bean Validation
 * proteggono le API anche quando vengono
 * chiamate direttamente tramite Postman
 * o altri client esterni.
 */
@Data
public class CustomerDto {

    /**
     * Identificativo generato dal database.
     *
     * Durante l'inserimento può essere null.
     */
    private Integer id;

    /**
     * Nome del cliente.
     */
    @NotBlank(
        message = "Il nome è obbligatorio"
    )
    @Size(
        min = 2,
        max = 60,
        message =
            "Il nome deve contenere "
            + "da 2 a 60 caratteri"
    )
    private String firstName;

    /**
     * Cognome del cliente.
     */
    @NotBlank(
        message = "Il cognome è obbligatorio"
    )
    @Size(
        min = 2,
        max = 60,
        message =
            "Il cognome deve contenere "
            + "da 2 a 60 caratteri"
    )
    private String lastName;

    /**
     * Numero di telefono.
     *
     * Sono ammessi:
     *
     * - cifre;
     * - spazi;
     * - simbolo +;
     * - parentesi;
     * - trattini.
     */
    @NotBlank(
        message =
            "Il numero di telefono è obbligatorio"
    )
    @Size(
        min = 6,
        max = 25,
        message =
            "Il numero di telefono deve contenere "
            + "da 6 a 25 caratteri"
    )
    @Pattern(
        regexp = "^[0-9+()\\-\\s]+$",
        message =
            "Il numero di telefono "
            + "contiene caratteri non validi"
    )
    private String phoneNumber;

    /**
     * Email del cliente.
     */
    @NotBlank(
        message = "L'email è obbligatoria"
    )
    @Email(
        message = "L'email non è valida"
    )
    @Size(
        max = 120,
        message =
            "L'email non può superare "
            + "120 caratteri"
    )
    private String email;

    /**
     * Data di nascita.
     *
     * Deve essere una data passata.
     */
    @NotNull(
        message =
            "La data di nascita è obbligatoria"
    )
    @Past(
        message =
            "La data di nascita deve essere "
            + "precedente alla data odierna"
    )
    private LocalDate dob;

    /**
     * Stato del cliente.
     *
     * Non utilizziamo Boolean perché
     * il frontend invia sempre true o false.
     */
    private boolean active;

    /**
     * Foto del cliente in formato:
     *
     * - data URL Base64;
     * - percorso statico;
     * - null.
     *
     * Impostiamo un limite prudenziale.
     */
    @Size(
        max = 2_000_000,
        message =
            "L'immagine del profilo è troppo grande"
    )
    private String profileImage;

    /**
     * Data di creazione gestita dal backend.
     */
    private LocalDateTime createdAt;

    /**
     * Data dell'ultima modifica
     * gestita dal backend.
     */
    private LocalDateTime updatedAt;

    /**
     * Identificativi degli appuntamenti.
     *
     * Non vengono gestiti direttamente
     * durante inserimento e modifica cliente.
     */
    private List<Integer> appointmentIds;
}