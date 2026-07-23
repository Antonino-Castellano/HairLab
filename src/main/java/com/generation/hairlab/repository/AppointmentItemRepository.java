package com.generation.hairlab.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.hairlab.model.AppointmentItem;

/**
 * Repository JPA per AppointmentItem.
 *
 * Oltre alle query già utilizzate dal modulo
 * Appuntamenti, il Blocco 8 aggiunge una query
 * temporale globale usata dalla Timeline.
 *
 * In questo modo il frontend non deve più fare:
 *
 * 1 richiesta appuntamenti
 * +
 * N richieste AppointmentItem
 *
 * ma usa un endpoint aggregato dedicato.
 */
@Repository
public interface AppointmentItemRepository
    extends JpaRepository<AppointmentItem, Integer> {

    /**
     * Item di un appuntamento,
     * ordinati cronologicamente.
     */
    List<AppointmentItem>
        findByAppointment_IdOrderByScheduledTimeAsc(
            Integer appointmentId
        );

    /**
     * Item assegnati a un operatore.
     */
    List<AppointmentItem>
        findByEmployee_IdOrderByScheduledTimeAsc(
            Integer employeeId
        );

    /**
     * Item di un operatore in un intervallo.
     */
    List<AppointmentItem>
        findByEmployee_IdAndScheduledTimeBetweenOrderByScheduledTimeAsc(
            Integer employeeId,
            LocalDateTime start,
            LocalDateTime end
        );

    /**
     * Item collegati a uno specifico servizio.
     */
    List<AppointmentItem>
        findBySalonProduct_Id(
            Integer salonProductId
        );

    /**
     * Tutti gli item programmati in:
     *
     * start <= scheduledTime < end
     *
     * Usiamo esplicitamente:
     *
     * GreaterThanEqual
     * +
     * LessThan
     *
     * per evitare il problema del confine
     * della mezzanotte tra due giornate.
     */
    List<AppointmentItem>
        findByScheduledTimeGreaterThanEqualAndScheduledTimeLessThanOrderByScheduledTimeAsc(
            LocalDateTime start,
            LocalDateTime end
        );
}
