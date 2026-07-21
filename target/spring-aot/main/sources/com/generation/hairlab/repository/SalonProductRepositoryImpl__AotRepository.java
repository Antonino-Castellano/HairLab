package com.generation.hairlab.repository;

import com.generation.hairlab.model.SalonProduct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.Integer;
import java.lang.String;
import java.util.List;
import java.util.Optional;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * AOT generated JPA repository implementation for {@link SalonProductRepository}.
 */
@Generated
public class SalonProductRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public SalonProductRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link SalonProductRepository#existsByNameIgnoreCase(java.lang.String)}.
   */
  public boolean existsByNameIgnoreCase(String name) {
    String queryString = "SELECT s.id FROM SalonProduct s WHERE UPPER(s.name) = UPPER(:name)";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("name", name != null ? name.toUpperCase() : name);
    query.setMaxResults(1);

    return !query.getResultList().isEmpty();
  }

  /**
   * AOT generated implementation of {@link SalonProductRepository#findByActiveTrue()}.
   */
  public List<SalonProduct> findByActiveTrue() {
    String queryString = "SELECT s FROM SalonProduct s WHERE s.active = TRUE";
    Query query = this.entityManager.createQuery(queryString);

    return (List<SalonProduct>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link SalonProductRepository#findByNameContainingIgnoreCase(java.lang.String)}.
   */
  public List<SalonProduct> findByNameContainingIgnoreCase(String name) {
    String queryString = "SELECT s FROM SalonProduct s WHERE UPPER(s.name) LIKE UPPER(:name) ESCAPE '\\'";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("name", "%%%s%%".formatted(name != null ? name.toUpperCase() : name));

    return (List<SalonProduct>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link SalonProductRepository#findByNameIgnoreCase(java.lang.String)}.
   */
  public Optional<SalonProduct> findByNameIgnoreCase(String name) {
    String queryString = "SELECT s FROM SalonProduct s WHERE UPPER(s.name) = UPPER(:name)";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("name", name != null ? name.toUpperCase() : name);

    return Optional.ofNullable((SalonProduct) convertOne(query.getSingleResultOrNull(), false, SalonProduct.class));
  }

  /**
   * AOT generated implementation of {@link SalonProductRepository#findByProductCategory_Id(java.lang.Integer)}.
   */
  public List<SalonProduct> findByProductCategory_Id(Integer productCategoryId) {
    String queryString = "SELECT s FROM SalonProduct s WHERE s.productCategory.id = :productCategoryId";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("productCategoryId", productCategoryId);

    return (List<SalonProduct>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link SalonProductRepository#findByProductCategory_IdAndActiveTrue(java.lang.Integer)}.
   */
  public List<SalonProduct> findByProductCategory_IdAndActiveTrue(Integer productCategoryId) {
    String queryString = "SELECT s FROM SalonProduct s WHERE s.productCategory.id = :productCategoryId AND s.active = TRUE";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("productCategoryId", productCategoryId);

    return (List<SalonProduct>) query.getResultList();
  }
}
