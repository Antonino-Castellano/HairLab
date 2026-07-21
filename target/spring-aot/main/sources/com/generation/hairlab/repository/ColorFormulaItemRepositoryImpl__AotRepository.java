package com.generation.hairlab.repository;

import com.generation.hairlab.model.ColorFormulaItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.Integer;
import java.lang.String;
import java.util.List;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * AOT generated JPA repository implementation for {@link ColorFormulaItemRepository}.
 */
@Generated
public class ColorFormulaItemRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public ColorFormulaItemRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link ColorFormulaItemRepository#findByColorFormula_Id(java.lang.Integer)}.
   */
  public List<ColorFormulaItem> findByColorFormula_Id(Integer colorFormulaId) {
    String queryString = "SELECT c FROM ColorFormulaItem c WHERE c.colorFormula.id = :colorFormulaId";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("colorFormulaId", colorFormulaId);

    return (List<ColorFormulaItem>) query.getResultList();
  }
}
