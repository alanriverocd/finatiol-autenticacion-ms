package com.finatiol.autenticacion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finatiol.common.constants.auth.SuccessCodes;
import com.finatiol.common.constants.auth.SuccessMessages;
import com.finatiol.autenticacion.dto.ApiResponse;
import com.finatiol.autenticacion.dto.AuthResponse;
import com.finatiol.autenticacion.dto.LoginRequest;
import com.finatiol.autenticacion.dto.RefreshTokenRequest;
import com.finatiol.autenticacion.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private final PasswordEncoder passwordEncoder;

    public AuthController(
            AuthService authService,
            PasswordEncoder passwordEncoder) {

        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(
            @RequestBody LoginRequest request) {

        AuthResponse response =
                authService.login(request);

        return new ApiResponse<>(

                SuccessCodes.LOGIN_EXITOSO,

                SuccessMessages.LOGIN_EXITOSO,

                HttpStatus.OK.value(),

                response
        );
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refreshToken(
            @RequestBody
            RefreshTokenRequest request) {

        AuthResponse response =
                authService
                        .refreshToken(request);

        return new ApiResponse<>(

                SuccessCodes.TOKEN_RENOVADO,

                SuccessMessages.TOKEN_RENOVADO,

                HttpStatus.OK.value(),

                response
        );
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(
            @RequestBody
            RefreshTokenRequest request) {

        authService.logout(request);

        return new ApiResponse<>(

                SuccessCodes.LOGOUT_EXITOSO,

                SuccessMessages.LOGOUT_EXITOSO,

                HttpStatus.OK.value(),

                null
        );
    }

}