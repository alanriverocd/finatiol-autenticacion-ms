package com.finatiol.autenticacion.service;

import java.util.List;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.finatiol.autenticacion.dto.ModuloResponseDTO;
import com.finatiol.autenticacion.dto.PermisoResponseDTO;
import com.finatiol.autenticacion.dto.RolRequestDTO;
import com.finatiol.autenticacion.dto.RolResponseDTO;

import com.finatiol.autenticacion.entity.PermisoEntity;
import com.finatiol.autenticacion.entity.RolEntity;

import com.finatiol.autenticacion.exception.UsuarioNoEncontradoException;

import com.finatiol.autenticacion.repository.PermisoRepository;
import com.finatiol.autenticacion.repository.RolRepository;

@Service
public class RolService {

    private final RolRepository
            rolRepository;

    private final PermisoRepository
            permisoRepository;

    public RolService(
            RolRepository rolRepository,

            PermisoRepository permisoRepository) {

        this.rolRepository =
                rolRepository;

        this.permisoRepository =
                permisoRepository;
    }

    public RolResponseDTO
    crearRol(
            RolRequestDTO request) {

        Set<PermisoEntity> permisos =

                Set.copyOf(

                        permisoRepository.findAllById(
                                request.getPermisosIds())
                );

        RolEntity rol =
                new RolEntity();

        rol.setNombre(
                request.getNombre());

        rol.setDescripcion(
                request.getDescripcion());

        rol.setPermisos(
                permisos);

        RolEntity rolGuardado =
                rolRepository.save(rol);

        return mapToDTO(
                rolGuardado);
    }

    public List<RolResponseDTO>
    listarRoles() {

        return rolRepository
                .findAll()

                .stream()

                .map(this::mapToDTO)

                .toList();
    }

    public RolResponseDTO
    obtenerRolPorId(
            Long id) {

        RolEntity rol =
                rolRepository
                        .findById(id)

                        .orElseThrow(() ->

                                new UsuarioNoEncontradoException(
                                        "Rol no encontrado"));

        return mapToDTO(rol);
    }

    public void eliminarRol(
            Long id) {

        RolEntity rol =
                rolRepository
                        .findById(id)

                        .orElseThrow(() ->

                                new UsuarioNoEncontradoException(
                                        "Rol no encontrado"));

        rolRepository.delete(rol);
    }

    private RolResponseDTO
    mapToDTO(
            RolEntity rol) {

        return new RolResponseDTO(

                rol.getId(),

                rol.getNombre(),

                rol.getDescripcion(),

                rol.getPermisos()

                        .stream()

                        .map(permiso ->

                        new PermisoResponseDTO(

                                permiso.getId(),

                                permiso.getNombre(),

                                permiso.getDescripcion(),

                                new ModuloResponseDTO(

                                        permiso.getModulo().getId(),

                                        permiso.getModulo().getNombre(),

                                        permiso.getModulo().getDescripcion(),

                                        permiso.getModulo().getRuta(),

                                        permiso.getModulo().getIcono(),

                                        permiso.getModulo().getActivo()
                                )
                        )
                        )

                        .toList()
        );
    }
}