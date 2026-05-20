package com.finatiol.autenticacion.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import com.finatiol.autenticacion.entity.UsuarioEntity;
import com.finatiol.autenticacion.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UsuarioRepository
            usuarioRepository;

    public CustomUserDetailsService(
            UsuarioRepository
                    usuarioRepository) {

        this.usuarioRepository =
                usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(
            String username)

            throws UsernameNotFoundException {

        UsuarioEntity usuario =
                usuarioRepository
                        .findByUsername(username)
                        .orElseThrow(() ->

                                new UsernameNotFoundException(
                                        "Usuario no encontrado"));

        return usuario;
    }
}