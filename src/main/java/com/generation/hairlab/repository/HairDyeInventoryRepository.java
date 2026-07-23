package com.generation.hairlab.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.hairlab.model.HairDyeInventory;

/**
 * Repository della giacenza Color Lab.
 */
public interface HairDyeInventoryRepository
    extends JpaRepository<HairDyeInventory, Integer> {

    Optional<HairDyeInventory>
        findByHairDye_Id(
            Integer hairDyeId
        );

    boolean existsByHairDye_Id(
        Integer hairDyeId
    );
}
