package com.generation.hairlab.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.hairlab.enums.JobTitle;
import com.generation.hairlab.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    /** Cerca un dipendente tramite email. */
    Optional<Employee> findByEmail(String email);

    /** Cerca un dipendente tramite numero di telefono. */
    Optional<Employee> findByTelephoneNumber(String telephoneNumber);

    /** Restituisce solamente i dipendenti attivi. */
    List<Employee> findByActiveTrue();

    /** Restituisce solamente i dipendenti disattivati. */
    List<Employee> findByActiveFalse();

    /** Cerca tutti i dipendenti che ricoprono una determinata mansione. */
    List<Employee> findByJobTitle(JobTitle jobTitle);

    /** Verifica se esiste già un dipendente con la stessa email. */
    boolean existsByEmail(String email);

    /** Verifica se esiste già un dipendente con lo stesso numero di telefono. */
    boolean existsByTelephoneNumber(String telephoneNumber);
}