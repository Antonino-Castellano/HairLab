package com.generation.hairlab.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.hairlab.model.ColorAnalysis;

/**
 * Repository JPA per ColorAnalysis.
 */
public interface ColorAnalysisRepository
        extends JpaRepository<ColorAnalysis, Integer> {

    Optional<ColorAnalysis> findByCustomerId(
        Integer customerId
    );

    boolean existsByCustomerId(
        Integer customerId
    );
}