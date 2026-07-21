package com.generation.hairlab.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.hairlab.model.Customer;

/**
 * Repository dedicato alla persistenza e alla ricerca delle Entity Customer.
 *
 * Estende JpaRepository per ottenere automaticamente le operazioni CRUD
 * principali, tra cui:
 *
 * - save(...)
 * - findById(...)
 * - findAll()
 * - deleteById(...)
 * - existsById(...)
 *
 * I metodi aggiuntivi sfruttano le query derivate di Spring Data JPA:
 * Spring costruisce automaticamente la query leggendo il nome del metodo.
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    /**
     * Cerca un cliente tramite il suo indirizzo email.
     *
     * L'email è univoca nella Entity Customer, quindi il risultato può
     * contenere al massimo un cliente.
     *
     * @param email indirizzo email da cercare
     * @return Optional contenente il cliente se presente
     */
    Optional<Customer> findByEmail(String email);

    /**
     * Cerca i clienti tramite una parte del nome o del cognome,
     * ignorando differenze tra maiuscole e minuscole.
     *
     * Lo stesso valore di ricerca può essere passato a entrambi i parametri.
     *
     * @param firstName parte del nome da cercare
     * @param lastName parte del cognome da cercare
     * @return lista dei clienti corrispondenti
     */
    List<Customer> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName,
            String lastName);

    /**
     * Restituisce tutti i clienti attivi.
     *
     * È utile per mostrare soltanto i clienti utilizzabili nelle nuove
     * prenotazioni senza cancellare lo storico dei clienti disattivati.
     *
     * @return lista dei clienti attivi
     */
    List<Customer> findByActiveTrue();

    /**
     * Verifica se esiste già un cliente con la stessa email.
     *
     * Può essere utilizzato dal Service prima di creare un nuovo cliente
     * per gestire in modo chiaro il vincolo unique presente nel database.
     *
     * @param email email da verificare
     * @return true se l'email è già presente
     */
    boolean existsByEmail(String email);
}
