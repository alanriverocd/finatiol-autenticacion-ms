package com.finatiol.autenticacion.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "finatiolsecretkeyfinatiolsecretkey052026";

    private static final long EXPIRATION =
            1000 * 60 * 60;

    private SecretKey getSignKey() {

        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes());
    }

    public String generateToken(String username) {

        return Jwts.builder()

                .subject(username)

                .issuedAt(new Date())

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + EXPIRATION))

                .signWith(
                        getSignKey(),
                        SignatureAlgorithm.HS256)

                .compact();
    }

    public String extractUsername(String token) {

        return extractClaims(token)
                .getSubject();
    }

    public boolean isTokenValid(
            String token,
            String username) {

        String extractedUsername =
                extractUsername(token);

        return extractedUsername.equals(username)
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(
            String token) {

        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }

    private Claims extractClaims(
            String token) {

        return Jwts.parser()

                .verifyWith(getSignKey())

                .build()

                .parseSignedClaims(token)

                .getPayload();
    }
}