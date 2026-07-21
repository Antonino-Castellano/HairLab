package com.generation.hairlab.repository;

import com.generation.hairlab.model.Customer;
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
 * AOT generated JPA repository implementation for {@link CustomerRepository}.
 */
@Generated
public class CustomerRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public CustomerRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link CustomerRepository#existsByEmail(java.lang.String)}.
   */
  public boolean existsByEmail(String email) {
    String queryString = "SELECT c.id FROM Customer c WHERE c.email = :email";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("email", email);
    query.setMaxResults(1);

    return !query.getResultList().isEmpty();
  }

  /**
   * AOT generated implementation of {@link CustomerRepository#findByActiveTrue()}.
   */
  public List<Customer> findByActiveTrue() {
    String queryString = "SELECT c FROM Customer c WHERE c.active = TRUE";
    Query query = this.entityManager.createQuery(queryString);

    return (List<Customer>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link CustomerRepository#findByEmail(java.lang.String)}.
   */
  public Optional<Customer> findByEmail(String email) {
    String queryString = "SELECT c FROM Customer c WHERE c.email = :email";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("email", email);

    return Optional.ofNullable((Customer) convertOne(query.getSingleResultOrNull(), false, Customer.class));
  }

  /**
   * AOT generated implementation of {@link CustomerRepository#findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(java.lang.String,java.lang.String)}.
   */
  public List<Customer> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
      String firstName, String lastName) {
    String queryString = "SELECT c FROM Customer c WHERE UPPER(c.firstName) LIKE UPPER(:firstName) ESCAPE '\\' OR UPPER(c.lastName) LIKE UPPER(:lastName) ESCAPE '\\'";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("firstName", "%%%s%%".formatted(firstName != null ? firstName.toUpperCase() : firstName));
    query.setParameter("lastName", "%%%s%%".formatted(lastName != null ? lastName.toUpperCase() : lastName));

    return (List<Customer>) query.getResultList();
  }
}
