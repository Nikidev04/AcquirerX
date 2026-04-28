package com.acquirerx.backend.iam.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private final Key key;
    private final long expirySeconds;

    public JwtUtil(String secret, long expirySeconds) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirySeconds = expirySeconds;
    }

    public String createToken(String userId, String role) {
        Instant now = Instant.now();

        return Jwts.builder()
                .subject(userId)
                .claims(Map.of("role", role))
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expirySeconds)))
                .signWith(key)
                .compact();
    }

    public Jws<Claims> validate(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseSignedClaims(token);
        } catch (JwtException e) {
            throw new IllegalArgumentException("Invalid or expired JWT token");
        }
    }
}