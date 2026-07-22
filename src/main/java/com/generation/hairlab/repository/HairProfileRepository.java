package com.generation.hairlab.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.hairlab.model.HairProfile;

/**
 * Repository JPA per HairProfile.
 */
public interface HairProfileRepository
        extends JpaRepository<HairProfile, Integer> {

    /**
     * Recupera il profilo capelli
     * appartenente a uno specifico cliente.
     */
    Optional<HairProfile> findByCustomerId(
        Integer customerId
    );

    /**
     * Verifica se il cliente possiede
     * già un HairProfile.
     */
    boolean existsByCustomerId(
        Integer customerId
    );
}