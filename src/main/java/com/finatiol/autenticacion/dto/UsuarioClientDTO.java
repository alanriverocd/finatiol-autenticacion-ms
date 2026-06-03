package com.finatiol.autenticacion.dto;

import java.util.List;

public class UsuarioClientDTO {

    private Long id;

    private String username;

    private String password;

    private Boolean activo;

    private List<String> roles;

    private List<String> permisos;

    private String tenantId;

    public UsuarioClientDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(
            String username) {

        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(
            String password) {

        this.password = password;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(
            Boolean activo) {

        this.activo = activo;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(
            List<String> roles) {

        this.roles = roles;
    }

    public List<String> getPermisos() {
        return permisos;
    }

    public void setPermisos(
            List<String> permisos) {

        this.permisos = permisos;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
