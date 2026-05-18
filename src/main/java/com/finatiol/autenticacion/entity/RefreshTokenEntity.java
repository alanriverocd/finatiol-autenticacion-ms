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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

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

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(
            UsuarioEntity usuario) {

        this.usuario = usuario;
    }
}