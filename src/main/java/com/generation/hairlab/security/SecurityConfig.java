package com.generation.hairlab.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
            .csrf(csrf -> csrf.disable()) // Cruciale disabilitarlo se usi JWT per API REST
            
            // Configura la gestione della sessione come STATELESS (niente cookie di sessione JSESSIONID)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/hairlab/api/auth/isalive" ).permitAll()
                .requestMatchers(HttpMethod.POST, "/hairlab/api/auth/login" ).permitAll()
                .requestMatchers(HttpMethod.POST, "/hairlab/api/users").permitAll()
                .anyRequest().authenticated() 
            );

        // AGGIUNGI QUESTO: Inserisce il filtro JWT prima di UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}