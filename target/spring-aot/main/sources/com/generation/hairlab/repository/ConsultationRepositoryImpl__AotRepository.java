package com.generation.hairlab.repository;

import com.generation.hairlab.enums.ConsultationType;
import com.generation.hairlab.model.Consultation;
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
 * AOT generated JPA repository implementation for {@link ConsultationRepository}.
 */
@Generated
public class ConsultationRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public ConsultationRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link ConsultationRepository#findByAppointment_Id(java.lang.Integer)}.
   */
  public List<Consultation> findByAppointment_Id(Integer appointmentId) {
    String queryString = "SELECT c FROM Consultation c WHERE c.appointment.id = :appointmentId";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("appointmentId", appointmentId);

    return (List<Consultation>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link ConsultationRepository#findByCustomer_IdOrderByConsultationDateDesc(java.lang.Integer)}.
   */
  public List<Consultation> findByCustomer_IdOrderByConsultationDateDesc(Integer customerId) {
    String queryString = "SELECT c FROM Consultation c WHERE c.customer.id = :customerId ORDER BY c.consultationDate desc";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("customerId", customerId);

    return (List<Consultation>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link ConsultationRepository#findByEmployee_IdOrderByConsultationDateDesc(java.lang.Integer)}.
   */
  public List<Consultation> findByEmployee_IdOrderByConsultationDateDesc(Integer employeeId) {
    String queryString = "SELECT c FROM Consultation c WHERE c.employee.id = :employeeId ORDER BY c.consultationDate desc";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("employeeId", employeeId);

    return (List<Consultation>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link ConsultationRepository#findByType(com.generation.hairlab.enums.ConsultationType)}.
   */
  public List<Consultation> findByType(ConsultationType type) {
    String queryString = "SELECT c FROM Consultation c WHERE c.type = :type";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("type", type);

    return (List<Consultation>) query.getResultList();
  }
}
