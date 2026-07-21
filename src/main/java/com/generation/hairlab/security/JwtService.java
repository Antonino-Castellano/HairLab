package com.generation.hairlab.security;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

import com.generation.hairlab.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final PasswordManager passwordManager;

    // vita di un token
    private final long EXPIRATION_TIME = 86400000; 

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("ROLE", user.getRole().toString());


        return  Jwts
                .builder()
                .claims(claims)
                .subject(user.getEmail()) // Il proprietario del token                       // sub
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))             // exp => tempo di scadenza
                .signWith(Keys.hmacShaKeyFor(passwordManager.getServerPassword().getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

}