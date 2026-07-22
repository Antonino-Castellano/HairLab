package com.generation.hairlab.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

/**
 * Filtro eseguito una volta
 * per ogni richiesta HTTP.
 *
 * Il suo compito è:
 *
 * 1. leggere l'header Authorization;
 * 2. estrarre il Bearer Token;
 * 3. verificare il JWT;
 * 4. recuperare email e ruolo;
 * 5. creare l'Authentication;
 * 6. salvarla nel SecurityContext.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter
    extends OncePerRequestFilter {

    /**
     * Service centralizzato JWT.
     */
    private final JwtService jwtService;

    /**
     * Metodo eseguito automaticamente
     * da Spring Security per ogni richiesta.
     */
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    )
    throws ServletException,
           IOException {

        /*
         * Recupera:
         *
         * Authorization:
         * Bearer eyJhbGciOi...
         */
        String authHeader =
            request.getHeader(
                "Authorization"
            );

        /*
         * Se non esiste un Bearer Token
         * non autentichiamo nessuno.
         *
         * La richiesta continua comunque:
         *
         * sarà SecurityConfig a decidere
         * se quell'endpoint può essere pubblico
         * oppure richiede autenticazione.
         */
        if (
            authHeader == null ||
            !authHeader.startsWith(
                "Bearer "
            )
        ) {

            filterChain.doFilter(
                request,
                response
            );

            return;
        }

        /*
         * Elimina:
         *
         * "Bearer "
         *
         * e conserva solamente il JWT.
         */
        String token =
            authHeader.substring(7);

        try {

            /*
             * Verifica firma, struttura
             * e scadenza una sola volta.
             */
            Claims claims =
                jwtService.parseToken(
                    token
                );

            /*
             * Il subject contiene l'email.
             */
            String username =
                claims.getSubject();

            /*
             * Recupera il ruolo.
             */
            String role =
                claims.get(
                    "ROLE",
                    String.class
                );

            /*
             * Creiamo l'autenticazione solamente se:
             *
             * - email presente;
             * - ruolo presente;
             * - nessun altro utente già autenticato.
             */
            if (
                username != null &&
                role != null &&
                SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    == null
            ) {

                /*
                 * Spring Security utilizza
                 * convenzionalmente:
                 *
                 * ROLE_ADMIN
                 * ROLE_USER
                 *
                 * mentre nel nostro Enum abbiamo:
                 *
                 * ADMIN
                 * USER
                 */
                List<SimpleGrantedAuthority>
                    authorities =
                        List.of(
                            new SimpleGrantedAuthority(
                                "ROLE_" + role
                            )
                        );

                /*
                 * Crea l'oggetto Authentication
                 * riconosciuto da Spring Security.
                 */
                UsernamePasswordAuthenticationToken
                    authentication =
                        new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities
                        );

                /*
                 * Salva l'utente autenticato
                 * nel contesto della richiesta.
                 */
                SecurityContextHolder
                    .getContext()
                    .setAuthentication(
                        authentication
                    );
            }

        }
        catch (
            JwtException |
            IllegalArgumentException exception
        ) {

            /*
             * Token:
             *
             * - scaduto;
             * - manomesso;
             * - malformato;
             * - firmato con una chiave diversa.
             *
             * Non stampiamo il JWT
             * e non mostriamo stack trace.
             *
             * Semplicemente eliminiamo
             * l'eventuale autenticazione.
             */
            SecurityContextHolder
                .clearContext();
        }

        /*
         * Continua la catena dei filtri.
         *
         * SecurityConfig deciderà successivamente:
         *
         * endpoint pubblico
         * oppure
         * endpoint autenticato.
         */
        filterChain.doFilter(
            request,
            response
        );
    }
}