package com.generation.hairlab.repository;

import com.generation.hairlab.enums.JobTitle;
import com.generation.hairlab.model.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.String;
import java.util.List;
import java.util.Optional;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * AOT generated JPA repository implementation for {@link EmployeeRepository}.
 */
@Generated
public class EmployeeRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public EmployeeRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link EmployeeRepository#existsByEmail(java.lang.String)}.
   */
  public boolean existsByEmail(String email) {
    String queryString = "SELECT e.id FROM Employee e WHERE e.email = :email";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("email", email);
    query.setMaxResults(1);

    return !query.getResultList().isEmpty();
  }

  /**
   * AOT generated implementation of {@link EmployeeRepository#existsByTelephoneNumber(java.lang.String)}.
   */
  public boolean existsByTelephoneNumber(String telephoneNumber) {
    String queryString = "SELECT e.id FROM Employee e WHERE e.telephoneNumber = :telephoneNumber";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("telephoneNumber", telephoneNumber);
    query.setMaxResults(1);

    return !query.getResultList().isEmpty();
  }

  /**
   * AOT generated implementation of {@link EmployeeRepository#findByActiveTrue()}.
   */
  public List<Employee> findByActiveTrue() {
    String queryString = "SELECT e FROM Employee e WHERE e.active = TRUE";
    Query query = this.entityManager.createQuery(queryString);

    return (List<Employee>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link EmployeeRepository#findByEmail(java.lang.String)}.
   */
  public Optional<Employee> findByEmail(String email) {
    String queryString = "SELECT e FROM Employee e WHERE e.email = :email";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("email", email);

    return Optional.ofNullable((Employee) convertOne(query.getSingleResultOrNull(), false, Employee.class));
  }

  /**
   * AOT generated implementation of {@link EmployeeRepository#findByJobTitle(com.generation.hairlab.enums.JobTitle)}.
   */
  public List<Employee> findByJobTitle(JobTitle jobTitle) {
    String queryString = "SELECT e FROM Employee e WHERE e.jobTitle = :jobTitle";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("jobTitle", jobTitle);

    return (List<Employee>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link EmployeeRepository#findByTelephoneNumber(java.lang.String)}.
   */
  public Optional<Employee> findByTelephoneNumber(String telephoneNumber) {
    String queryString = "SELECT e FROM Employee e WHERE e.telephoneNumber = :telephoneNumber";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("telephoneNumber", telephoneNumber);

    return Optional.ofNullable((Employee) convertOne(query.getSingleResultOrNull(), false, Employee.class));
  }
}
