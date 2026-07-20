package com.generation.hairlab.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.hairlab.model.HairProfile;

/**
 * Repository dedicato alla gestione delle schede tecniche HairProfile.
 *
 * La relazione tra HairProfile e Customer è OneToOne: per questo il
 * repository espone metodi che permettono di recuperare direttamente
 * la scheda associata a uno specifico cliente.
 */
public interface HairProfileRepository extends JpaRepository<HairProfile, Integer> {

    /**
     * Cerca la HairProfile associata a un cliente.
     *
     * L'underscore nel nome del metodo indica esplicitamente a Spring Data
     * di attraversare la relazione customer e leggere la proprietà id.
     *
     * @param customerId identificativo del cliente
     * @return Optional contenente la scheda se presente
     */
    Optional<HairProfile> findByCustomer_Id(Integer customerId);

    /**
     * Verifica se il cliente possiede già una HairProfile.
     *
     * È utile per rispettare la relazione OneToOne ed evitare di creare
     * più schede correnti per lo stesso cliente.
     *
     * @param customerId identificativo del cliente
     * @return true se esiste già una scheda associata
     */
    boolean existsByCustomer_Id(Integer customerId);
}
