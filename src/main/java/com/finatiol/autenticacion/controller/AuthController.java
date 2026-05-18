package com.finatiol.autenticacion.controller;

import org.springframework.web.bind.annotation.*;

import com.finatiol.autenticacion.dto.LoginRequest;
import com.finatiol.autenticacion.dto.LoginResponse;
import com.finatiol.autenticacion.service.AuthService;

@RestController

@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(
            AuthService authService) {

        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        String token =
                authService.login(request);

        return new LoginResponse(token);
    }
}