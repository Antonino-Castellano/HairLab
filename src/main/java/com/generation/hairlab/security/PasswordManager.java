package com.generation.hairlab.security;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Gestisce la chiave segreta utilizzata
 * per firmare e verificare i JWT di HairLab.
 *
 * IMPORTANTE:
 *
 * La chiave NON è più scritta direttamente
 * nel codice sorgente.
 *
 * Viene letta dalla proprietà:
 *
 * hairlab.jwt.secret
 *
 * che a sua volta viene valorizzata
 * tramite una variabile d'ambiente.
 */
@Service
public class PasswordManager {

    /**
     * Chiave segreta del server.
     */
    private final String serverPassword;

    /**
     * Spring inietta automaticamente
     * il valore configurato in:
     *
     * hairlab.jwt.secret
     */
    public PasswordManager(
        @Value("${hairlab.jwt.secret}")
        String serverPassword
    ) {

        /*
         * Una chiave HMAC per JWT
         * deve essere sufficientemente lunga.
         *
         * Blocchiamo l'avvio dell'applicazione
         * se viene configurata una chiave
         * troppo corta.
         */
        if (
            serverPassword == null ||
            serverPassword
                .getBytes(StandardCharsets.UTF_8)
                .length < 32
        ) {

            throw new IllegalStateException(
                "HAIRLAB_JWT_SECRET deve contenere "
                + "almeno 32 byte"
            );
        }

        this.serverPassword =
            serverPassword;
    }

    /**
     * Restituisce la chiave segreta.
     *
     * Viene utilizzata solamente
     * dai componenti di sicurezza JWT.
     */
    public String getServerPassword() {

        return serverPassword;
    }
}