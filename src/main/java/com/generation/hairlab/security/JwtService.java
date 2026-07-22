package com.generation.hairlab.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.generation.hairlab.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Service centralizzato per la gestione
 * dei JSON Web Token di HairLab.
 *
 * Gestisce:
 *
 * - generazione del token;
 * - firma crittografica;
 * - verifica della firma;
 * - lettura del payload;
 * - estrazione dell'utente;
 * - estrazione del ruolo;
 * - scadenza del token.
 */
@Service
public class JwtService {

    /**
     * Gestore della chiave segreta.
     */
    private final PasswordManager passwordManager;

    /**
     * Durata del token espressa
     * in millisecondi.
     *
     * Default:
     *
     * 86400000 ms
     * =
     * 24 ore
     */
    private final long expirationTime;

    /**
     * Costruttore utilizzato da Spring.
     *
     * La durata viene letta dalla configurazione.
     */
    public JwtService(
        PasswordManager passwordManager,

        @Value(
            "${hairlab.jwt.expiration-ms:86400000}"
        )
        long expirationTime
    ) {

        this.passwordManager =
            passwordManager;

        this.expirationTime =
            expirationTime;
    }

    /**
     * Genera un JWT per l'utente
     * che ha effettuato correttamente il login.
     *
     * Nel token salviamo:
     *
     * sub
     * =
     * email dell'utente
     *
     * ROLE
     * =
     * ruolo applicativo
     *
     * iat
     * =
     * data di creazione
     *
     * exp
     * =
     * data di scadenza
     */
    public String generateToken(
        User user
    ) {

        Map<String, Object> claims =
            new HashMap<>();

        claims.put(
            "ROLE",
            user.getRole().name()
        );

        long currentTime =
            System.currentTimeMillis();

        return Jwts
            .builder()

            .claims(
                claims
            )

            /*
             * Il subject identifica
             * il proprietario del token.
             *
             * Utilizziamo l'email.
             */
            .subject(
                user.getEmail()
            )

            /*
             * Momento di creazione.
             */
            .issuedAt(
                new Date(
                    currentTime
                )
            )

            /*
             * Momento di scadenza.
             */
            .expiration(
                new Date(
                    currentTime +
                    expirationTime
                )
            )

            /*
             * Firma crittografica.
             */
            .signWith(
                getSigningKey()
            )

            /*
             * Genera la stringa finale:
             *
             * HEADER.PAYLOAD.SIGNATURE
             */
            .compact();
    }

    /**
     * Verifica il JWT e restituisce
     * il relativo payload.
     *
     * Questa operazione:
     *
     * - verifica la firma;
     * - verifica la struttura;
     * - verifica la scadenza.
     *
     * Se il token non è valido,
     * JJWT genera un'eccezione.
     */
    public Claims parseToken(
        String token
    ) {

        return Jwts
            .parser()

            /*
             * Indichiamo la stessa chiave
             * utilizzata durante la firma.
             */
            .verifyWith(
                getSigningKey()
            )

            .build()

            /*
             * Verifica e decodifica
             * il JWT firmato.
             */
            .parseSignedClaims(
                token
            )

            /*
             * Restituisce il payload.
             */
            .getPayload();
    }

    /**
     * Recupera l'email dell'utente
     * contenuta nel subject.
     */
    public String extractUsername(
        String token
    ) {

        return parseToken(
            token
        ).getSubject();
    }

    /**
     * Recupera il ruolo
     * memorizzato nel JWT.
     */
    public String extractRole(
        String token
    ) {

        return parseToken(
            token
        ).get(
            "ROLE",
            String.class
        );
    }

    /**
     * Costruisce la SecretKey HMAC
     * utilizzata da JJWT.
     */
    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
            passwordManager
                .getServerPassword()
                .getBytes(
                    StandardCharsets.UTF_8
                )
        );
    }
}