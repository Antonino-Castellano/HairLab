package com.generation.hairlab.repository;

import com.generation.hairlab.model.AppointmentItem;
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
 * AOT generated JPA repository implementation for {@link AppointmentItemRepository}.
 */
@Generated
public class AppointmentItemRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public AppointmentItemRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link AppointmentItemRepository#findByAppointment_IdOrderByScheduledTimeAsc(java.lang.Integer)}.
   */
  public List<AppointmentItem> findByAppointment_IdOrderByScheduledTimeAsc(Integer appointmentId) {
    String queryString = "SELECT a FROM AppointmentItem a WHERE a.appointment.id = :appointmentId ORDER BY a.scheduledTime asc";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("appointmentId", appointmentId);

    return (List<AppointmentItem>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link AppointmentItemRepository#findByEmployee_IdAndScheduledTimeBetweenOrderByScheduledTimeAsc(java.lang.Integer,java.time.LocalDateTime,java.time.LocalDateTime)}.
   */
  public List<AppointmentItem> findByEmployee_IdAndScheduledTimeBetweenOrderByScheduledTimeAsc(
      Integer employeeId, LocalDateTime start, LocalDateTime end) {
    String queryString = "SELECT a FROM AppointmentItem a WHERE a.employee.id = :employeeId AND a.scheduledTime BETWEEN :start AND :end ORDER BY a.scheduledTime asc";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("employeeId", employeeId);
    query.setParameter("start", start);
    query.setParameter("end", end);

    return (List<AppointmentItem>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link AppointmentItemRepository#findByEmployee_IdOrderByScheduledTimeAsc(java.lang.Integer)}.
   */
  public List<AppointmentItem> findByEmployee_IdOrderByScheduledTimeAsc(Integer employeeId) {
    String queryString = "SELECT a FROM AppointmentItem a WHERE a.employee.id = :employeeId ORDER BY a.scheduledTime asc";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("employeeId", employeeId);

    return (List<AppointmentItem>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link AppointmentItemRepository#findBySalonProduct_Id(java.lang.Integer)}.
   */
  public List<AppointmentItem> findBySalonProduct_Id(Integer salonProductId) {
    String queryString = "SELECT a FROM AppointmentItem a WHERE a.salonProduct.id = :salonProductId";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("salonProductId", salonProductId);

    return (List<AppointmentItem>) query.getResultList();
  }
}
