package com.generation.hairlab.repository;

import com.generation.hairlab.enums.AppointmentStatus;
import com.generation.hairlab.model.Appointment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.Integer;
import java.lang.String;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * AOT generated JPA repository implementation for {@link AppointmentRepository}.
 */
@Generated
public class AppointmentRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public AppointmentRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link AppointmentRepository#findByCustomer_IdAndStatusOrderByStartDateTimeDesc(java.lang.Integer,com.generation.hairlab.enums.AppointmentStatus)}.
   */
  public List<Appointment> findByCustomer_IdAndStatusOrderByStartDateTimeDesc(Integer customerId,
      AppointmentStatus status) {
    String queryString = "SELECT a FROM Appointment a WHERE a.customer.id = :customerId AND a.status = :status ORDER BY a.startDateTime desc";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("customerId", customerId);
    query.setParameter("status", status);

    return (List<Appointment>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link AppointmentRepository#findByCustomer_IdOrderByStartDateTimeDesc(java.lang.Integer)}.
   */
  public List<Appointment> findByCustomer_IdOrderByStartDateTimeDesc(Integer customerId) {
    String queryString = "SELECT a FROM Appointment a WHERE a.customer.id = :customerId ORDER BY a.startDateTime desc";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("customerId", customerId);

    return (List<Appointment>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link AppointmentRepository#findByStartDateTimeBetweenOrderByStartDateTimeAsc(java.time.LocalDateTime,java.time.LocalDateTime)}.
   */
  public List<Appointment> findByStartDateTimeBetweenOrderByStartDateTimeAsc(LocalDateTime start,
      LocalDateTime end) {
    String queryString = "SELECT a FROM Appointment a WHERE a.startDateTime BETWEEN :start AND :end ORDER BY a.startDateTime asc";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("start", start);
    query.setParameter("end", end);

    return (List<Appointment>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link AppointmentRepository#findByStatus(com.generation.hairlab.enums.AppointmentStatus)}.
   */
  public List<Appointment> findByStatus(AppointmentStatus status) {
    String queryString = "SELECT a FROM Appointment a WHERE a.status = :status";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("status", status);

    return (List<Appointment>) query.getResultList();
  }
}
