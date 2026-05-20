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

import com.finatiol.autenticacion.dto.RolRequestDTO;

import com.finatiol.autenticacion.dto.RolResponseDTO;

import com.finatiol.autenticacion.service.RolService;

@RestController
@RequestMapping("/roles")
public class RolController {

    private final RolService
            rolService;

    public RolController(
            RolService rolService) {

        this.rolService =
                rolService;
    }

    @PostMapping
    @PreAuthorize(
            "hasAuthority('USUARIO_EDITAR')")
    public ApiResponse<RolResponseDTO>
    crearRol(

            @RequestBody
            RolRequestDTO request) {

        RolResponseDTO response =
                rolService
                        .crearRol(request);

        return new ApiResponse<>(

                SuccessCodes.ROL_CREADO,

                SuccessMessages.ROL_CREADO,

                HttpStatus.CREATED.value(),

                response
        );
    }

    @GetMapping
    @PreAuthorize(
            "hasAuthority('USUARIO_VER')")
    public ApiResponse<List<RolResponseDTO>>
    listarRoles() {

        List<RolResponseDTO>
                roles =

                rolService
                        .listarRoles();

        return new ApiResponse<>(

                SuccessCodes.ROLES_OBTENIDOS,

                SuccessMessages.ROLES_OBTENIDOS,

                HttpStatus.OK.value(),

                roles
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize(
            "hasAuthority('USUARIO_VER')")
    public ApiResponse<RolResponseDTO>
    obtenerRolPorId(

            @PathVariable
            Long id) {

        RolResponseDTO response =
                rolService
                        .obtenerRolPorId(id);

        return new ApiResponse<>(

                SuccessCodes.ROL_OBTENIDO,

                SuccessMessages.ROL_OBTENIDO,

                HttpStatus.OK.value(),

                response
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(
            "hasAuthority('USUARIO_ELIMINAR')")
    public ApiResponse<String>
    eliminarRol(

            @PathVariable
            Long id) {

        rolService
                .eliminarRol(id);

        return new ApiResponse<>(

                SuccessCodes.ROL_ELIMINADO,

                SuccessMessages.ROL_ELIMINADO,

                HttpStatus.OK.value(),

                SuccessMessages.ROL_ELIMINADO
        );
    }
}