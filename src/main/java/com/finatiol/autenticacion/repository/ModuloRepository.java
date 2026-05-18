package com.finatiol.autenticacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finatiol.autenticacion.entity.ModuloEntity;

@Repository
public interface ModuloRepository extends JpaRepository<ModuloEntity, Long> {

}
