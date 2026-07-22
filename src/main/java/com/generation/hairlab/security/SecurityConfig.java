package com.generation.hairlab.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configurazione centrale della sicurezza HairLab.
 *
 * Gestisce:
 *
 * - autenticazione JWT;
 * - autorizzazione degli endpoint;
 * - CORS globale;
 * - sessione stateless;
 * - risposta 401 per utenti non autenticati;
 * - risposta 403 per utenti senza permessi.
 */
@Configuration
public class SecurityConfig {

    /**
     * Filtro che legge e verifica
     * il JWT delle richieste.
     */
    private final JwtAuthenticationFilter
        jwtAuthFilter;

    /**
     * Origin frontend autorizzato.
     *
     * Default sviluppo:
     *
     * http://localhost:4200
     */
    private final String allowedOrigin;

    /**
     * Costruttore utilizzato da Spring.
     */
    public SecurityConfig(
        JwtAuthenticationFilter
            jwtAuthFilter,

        @Value(
            "${hairlab.cors.allowed-origin:"
            + "http://localhost:4200}"
        )
        String allowedOrigin
    ) {

        this.jwtAuthFilter =
            jwtAuthFilter;

        this.allowedOrigin =
            allowedOrigin;
    }

    /**
     * Catena principale dei filtri
     * Spring Security.
     */
    @Bean
    public SecurityFilterChain
        securityFilterChain(
            HttpSecurity http
        )
        throws Exception {

        http

            /*
             * ====================================================
             * CORS
             * ====================================================
             *
             * Utilizza la configurazione globale
             * dichiarata più sotto.
             */
            .cors(
                cors ->
                    cors.configurationSource(
                        corsConfigurationSource()
                    )
            )

            /*
             * ====================================================
             * CSRF
             * ====================================================
             *
             * L'applicazione utilizza:
             *
             * REST API
             * +
             * JWT Bearer Token
             *
             * e non una sessione browser
             * autenticata tramite cookie.
             */
            .csrf(
                AbstractHttpConfigurer::disable
            )

            /*
             * Non utilizziamo form login HTML.
             */
            .formLogin(
                AbstractHttpConfigurer::disable
            )

            /*
             * Non utilizziamo HTTP Basic.
             */
            .httpBasic(
                AbstractHttpConfigurer::disable
            )

            /*
             * Non utilizziamo il logout
             * server-side di Spring.
             *
             * Nel frontend il logout consiste
             * nella rimozione del JWT.
             */
            .logout(
                AbstractHttpConfigurer::disable
            )

            /*
             * ====================================================
             * SESSIONE
             * ====================================================
             *
             * Nessuna HttpSession viene utilizzata
             * per conservare l'autenticazione.
             *
             * Ogni richiesta deve essere
             * autenticata tramite JWT.
             */
            .sessionManagement(
                session ->
                    session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                    )
            )

            /*
             * ====================================================
             * AUTORIZZAZIONE
             * ====================================================
             */
            .authorizeHttpRequests(
                auth -> auth

                    /*
                     * Le richieste OPTIONS vengono utilizzate
                     * dal browser per il preflight CORS.
                     */
                    .requestMatchers(
                        HttpMethod.OPTIONS,
                        "/**"
                    )
                    .permitAll()

                    /*
                     * Health check pubblico.
                     */
                    .requestMatchers(
                        HttpMethod.GET,
                        "/hairlab/api/auth/isalive"
                    )
                    .permitAll()

                    /*
                     * Login pubblico.
                     *
                     * Non possiamo richiedere il JWT
                     * prima che il JWT venga creato.
                     */
                    .requestMatchers(
                        HttpMethod.POST,
                        "/hairlab/api/auth/login"
                    )
                    .permitAll()

                    /*
                     * Registrazione utente.
                     *
                     * Rimane pubblica,
                     * ma nello UserService impediremo
                     * a un utente anonimo
                     * di autoassegnarsi ADMIN.
                     */
                    .requestMatchers(
                        HttpMethod.POST,
                        "/hairlab/api/users"
                    )
                    .permitAll()

                    /*
                     * TUTTO IL RESTO
                     * richiede un JWT valido.
                     */
                    .anyRequest()
                    .authenticated()
            )

            /*
             * ====================================================
             * ERRORI SICUREZZA
             * ====================================================
             */

            /*
             * Nessuna autenticazione valida:
             *
             * HTTP 401 Unauthorized
             */
            .exceptionHandling(
                exception -> exception

                    .authenticationEntryPoint(
                        new HttpStatusEntryPoint(
                            HttpStatus.UNAUTHORIZED
                        )
                    )

                    /*
                     * Utente autenticato
                     * ma senza permessi sufficienti:
                     *
                     * HTTP 403 Forbidden
                     */
                    .accessDeniedHandler(
                        (
                            request,
                            response,
                            accessDeniedException
                        ) ->

                            response.setStatus(
                                HttpStatus
                                    .FORBIDDEN
                                    .value()
                            )
                    )
            );

        /*
         * Il nostro filtro JWT deve essere eseguito
         * prima del filtro standard
         * UsernamePasswordAuthenticationFilter.
         */
        http.addFilterBefore(
            jwtAuthFilter,
            UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }

    /**
     * Configurazione CORS globale.
     *
     * Evita di dover configurare CORS
     * manualmente in ogni Controller.
     */
    @Bean
    public CorsConfigurationSource
        corsConfigurationSource() {

        CorsConfiguration configuration =
            new CorsConfiguration();

        /*
         * Frontend autorizzato.
         */
        configuration.setAllowedOrigins(
            List.of(
                allowedOrigin
            )
        );

        /*
         * Metodi HTTP utilizzati
         * dalle REST API HairLab.
         */
        configuration.setAllowedMethods(
            List.of(
                "GET",
                "POST",
                "PUT",
                "PATCH",
                "DELETE",
                "OPTIONS"
            )
        );

        /*
         * Header che il frontend
         * può inviare.
         */
        configuration.setAllowedHeaders(
            List.of(
                "Authorization",
                "Content-Type",
                "Accept",
                "Origin",
                "X-Requested-With"
            )
        );

        /*
         * HairLab attualmente non usa
         * cookie di autenticazione cross-origin.
         *
         * Il JWT viene inviato manualmente
         * nell'header Authorization.
         */
        configuration.setAllowCredentials(
            false
        );

        /*
         * Il browser può memorizzare
         * il risultato del preflight
         * per un'ora.
         */
        configuration.setMaxAge(
            3600L
        );

        UrlBasedCorsConfigurationSource
            source =
                new UrlBasedCorsConfigurationSource();

        /*
         * La configurazione CORS
         * si applica a tutte le API.
         */
        source.registerCorsConfiguration(
            "/**",
            configuration
        );

        return source;
    }
}