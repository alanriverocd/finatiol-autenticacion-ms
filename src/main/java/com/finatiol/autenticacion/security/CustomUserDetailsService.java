package com.finatiol.autenticacion.security;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.finatiol.autenticacion.client.UsuarioClient;
import com.finatiol.autenticacion.dto.UsuarioClientDTO;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UsuarioClient usuarioClient;

    public CustomUserDetailsService(
            UsuarioClient usuarioClient) {

        this.usuarioClient = usuarioClient;
    }

    @Override
    public UserDetails loadUserByUsername(
            String username)
            throws UsernameNotFoundException {

        UsuarioClientDTO dto;
        try {
            dto = usuarioClient
                    .findByUsernameForAuth(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException(
                    "Usuario no encontrado: " + username);
        }

        List<String> permisos = dto.getPermisos() != null
                ? dto.getPermisos()
                : List.of();

        return new UserDetailsImpl(
                dto.getUsername(),
                dto.getPassword(),
                dto.getActivo(),
                dto.getRoles(),
                permisos,
                dto.getTenantId()
        );
    }
}
