package com.generation.hairlab.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.hairlab.model.SalonProduct;

/**
 * Repository dedicato ai SalonProduct, cioè i servizi o prodotti
 * presenti nel listino del salone.
 *
 * I metodi aggiuntivi permettono di filtrare il catalogo per stato,
 * categoria e nome senza scrivere manualmente query SQL.
 */
public interface SalonProductRepository extends JpaRepository<SalonProduct, Integer> {

    Optional<SalonProduct> findByNameIgnoreCase(String name);

    List<SalonProduct> findByActiveTrue();

    List<SalonProduct> findByProductCategory_Id(Integer productCategoryId);

    List<SalonProduct> findByProductCategory_IdAndActiveTrue(Integer productCategoryId);

    List<SalonProduct> findByNameContainingIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}