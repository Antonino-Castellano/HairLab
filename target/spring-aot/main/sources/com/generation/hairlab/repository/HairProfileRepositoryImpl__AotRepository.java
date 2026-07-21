package com.generation.hairlab.repository;

import com.generation.hairlab.model.HairProfile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.Integer;
import java.lang.String;
import java.util.Optional;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * AOT generated JPA repository implementation for {@link HairProfileRepository}.
 */
@Generated
public class HairProfileRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public HairProfileRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link HairProfileRepository#existsByCustomer_Id(java.lang.Integer)}.
   */
  public boolean existsByCustomer_Id(Integer customerId) {
    String queryString = "SELECT h.id FROM HairProfile h WHERE h.customer.id = :customerId";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("customerId", customerId);
    query.setMaxResults(1);

    return !query.getResultList().isEmpty();
  }

  /**
   * AOT generated implementation of {@link HairProfileRepository#findByCustomer_Id(java.lang.Integer)}.
   */
  public Optional<HairProfile> findByCustomer_Id(Integer customerId) {
    String queryString = "SELECT h FROM HairProfile h WHERE h.customer.id = :customerId";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("customerId", customerId);

    return Optional.ofNullable((HairProfile) convertOne(query.getSingleResultOrNull(), false, HairProfile.class));
  }
}
