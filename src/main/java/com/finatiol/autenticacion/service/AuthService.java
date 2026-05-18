package com.finatiol.autenticacion.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import com.finatiol.autenticacion.constants.ErrorMessages;
import com.finatiol.autenticacion.dto.AuthResponse;
import com.finatiol.autenticacion.dto.LoginRequest;
import com.finatiol.autenticacion.dto.RefreshTokenRequest;
import com.finatiol.autenticacion.entity.RefreshTokenEntity;
import com.finatiol.autenticacion.entity.UsuarioEntity;

import com.finatiol.autenticacion.exception.PasswordIncorrectoException;

import com.finatiol.autenticacion.exception.UsuarioNoEncontradoException;

import com.finatiol.autenticacion.repository.UsuarioRepository;

import com.finatiol.autenticacion.security.JwtService;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;
    
    private final RefreshTokenService refreshTokenService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,

            RefreshTokenService
                    refreshTokenService) {

        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;

        this.refreshTokenService =
                refreshTokenService;
    }

    public AuthResponse login(
            LoginRequest request) {

        UsuarioEntity usuario =
                usuarioRepository
                        .findByUsername(
                                request.getUsername())
                        .orElseThrow(() ->

                                new UsuarioNoEncontradoException(
                                        ErrorMessages.USUARIO_NO_ENCONTRADO
                                )
                        );

        if (!passwordEncoder.matches(
                request.getPassword(),
                usuario.getPassword())) {

            throw new PasswordIncorrectoException(
                    ErrorMessages.PASSWORD_INCORRECTO
            );
        }

        String accessToken =
                jwtService.generateToken(
                        usuario);

        RefreshTokenEntity refreshToken =
                refreshTokenService
                        .crearRefreshToken(
                                usuario);

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

                        refreshToken
                                .getUsuario()
                                .getUsername()
                );

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