package com.finatiol.autenticacion.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;

import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;

import jakarta.persistence.Table;

@Entity
@Table(name = "auditoria")
public class AuditoriaEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario;

    private String metodo;

    private String endpoint;

    @Column(length = 1000)
    private String accion;

    private LocalDateTime fecha;

    public AuditoriaEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(
            Long id) {

        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(
            String usuario) {

        this.usuario = usuario;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(
            String metodo) {

        this.metodo = metodo;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(
            String endpoint) {

        this.endpoint = endpoint;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(
            String accion) {

        this.accion = accion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(
            LocalDateTime fecha) {

        this.fecha = fecha;
    }
}