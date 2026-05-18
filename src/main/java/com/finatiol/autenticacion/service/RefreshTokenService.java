package com.finatiol.autenticacion.service;

import java.time.LocalDateTime;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.finatiol.autenticacion.entity.RefreshTokenEntity;

import com.finatiol.autenticacion.entity.UsuarioEntity;

import com.finatiol.autenticacion.exception.TokenExpiradoException;

import com.finatiol.autenticacion.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {

    private static final long
            REFRESH_TOKEN_DURATION_DAYS = 7;

    private final RefreshTokenRepository
            refreshTokenRepository;

    public RefreshTokenService(
            RefreshTokenRepository
                    refreshTokenRepository) {

        this.refreshTokenRepository =
                refreshTokenRepository;
    }

    public RefreshTokenEntity
    crearRefreshToken(
            UsuarioEntity usuario) {

        RefreshTokenEntity refreshToken =
                new RefreshTokenEntity();

        refreshToken.setToken(
                UUID.randomUUID().toString());

        refreshToken.setExpiracion(
                LocalDateTime.now()
                        .plusDays(
                                REFRESH_TOKEN_DURATION_DAYS));

        refreshToken.setRevocado(false);

        refreshToken.setUsuario(usuario);

        return refreshTokenRepository
                .save(refreshToken);
    }

    public RefreshTokenEntity
    validarRefreshToken(
            String token) {

        RefreshTokenEntity refreshToken =
                refreshTokenRepository

                        .findByToken(token)

                        .orElseThrow(() ->

                                new TokenExpiradoException(
                                        "Refresh token no encontrado"
                                )
                        );

        if (refreshToken.getRevocado()) {

            throw new TokenExpiradoException(
                    "Refresh token revocado");
        }

        if (refreshToken.getExpiracion()
                .isBefore(
                        LocalDateTime.now())) {

            throw new TokenExpiradoException(
                    "Refresh token expirado");
        }

        return refreshToken;
    }
    
    public void logout(
            String token) {

        RefreshTokenEntity refreshToken =
                refreshTokenRepository

                        .findByToken(token)

                        .orElseThrow(() ->

                                new TokenExpiradoException(
                                        "Refresh token no encontrado"
                                )
                        );

        refreshToken.setRevocado(true);

        refreshTokenRepository
                .save(refreshToken);
    }
}