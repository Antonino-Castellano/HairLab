package com.generation.hairlab.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.hairlab.model.ProductCategory;

/**
 * Repository dedicato alle categorie utilizzate per organizzare
 * i servizi e i prodotti del salone.
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    /**
     * Cerca una categoria tramite il nome ignorando maiuscole e minuscole.
     *
     * Il nome è univoco nella Entity ProductCategory.
     *
     * @param name nome della categoria
     * @return Optional contenente la categoria se presente
     */
    Optional<ProductCategory> findByNameIgnoreCase(String name);

    /**
     * Restituisce tutte le categorie attive.
     *
     * @return lista delle categorie attive
     */
    List<ProductCategory> findByActiveTrue();

    /**
     * Verifica l'esistenza di una categoria con lo stesso nome.
     *
     * @param name nome da verificare
     * @return true se esiste già una categoria con quel nome
     */
    boolean existsByNameIgnoreCase(String name);
}
