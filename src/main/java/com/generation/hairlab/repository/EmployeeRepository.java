package com.generation.hairlab.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.hairlab.enums.JobTitle;
import com.generation.hairlab.model.Employee;

/**
 * Repository dedicato alla gestione persistente degli Employee.
 *
 * JpaRepository fornisce le operazioni CRUD di base, mentre i metodi
 * aggiuntivi permettono di ricercare dipendenti tramite dati utili
 * alla gestione operativa del salone.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    /**
     * Cerca un dipendente tramite email.
     *
     * L'email è definita come univoca nella Entity Employee.
     *
     * @param email email del dipendente
     * @return Optional contenente il dipendente se presente
     */
    Optional<Employee> findByEmail(String email);

    /**
     * Cerca un dipendente tramite numero di telefono.
     *
     * Il numero è definito come univoco nella Entity Employee.
     *
     * @param telephoneNumber numero di telefono da cercare
     * @return Optional contenente il dipendente se presente
     */
    Optional<Employee> findByTelephoneNumber(String telephoneNumber);

    /**
     * Restituisce solamente i dipendenti attivi.
     *
     * È utile, ad esempio, durante l'assegnazione di un operatore
     * a un AppointmentItem.
     *
     * @return lista dei dipendenti attivi
     */
    List<Employee> findByActiveTrue();

    /**
     * Cerca tutti i dipendenti che ricoprono una determinata mansione.
     *
     * JobTitle rappresenta la mansione principale e non le singole
     * specializzazioni professionali.
     *
     * @param jobTitle mansione da cercare
     * @return lista dei dipendenti con la mansione indicata
     */
    List<Employee> findByJobTitle(JobTitle jobTitle);

    /**
     * Verifica se esiste già un dipendente con la stessa email.
     *
     * @param email email da verificare
     * @return true se l'email è già presente
     */
    boolean existsByEmail(String email);

    /**
     * Verifica se esiste già un dipendente con lo stesso numero di telefono.
     *
     * @param telephoneNumber numero da verificare
     * @return true se il numero è già presente
     */
    boolean existsByTelephoneNumber(String telephoneNumber);
}
