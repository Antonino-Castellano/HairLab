package com.generation.hairlab.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.hairlab.model.Customer;

/**
 * Repository JPA dedicato ai clienti HairLab.
 *
 * Oltre alle ricerche per email,
 * espone query specifiche per stato:
 *
 * - clienti attivi;
 * - clienti disattivati.
 */
public interface CustomerRepository
        extends JpaRepository<Customer, Integer> {

    /**
     * Cerca un cliente tramite email.
     */
    Optional<Customer> findByEmail(
        String email
    );

    /**
     * Verifica se esiste già un cliente
     * con una determinata email.
     */
    boolean existsByEmail(
        String email
    );

    /**
     * Restituisce solamente
     * i clienti attivi.
     */
    List<Customer> findByActiveTrue();

    /**
     * Restituisce solamente
     * i clienti disattivati.
     */
    List<Customer> findByActiveFalse();
}
