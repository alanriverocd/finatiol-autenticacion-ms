package com.finatiol.autenticacion.service;

import java.util.Collections;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.finatiol.autenticacion.client.UsuarioClient;
import com.finatiol.autenticacion.constants.ErrorMessages;
import com.finatiol.autenticacion.dto.AuthResponse;
import com.finatiol.autenticacion.dto.LoginRequest;
import com.finatiol.autenticacion.dto.RefreshTokenRequest;
import com.finatiol.autenticacion.dto.UsuarioClientDTO;
import com.finatiol.autenticacion.entity.RefreshTokenEntity;
import com.finatiol.autenticacion.exception.PasswordIncorrectoException;
import com.finatiol.autenticacion.exception.UsuarioNoEncontradoException;
import com.finatiol.autenticacion.security.JwtService;
import com.finatiol.autenticacion.security.UserDetailsImpl;

@Service
public class AuthService {

    private final UsuarioClient usuarioClient;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenService refreshTokenService;

    public AuthService(
            UsuarioClient usuarioClient,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            RefreshTokenService refreshTokenService) {

        this.usuarioClient = usuarioClient;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    public AuthResponse login(
            LoginRequest request) {

        UsuarioClientDTO usuario;
        try {
            usuario = usuarioClient
                    .findByUsernameForAuth(
                            request.getUsername());
        } catch (Exception e) {
            throw new UsuarioNoEncontradoException(
                    ErrorMessages.USUARIO_NO_ENCONTRADO);
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                usuario.getPassword())) {

            throw new PasswordIncorrectoException(
                    ErrorMessages.PASSWORD_INCORRECTO);
        }

        List<String> permisos = usuario.getPermisos() != null
                ? usuario.getPermisos()
                : Collections.emptyList();

        UserDetailsImpl userDetails = new UserDetailsImpl(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getActivo(),
                usuario.getRoles() != null
                        ? usuario.getRoles()
                        : Collections.emptyList(),
                permisos
        );

        String accessToken =
                jwtService.generateToken(userDetails);

        RefreshTokenEntity refreshToken =
                refreshTokenService
                        .crearRefreshToken(
                                usuario.getUsername());

        return new AuthResponse(
                accessToken,
                refreshToken.getToken()
        );
    }

    public AuthResponse refreshToken(
            RefreshTokenRequest request) {

        RefreshTokenEntity refreshToken =
                refreshTokenService
                        .validarRefreshToken(
                                request.getRefreshToken());

        String accessToken =
                jwtService.generateAccessToken(
                        refreshToken.getUsername());

        return new AuthResponse(
                accessToken,
                refreshToken.getToken()
        );
    }

    public void logout(
            RefreshTokenRequest request) {

        refreshTokenService
                .logout(request.getRefreshToken());
    }
}

