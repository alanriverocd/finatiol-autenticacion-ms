package com.finatiol.autenticacion.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "permisos")
public class PermisoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "modulo_id")
    private ModuloEntity modulo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(
            String nombre) {

        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(
            String descripcion) {

        this.descripcion = descripcion;
    }

    public ModuloEntity getModulo() {
        return modulo;
    }

    public void setModulo(
            ModuloEntity modulo) {

        this.modulo = modulo;
    }
}