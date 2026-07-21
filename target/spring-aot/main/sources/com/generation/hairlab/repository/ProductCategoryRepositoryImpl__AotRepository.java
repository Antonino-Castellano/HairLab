package com.generation.hairlab.repository;

import com.generation.hairlab.model.ProductCategory;
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
 * AOT generated JPA repository implementation for {@link ProductCategoryRepository}.
 */
@Generated
public class ProductCategoryRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public ProductCategoryRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link ProductCategoryRepository#existsByNameIgnoreCase(java.lang.String)}.
   */
  public boolean existsByNameIgnoreCase(String name) {
    String queryString = "SELECT p.id FROM ProductCategory p WHERE UPPER(p.name) = UPPER(:name)";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("name", name != null ? name.toUpperCase() : name);
    query.setMaxResults(1);

    return !query.getResultList().isEmpty();
  }

  /**
   * AOT generated implementation of {@link ProductCategoryRepository#findByActiveTrue()}.
   */
  public List<ProductCategory> findByActiveTrue() {
    String queryString = "SELECT p FROM ProductCategory p WHERE p.active = TRUE";
    Query query = this.entityManager.createQuery(queryString);

    return (List<ProductCategory>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link ProductCategoryRepository#findByNameIgnoreCase(java.lang.String)}.
   */
  public Optional<ProductCategory> findByNameIgnoreCase(String name) {
    String queryString = "SELECT p FROM ProductCategory p WHERE UPPER(p.name) = UPPER(:name)";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("name", name != null ? name.toUpperCase() : name);

    return Optional.ofNullable((ProductCategory) convertOne(query.getSingleResultOrNull(), false, ProductCategory.class));
  }
}
