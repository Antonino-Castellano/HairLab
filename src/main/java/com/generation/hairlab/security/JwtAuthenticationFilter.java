package com.generation.hairlab.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public final PasswordManager passwordManager;


    @Override
    protected void doFilterInternal
    (
            HttpServletRequest request,         // request, con i suoi header, col suo body, come avete visto in TS
            HttpServletResponse response,       // la response, che posso trascurare, viene prodotta dopo
            FilterChain filterChain             // l'elenco degli altri filtri
    )
    throws ServletException, IOException {
        
        // recupero il valore dell'header Authorization, che è quello che ho impostato
        // nella chiamata http
        String authHeader = request.getHeader("Authorization");
        //'Bearer '+token

        System.out.println("Ricevuto "+authHeader);


        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Rimuove "Bearer " ed estrae solo il token
            
            System.out.println("Ricevuto token"+token);

            try {
                // Recuperiamo l'intero payload (claims) del token con una sola operazione di parsing
                var claims = Jwts.parser()
                        .verifyWith(Keys.hmacShaKeyFor(passwordManager.getServerPassword().getBytes(StandardCharsets.UTF_8)))
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                String username = claims.getSubject();
                String role = claims.get("ROLE", String.class);
                
                System.out.println("Verificato "+username);


                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    
                    // Creiamo la lista di autorità. Se il ruolo esiste, gli iniettiamo davanti il prefisso "ROLE_"
                    // In questo modo .hasRole("ADMIN") cercherà esattamente "ROLE_ADMIN" e darà il via libera.
                    List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

                    // Passiamo le autorità reali al token di autenticazione anziché la lista vuota
                    UsernamePasswordAuthenticationToken authToken = 
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    
                    System.out.println("Creo autenticazione");

                    // Salva l'utente nel contesto con i suoi ruoli attivi
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    System.out.println(SecurityContextHolder.getContext().getAuthentication());
                    
                }
            } catch (Exception e) {
                // Token scaduto, manomesso o non valido: non facciamo nulla, la richiesta fallirà l'autorizzazione
                e.printStackTrace();
            }
        }

        // Passa la richiesta al filtro successivo della catena
        filterChain.doFilter(request, response);
    }
}