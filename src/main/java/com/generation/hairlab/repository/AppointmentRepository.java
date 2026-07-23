package com.generation.hairlab.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.hairlab.enums.AppointmentStatus;
import com.generation.hairlab.model.Appointment;

/**
 * Repository dedicato alla gestione degli appuntamenti del salone.
 *
 * Oltre alle operazioni CRUD standard, espone ricerche utili per agenda,
 * storico cliente, filtri per stato e intervalli temporali.
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    /**
     * Restituisce lo storico degli appuntamenti di un cliente,
     * ordinato dal più recente al più vecchio.
     *
     * @param customerId identificativo del cliente
     * @return lista degli appuntamenti del cliente
     */
    List<Appointment> findByCustomer_IdOrderByStartDateTimeDesc(Integer customerId);

    /**
     * Restituisce gli appuntamenti che possiedono uno specifico stato.
     *
     * @param status stato da cercare
     * @return lista degli appuntamenti con lo stato indicato
     */
    List<Appointment> findByStatus(AppointmentStatus status);

    /**
     * Restituisce gli appuntamenti compresi tra due date/ore,
     * ordinati cronologicamente.
     *
     * Questo metodo è utile per costruire l'agenda giornaliera,
     * settimanale o per qualsiasi intervallo temporale.
     *
     * @param start limite iniziale incluso nella ricerca
     * @param end limite finale incluso nella ricerca
     * @return lista degli appuntamenti nell'intervallo
     */
    List<Appointment> findByStartDateTimeBetweenOrderByStartDateTimeAsc(
            LocalDateTime start,
            LocalDateTime end);

    /**
     * Restituisce gli appuntamenti di uno specifico cliente filtrati per stato.
     *
     * Può essere utilizzato, ad esempio, per ottenere lo storico dei soli
     * appuntamenti completati.
     *
     * @param customerId identificativo del cliente
     * @param status stato richiesto
     * @return lista degli appuntamenti corrispondenti
     */
    List<Appointment> findByCustomer_IdAndStatusOrderByStartDateTimeDesc(
            Integer customerId,
            AppointmentStatus status);

}
