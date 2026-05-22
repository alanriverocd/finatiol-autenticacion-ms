package com.finatiol.autenticacion.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

    private final String username;

    private final String password;

    private final Boolean activo;

    private final List<String> roles;

    private final List<String> permisos;

    public UserDetailsImpl(
            String username,
            String password,
            Boolean activo,
            List<String> roles,
            List<String> permisos) {

        this.username = username;
        this.password = password;
        this.activo = activo;
        this.roles = roles;
        this.permisos = permisos;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public List<String> getPermisos() {
        return permisos;
    }

    @Override
    public Collection<? extends GrantedAuthority>
    getAuthorities() {

        return permisos.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
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
