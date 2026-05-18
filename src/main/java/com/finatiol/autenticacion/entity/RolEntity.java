package com.finatiol.autenticacion.entity;

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
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "roles")
@Data
public class RolEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String nombre;
	
	private String descripcion;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "rol_permiso",
		joinColumns = @JoinColumn(name = "rol_id"),
		inverseJoinColumns = @JoinColumn(name = "permiso_id")
	)
	private Set<PermisoEntity> permisos;
	
}
