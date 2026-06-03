package com.finatiol.autenticacion.dto;

import jakarta.validation.constraints.NotBlank;

public class AuditoriaRequestDTO {

    @NotBlank
    private String usuario;

    @NotBlank
    private String metodo;

    @NotBlank
    private String endpoint;

    @NotBlank
    private String accion;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
}
