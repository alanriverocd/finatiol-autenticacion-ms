package com.finatiol.autenticacion.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "usuarios")
public class UsuarioEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true)
    private String username;

    private String email;

    private String password;

    private Boolean activo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_rol",

            joinColumns = @JoinColumn(
                    name = "usuario_id"),

            inverseJoinColumns = @JoinColumn(
                    name = "rol_id")
    )
    private Set<RolEntity> roles =
            new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    private List<RefreshTokenEntity> refreshTokens;

    public UsuarioEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(
            String username) {

        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(
            String email) {

        this.email = email;
    }

    @Override
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

    public Set<RolEntity> getRoles() {
        return roles;
    }

    public void setRoles(
            Set<RolEntity> roles) {

        this.roles = roles;
    }

    public List<RefreshTokenEntity>
    getRefreshTokens() {

        return refreshTokens;
    }

    public void setRefreshTokens(
            List<RefreshTokenEntity>
                    refreshTokens) {

        this.refreshTokens =
                refreshTokens;
    }

    @Override
    public Collection<? extends GrantedAuthority>
    getAuthorities() {

        return roles.stream()

                .flatMap(rol ->

                        rol.getPermisos()
                                .stream())

                .map(permiso ->

                        new SimpleGrantedAuthority(
                                permiso.getNombre()))

                .toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activo != null && activo;
    }
}