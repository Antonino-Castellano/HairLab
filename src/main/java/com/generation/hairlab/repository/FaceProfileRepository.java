package com.generation.hairlab.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.hairlab.model.FaceProfile;

/**
 * Repository JPA per FaceProfile.
 */
public interface FaceProfileRepository
        extends JpaRepository<FaceProfile, Integer> {

    /**
     * Recupera il profilo tramite cliente.
     */
    Optional<FaceProfile> findByCustomerId(
        Integer customerId
    );

    /**
     * Verifica se il cliente possiede già
     * un profilo del viso.
     */
    boolean existsByCustomerId(
        Integer customerId
    );
}