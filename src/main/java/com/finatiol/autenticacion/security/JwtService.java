package com.finatiol.autenticacion.security;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

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

    public String generateToken(
            UserDetailsImpl usuario) {

        List<String> roles =
                usuario.getRoles() == null
                        ? Collections.emptyList()
                        : usuario.getRoles();

        List<String> permisos =
                usuario.getPermisos() == null
                        ? Collections.emptyList()
                        : usuario.getPermisos();

        return Jwts.builder()
                .subject(usuario.getUsername())
                .claim("roles", roles)
                .claim("permisos", permisos)
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

    public String extractUsername(
            String token) {

        return extractClaims(token)
                .getSubject();
    }

    public boolean isTokenValid(
            String token,
            String username) {

        String extractedUsername =
                extractUsername(token);

        return extractedUsername
                .equals(username)

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

    public List<String> extractPermisos(
            String token) {

        Claims claims =
                extractClaims(token);

        return claims.get(
                "permisos",
                List.class);
    }

    public String generateAccessToken(
            String username) {

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
}