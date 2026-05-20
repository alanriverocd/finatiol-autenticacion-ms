package com.finatiol.autenticacion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.finatiol.autenticacion.dto.ModuloResponseDTO;
import com.finatiol.autenticacion.dto.PermisoRequestDTO;
import com.finatiol.autenticacion.dto.PermisoResponseDTO;

import com.finatiol.autenticacion.entity.ModuloEntity;
import com.finatiol.autenticacion.entity.PermisoEntity;

import com.finatiol.autenticacion.exception.UsuarioNoEncontradoException;

import com.finatiol.autenticacion.repository.ModuloRepository;
import com.finatiol.autenticacion.repository.PermisoRepository;

@Service
public class PermisoService {

    private final PermisoRepository
            permisoRepository;

    private final ModuloRepository
            moduloRepository;

    public PermisoService(

            PermisoRepository permisoRepository,

            ModuloRepository moduloRepository) {

        this.permisoRepository =
                permisoRepository;

        this.moduloRepository =
                moduloRepository;
    }

    public PermisoResponseDTO
    crearPermiso(
            PermisoRequestDTO request) {

        ModuloEntity modulo =
                moduloRepository
                        .findById(
                                request.getModuloId())

                        .orElseThrow(() ->

                                new UsuarioNoEncontradoException(
                                        "Modulo no encontrado"));

        PermisoEntity permiso =
                new PermisoEntity();

        permiso.setNombre(
                request.getNombre());

        permiso.setDescripcion(
                request.getDescripcion());

        permiso.setModulo(
                modulo);

        PermisoEntity permisoGuardado =
                permisoRepository.save(
                        permiso);

        return mapToDTO(
                permisoGuardado);
    }

    public List<PermisoResponseDTO>
    listarPermisos() {

        return permisoRepository
                .findAll()

                .stream()

                .map(this::mapToDTO)

                .toList();
    }

    public PermisoResponseDTO
    obtenerPermisoPorId(
            Long id) {

        PermisoEntity permiso =
                permisoRepository
                        .findById(id)

                        .orElseThrow(() ->

                                new UsuarioNoEncontradoException(
                                        "Permiso no encontrado"));

        return mapToDTO(
                permiso);
    }

    public void eliminarPermiso(
            Long id) {

        PermisoEntity permiso =
                permisoRepository
                        .findById(id)

                        .orElseThrow(() ->

                                new UsuarioNoEncontradoException(
                                        "Permiso no encontrado"));

        permisoRepository.delete(
                permiso);
    }

    private PermisoResponseDTO
    mapToDTO(
            PermisoEntity permiso) {

        return new PermisoResponseDTO(

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
        );
    }
}