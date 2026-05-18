package com.finatiol.autenticacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finatiol.autenticacion.entity.PermisoEntity;

@Repository
public interface PermisoRepository extends JpaRepository<PermisoEntity, Long> {
	

}
