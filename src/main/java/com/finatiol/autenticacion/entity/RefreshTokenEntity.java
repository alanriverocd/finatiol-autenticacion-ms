package com.finatiol.autenticacion.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "refresh_tokens")
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 1000)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiracion;

    @Column(nullable = false)
    private Boolean revocado;

    @Column(nullable = false)
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(
            String token) {

        this.token = token;
    }

    public LocalDateTime getExpiracion() {
        return expiracion;
    }

    public void setExpiracion(
            LocalDateTime expiracion) {

        this.expiracion = expiracion;
    }

    public Boolean getRevocado() {
        return revocado;
    }

    public void setRevocado(
            Boolean revocado) {

        this.revocado = revocado;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(
            String username) {

        this.username = username;
    }
}
