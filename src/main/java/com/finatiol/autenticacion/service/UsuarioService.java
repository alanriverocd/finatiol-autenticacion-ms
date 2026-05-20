package com.finatiol.autenticacion.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import com.finatiol.autenticacion.dto.UsuarioRequestDTO;

import com.finatiol.autenticacion.dto.UsuarioResponseDTO;

import com.finatiol.autenticacion.entity.RolEntity;
import com.finatiol.autenticacion.entity.UsuarioEntity;

import com.finatiol.autenticacion.exception.UsuarioNoEncontradoException;

import com.finatiol.autenticacion.repository.RolRepository;
import com.finatiol.autenticacion.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository
            usuarioRepository;

    private final PasswordEncoder
            passwordEncoder;

    private final RolRepository
            rolRepository;

    public UsuarioService(

            UsuarioRepository usuarioRepository,

            PasswordEncoder passwordEncoder,

            RolRepository rolRepository) {

        this.usuarioRepository =
                usuarioRepository;

        this.passwordEncoder =
                passwordEncoder;

        this.rolRepository =
                rolRepository;
    }

    public UsuarioResponseDTO
    crearUsuario(
            UsuarioRequestDTO request) {

        UsuarioEntity usuario =
                new UsuarioEntity();

        usuario.setNombre(
                request.getNombre());

        usuario.setUsername(
                request.getUsername());

        usuario.setEmail(
                request.getEmail());

        usuario.setPassword(

                passwordEncoder.encode(
                        request.getPassword()));

        usuario.setActivo(true);

        UsuarioEntity usuarioGuardado =
                usuarioRepository.save(
                        usuario);

        return new UsuarioResponseDTO(

                usuarioGuardado.getId(),

                usuarioGuardado.getNombre(),

                usuarioGuardado.getUsername(),

                usuarioGuardado.getEmail(),

                usuarioGuardado.getActivo(),

                List.of()
        );
    }

    public List<UsuarioResponseDTO>
    listarUsuarios() {

        return usuarioRepository
                .findAll()

                .stream()

                .map(usuario ->

                        new UsuarioResponseDTO(

                                usuario.getId(),

                                usuario.getNombre(),

                                usuario.getUsername(),

                                usuario.getEmail(),

                                usuario.getActivo(),

                                List.of()
                        )
                )

                .toList();
    }

    public UsuarioResponseDTO
    obtenerUsuarioPorId(
            Long id) {

        UsuarioEntity usuario =
                usuarioRepository
                        .findById(id)

                        .orElseThrow(() ->

                                new UsuarioNoEncontradoException(
                                        "Usuario no encontrado"));

        return new UsuarioResponseDTO(

                usuario.getId(),

                usuario.getNombre(),

                usuario.getUsername(),

                usuario.getEmail(),

                usuario.getActivo(),

                List.of()
        );
    }

    public UsuarioResponseDTO
    actualizarUsuario(

            Long id,

            UsuarioRequestDTO request) {

        UsuarioEntity usuario =
                usuarioRepository
                        .findById(id)

                        .orElseThrow(() ->

                                new UsuarioNoEncontradoException(
                                        "Usuario no encontrado"));

        usuario.setNombre(
                request.getNombre());

        usuario.setUsername(
                request.getUsername());

        usuario.setEmail(
                request.getEmail());

        if (request.getPassword() != null
                && !request.getPassword()
                        .isBlank()) {

            usuario.setPassword(

                    passwordEncoder.encode(
                            request.getPassword()));
        }

        UsuarioEntity usuarioActualizado =
                usuarioRepository
                        .save(usuario);

        return new UsuarioResponseDTO(

                usuarioActualizado.getId(),

                usuarioActualizado.getNombre(),

                usuarioActualizado.getUsername(),

                usuarioActualizado.getEmail(),

                usuarioActualizado.getActivo(),

                List.of()
        );
    }

    public void eliminarUsuario(
            Long id) {

        UsuarioEntity usuario =
                usuarioRepository
                        .findById(id)

                        .orElseThrow(() ->

                                new UsuarioNoEncontradoException(
                                        "Usuario no encontrado"));

        usuarioRepository.delete(
                usuario);
    }

    public void asignarRol(

            Long usuarioId,

            Long rolId) {

        UsuarioEntity usuario =
                usuarioRepository
                        .findById(usuarioId)

                        .orElseThrow(() ->

                                new UsuarioNoEncontradoException(
                                        "Usuario no encontrado"));

        RolEntity rol =
                rolRepository
                        .findById(rolId)

                        .orElseThrow(() ->

                                new UsuarioNoEncontradoException(
                                        "Rol no encontrado"));

        if (usuario.getRoles() == null) {

            usuario.setRoles(
                    new HashSet<>());
        }

        usuario.getRoles()
                .add(rol);

        usuarioRepository.save(
                usuario);
    }

    public void removerRol(

            Long usuarioId,

            Long rolId) {

        UsuarioEntity usuario =
                usuarioRepository
                        .findById(usuarioId)

                        .orElseThrow(() ->

                                new UsuarioNoEncontradoException(
                                        "Usuario no encontrado"));

        usuario.getRoles()

                .removeIf(rol ->

                        rol.getId()
                                .equals(rolId));

        usuarioRepository.save(
                usuario);
    }
}