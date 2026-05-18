package com.finatiol.autenticacion.controller;

import org.springframework.http.HttpStatus;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.finatiol.autenticacion.constants.SuccessCodes;

import com.finatiol.autenticacion.constants.SuccessMessages;

import com.finatiol.autenticacion.dto.ApiResponse;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @GetMapping("/perfil")

    @PreAuthorize(
            "hasAuthority('VER_PERFIL')")

    public ApiResponse<String> perfil(
            Authentication authentication) {

        return new ApiResponse<>(

                SuccessCodes.PERFIL_OBTENIDO,

                SuccessMessages.PERFIL_OBTENIDO,

                HttpStatus.OK.value(),

                "Usuario autenticado: "
                        + authentication.getName()
        );
    }
}