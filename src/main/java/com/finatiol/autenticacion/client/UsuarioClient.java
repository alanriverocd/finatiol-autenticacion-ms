package com.finatiol.autenticacion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.finatiol.autenticacion.dto.UsuarioClientDTO;
import com.finatiol.autenticacion.dto.UsuarioResponseDTO;

@FeignClient(name = "finatiol-usuarios-ms")
public interface UsuarioClient {

    @GetMapping("/usuarios/auth/{username}")
    UsuarioClientDTO findByUsernameForAuth(
            @PathVariable("username") String username);

    @GetMapping("/usuarios/username/{username}")
    UsuarioResponseDTO obtenerPorUsername(
            @PathVariable("username") String username);
}
