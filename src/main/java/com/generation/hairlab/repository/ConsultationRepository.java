package com.generation.hairlab.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.hairlab.enums.ConsultationType;
import com.generation.hairlab.model.Consultation;

/**
 * Repository dedicato alle consulenze tecniche effettuate sui clienti.
 *
 * Permette di recuperare lo storico delle consulenze per cliente,
 * operatore, appuntamento o tipologia.
 */
public interface ConsultationRepository extends JpaRepository<Consultation, Integer> {

    /**
     * Restituisce tutte le consulenze di un cliente,
     * dalla più recente alla meno recente.
     *
     * @param customerId identificativo del cliente
     * @return storico delle consulenze del cliente
     */
    List<Consultation> findByCustomer_IdOrderByConsultationDateDesc(Integer customerId);

    /**
     * Restituisce tutte le consulenze effettuate da un dipendente.
     *
     * @param employeeId identificativo dell'operatore
     * @return lista delle consulenze effettuate
     */
    List<Consultation> findByEmployee_IdOrderByConsultationDateDesc(Integer employeeId);

    /**
     * Restituisce le consulenze associate a uno specifico appuntamento.
     *
     * La relazione con Appointment è opzionale, quindi non tutte
     * le Consultation compariranno necessariamente in questa ricerca.
     *
     * @param appointmentId identificativo dell'appuntamento
     * @return lista delle consulenze collegate
     */
    List<Consultation> findByAppointment_Id(Integer appointmentId);

    /**
     * Filtra le consulenze in base alla tipologia tecnica.
     *
     * @param type tipo di consulenza
     * @return lista delle consulenze del tipo indicato
     */
    List<Consultation> findByType(ConsultationType type);
}
