package com.generation.hairlab.repository;

import com.generation.hairlab.enums.ProductType;
import com.generation.hairlab.enums.Reflection;
import com.generation.hairlab.enums.ToneLevel;
import com.generation.hairlab.model.HairDye;
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
 * AOT generated JPA repository implementation for {@link HairDyeRepository}.
 */
@Generated
public class HairDyeRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public HairDyeRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link HairDyeRepository#existsByCode(java.lang.String)}.
   */
  public boolean existsByCode(String code) {
    String queryString = "SELECT h.id FROM HairDye h WHERE h.code = :code";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("code", code);
    query.setMaxResults(1);

    return !query.getResultList().isEmpty();
  }

  /**
   * AOT generated implementation of {@link HairDyeRepository#findByActiveTrue()}.
   */
  public List<HairDye> findByActiveTrue() {
    String queryString = "SELECT h FROM HairDye h WHERE h.active = TRUE";
    Query query = this.entityManager.createQuery(queryString);

    return (List<HairDye>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link HairDyeRepository#findByBrandIgnoreCase(java.lang.String)}.
   */
  public List<HairDye> findByBrandIgnoreCase(String brand) {
    String queryString = "SELECT h FROM HairDye h WHERE UPPER(h.brand) = UPPER(:brand)";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("brand", brand != null ? brand.toUpperCase() : brand);

    return (List<HairDye>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link HairDyeRepository#findByCode(java.lang.String)}.
   */
  public Optional<HairDye> findByCode(String code) {
    String queryString = "SELECT h FROM HairDye h WHERE h.code = :code";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("code", code);

    return Optional.ofNullable((HairDye) convertOne(query.getSingleResultOrNull(), false, HairDye.class));
  }

  /**
   * AOT generated implementation of {@link HairDyeRepository#findByPrimaryReflection(com.generation.hairlab.enums.Reflection)}.
   */
  public List<HairDye> findByPrimaryReflection(Reflection primaryReflection) {
    String queryString = "SELECT h FROM HairDye h WHERE h.primaryReflection = :primaryReflection";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("primaryReflection", primaryReflection);

    return (List<HairDye>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link HairDyeRepository#findByProductType(com.generation.hairlab.enums.ProductType)}.
   */
  public List<HairDye> findByProductType(ProductType productType) {
    String queryString = "SELECT h FROM HairDye h WHERE h.productType = :productType";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("productType", productType);

    return (List<HairDye>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link HairDyeRepository#findByToneLevel(com.generation.hairlab.enums.ToneLevel)}.
   */
  public List<HairDye> findByToneLevel(ToneLevel toneLevel) {
    String queryString = "SELECT h FROM HairDye h WHERE h.toneLevel = :toneLevel";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("toneLevel", toneLevel);

    return (List<HairDye>) query.getResultList();
  }
}
