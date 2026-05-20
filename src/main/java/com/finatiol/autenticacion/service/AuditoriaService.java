package com.finatiol.autenticacion.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.finatiol.autenticacion.entity.AuditoriaEntity;

import com.finatiol.autenticacion.repository.AuditoriaRepository;

@Service
public class AuditoriaService {

    private final AuditoriaRepository
            auditoriaRepository;

    public AuditoriaService(
            AuditoriaRepository auditoriaRepository) {

        this.auditoriaRepository =
                auditoriaRepository;
    }

    public void registrar(

            String usuario,

            String metodo,

            String endpoint,

            String accion) {

        AuditoriaEntity auditoria =
                new AuditoriaEntity();

        auditoria.setUsuario(
                usuario);

        auditoria.setMetodo(
                metodo);

        auditoria.setEndpoint(
                endpoint);

        auditoria.setAccion(
                accion);

        auditoria.setFecha(
                LocalDateTime.now());

        auditoriaRepository.save(
                auditoria);
    }
}