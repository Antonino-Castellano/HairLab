package com.generation.hairlab.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.hairlab.model.AppointmentItem;

/**
 * Repository dedicato ai singoli servizi contenuti negli appuntamenti.
 *
 * AppointmentItem collega Appointment, SalonProduct ed Employee.
 * I metodi di ricerca permettono di recuperare gli item di un appuntamento
 * e gli impegni di un operatore in uno specifico intervallo temporale.
 */
public interface AppointmentItemRepository extends JpaRepository<AppointmentItem, Integer> {

    /**
     * Restituisce tutti gli item appartenenti a un appuntamento,
     * ordinati per orario previsto.
     *
     * @param appointmentId identificativo dell'appuntamento
     * @return lista degli item dell'appuntamento
     */
    List<AppointmentItem> findByAppointment_IdOrderByScheduledTimeAsc(Integer appointmentId);

    /**
     * Restituisce tutti gli item assegnati a un dipendente,
     * ordinati cronologicamente.
     *
     * @param employeeId identificativo del dipendente
     * @return lista dei servizi assegnati all'operatore
     */
    List<AppointmentItem> findByEmployee_IdOrderByScheduledTimeAsc(Integer employeeId);

    /**
     * Restituisce gli item di un dipendente che iniziano all'interno
     * di uno specifico intervallo temporale.
     *
     * Questo metodo fornisce al Service i dati necessari per verificare
     * eventuali sovrapposizioni di agenda. La regola completa di overlap
     * rimane nel Service, perché deve considerare anche la durata del servizio.
     *
     * @param employeeId identificativo del dipendente
     * @param start inizio dell'intervallo
     * @param end fine dell'intervallo
     * @return lista degli item trovati
     */
    List<AppointmentItem> findByEmployee_IdAndScheduledTimeBetweenOrderByScheduledTimeAsc(
            Integer employeeId,
            LocalDateTime start,
            LocalDateTime end);

    /**
     * Restituisce tutti gli item nei quali è stato utilizzato
     * uno specifico SalonProduct.
     *
     * @param salonProductId identificativo del servizio/prodotto
     * @return lista degli item associati
     */
    List<AppointmentItem> findBySalonProduct_Id(Integer salonProductId);
}
