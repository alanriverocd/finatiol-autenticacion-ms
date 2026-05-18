package com.finatiol.autenticacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.finatiol.autenticacion.entity.RefreshTokenEntity;

@Repository
public interface RefreshTokenRepository
        extends JpaRepository<
                RefreshTokenEntity,
                Long> {

    Optional<RefreshTokenEntity>
    findByToken(String token);
}