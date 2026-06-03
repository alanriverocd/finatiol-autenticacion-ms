package com.finatiol.autenticacion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finatiol.autenticacion.dto.ApiResponse;
import com.finatiol.autenticacion.dto.AuditoriaRequestDTO;
import com.finatiol.autenticacion.service.AuditoriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auditoria")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    public AuditoriaController(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @PostMapping
    public ApiResponse<String> registrar(@Valid @RequestBody AuditoriaRequestDTO request) {

        auditoriaService.registrar(
                request.getUsuario(),
                request.getMetodo(),
                request.getEndpoint(),
                request.getAccion());

        return new ApiResponse<>(
                "AUDIT-001",
                "Evento de auditoria registrado",
                HttpStatus.CREATED.value(),
                "OK");
    }
}
