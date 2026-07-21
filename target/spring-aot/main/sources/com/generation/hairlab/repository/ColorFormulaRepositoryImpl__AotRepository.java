package com.generation.hairlab.repository;

import com.generation.hairlab.enums.ColorFormulaStatus;
import com.generation.hairlab.model.ColorFormula;
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
 * AOT generated JPA repository implementation for {@link ColorFormulaRepository}.
 */
@Generated
public class ColorFormulaRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public ColorFormulaRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link ColorFormulaRepository#existsByNameIgnoreCase(java.lang.String)}.
   */
  public boolean existsByNameIgnoreCase(String name) {
    String queryString = "SELECT c.id FROM ColorFormula c WHERE UPPER(c.name) = UPPER(:name)";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("name", name != null ? name.toUpperCase() : name);
    query.setMaxResults(1);

    return !query.getResultList().isEmpty();
  }

  /**
   * AOT generated implementation of {@link ColorFormulaRepository#findByAppointmentItem_IdOrderByCreatedAtDesc(java.lang.Integer)}.
   */
  public List<ColorFormula> findByAppointmentItem_IdOrderByCreatedAtDesc(
      Integer appointmentItemId) {
    String queryString = "SELECT c FROM ColorFormula c WHERE c.appointmentItem.id = :appointmentItemId ORDER BY c.createdAt desc";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("appointmentItemId", appointmentItemId);

    return (List<ColorFormula>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link ColorFormulaRepository#findByConsultation_IdOrderByCreatedAtDesc(java.lang.Integer)}.
   */
  public List<ColorFormula> findByConsultation_IdOrderByCreatedAtDesc(Integer consultationId) {
    String queryString = "SELECT c FROM ColorFormula c WHERE c.consultation.id = :consultationId ORDER BY c.createdAt desc";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("consultationId", consultationId);

    return (List<ColorFormula>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link ColorFormulaRepository#findByNameIgnoreCase(java.lang.String)}.
   */
  public Optional<ColorFormula> findByNameIgnoreCase(String name) {
    String queryString = "SELECT c FROM ColorFormula c WHERE UPPER(c.name) = UPPER(:name)";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("name", name != null ? name.toUpperCase() : name);

    return Optional.ofNullable((ColorFormula) convertOne(query.getSingleResultOrNull(), false, ColorFormula.class));
  }

  /**
   * AOT generated implementation of {@link ColorFormulaRepository#findByStatus(com.generation.hairlab.enums.ColorFormulaStatus)}.
   */
  public List<ColorFormula> findByStatus(ColorFormulaStatus status) {
    String queryString = "SELECT c FROM ColorFormula c WHERE c.status = :status";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("status", status);

    return (List<ColorFormula>) query.getResultList();
  }
}
