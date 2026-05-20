package com.finatiol.autenticacion.controller;

import java.util.List;

import org.springframework.http.HttpStatus;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.finatiol.autenticacion.constants.SuccessCodes;

import com.finatiol.autenticacion.constants.SuccessMessages;

import com.finatiol.autenticacion.dto.ApiResponse;

import com.finatiol.autenticacion.dto.ModuloRequestDTO;

import com.finatiol.autenticacion.dto.ModuloResponseDTO;

import com.finatiol.autenticacion.service.ModuloService;

@RestController
@RequestMapping("/modulos")
public class ModuloController {

    private final ModuloService
            moduloService;

    public ModuloController(
            ModuloService moduloService) {

        this.moduloService =
                moduloService;
    }

    @PostMapping
    @PreAuthorize(
            "hasAuthority('USUARIO_EDITAR')")
    public ApiResponse<ModuloResponseDTO>
    crearModulo(

            @RequestBody
            ModuloRequestDTO request) {

        ModuloResponseDTO response =
                moduloService
                        .crearModulo(request);

        return new ApiResponse<>(

                SuccessCodes.MODULO_CREADO,

                SuccessMessages.MODULO_CREADO,

                HttpStatus.CREATED.value(),

                response
        );
    }

    @GetMapping
    @PreAuthorize(
            "hasAuthority('USUARIO_VER')")
    public ApiResponse<List<ModuloResponseDTO>>
    listarModulos() {

        List<ModuloResponseDTO>
                modulos =

                moduloService
                        .listarModulos();

        return new ApiResponse<>(

                SuccessCodes.MODULOS_OBTENIDOS,

                SuccessMessages.MODULOS_OBTENIDOS,

                HttpStatus.OK.value(),

                modulos
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize(
            "hasAuthority('USUARIO_VER')")
    public ApiResponse<ModuloResponseDTO>
    obtenerModuloPorId(

            @PathVariable
            Long id) {

        ModuloResponseDTO response =
                moduloService
                        .obtenerModuloPorId(id);

        return new ApiResponse<>(

                SuccessCodes.MODULO_OBTENIDO,

                SuccessMessages.MODULO_OBTENIDO,

                HttpStatus.OK.value(),

                response
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(
            "hasAuthority('USUARIO_ELIMINAR')")
    public ApiResponse<String>
    eliminarModulo(

            @PathVariable
            Long id) {

        moduloService
                .eliminarModulo(id);

        return new ApiResponse<>(

                SuccessCodes.MODULO_ELIMINADO,

                SuccessMessages.MODULO_ELIMINADO,

                HttpStatus.OK.value(),

                SuccessMessages.MODULO_ELIMINADO
        );
    }
}