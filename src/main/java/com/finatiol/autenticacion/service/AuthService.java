package com.finatiol.autenticacion.service;

import org.springframework.stereotype.Service;

import com.finatiol.autenticacion.dto.LoginRequest;
import com.finatiol.autenticacion.entity.UsuarioEntity;
import com.finatiol.autenticacion.repository.UsuarioRepository;
import com.finatiol.autenticacion.security.JwtService;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            JwtService jwtService) {

        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    public String login(LoginRequest request) {

        UsuarioEntity usuario =
                usuarioRepository
                        .findByUsername(
                                request.getUsername())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Usuario no encontrado"));

        if (!usuario.getPassword()
                .equals(request.getPassword())) {

            throw new RuntimeException(
                    "Password incorrecto");
        }

        return jwtService.generateToken(
                usuario.getUsername());
    }
}