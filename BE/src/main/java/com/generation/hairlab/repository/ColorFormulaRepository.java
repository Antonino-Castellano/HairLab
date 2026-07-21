package com.generation.hairlab.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.hairlab.enums.ColorFormulaStatus;
import com.generation.hairlab.model.ColorFormula;

/**
 * Repository dedicato alle formule colore create nel gestionale HairLab.
 *
 * Le formule possono essere ricercate per nome, consulenza,
 * AppointmentItem o stato del loro ciclo di vita.
 */
public interface ColorFormulaRepository extends JpaRepository<ColorFormula, Integer> {

    /**
     * Cerca una formula tramite il nome univoco.
     *
     * @param name nome della formula
     * @return Optional contenente la formula se presente
     */
    Optional<ColorFormula> findByNameIgnoreCase(String name);

    /**
     * Restituisce tutte le formule associate a una consulenza,
     * ordinate dalla più recente.
     *
     * @param consultationId identificativo della consulenza
     * @return lista delle formule collegate
     */
    List<ColorFormula> findByConsultation_IdOrderByCreatedAtDesc(Integer consultationId);

    /**
     * Restituisce tutte le formule collegate a uno specifico AppointmentItem.
     *
     * Questo permette di recuperare, ad esempio, le diverse formule
     * proposte o utilizzate per uno stesso servizio tecnico.
     *
     * @param appointmentItemId identificativo dell'AppointmentItem
     * @return lista delle formule collegate
     */
    List<ColorFormula> findByAppointmentItem_IdOrderByCreatedAtDesc(Integer appointmentItemId);

    /**
     * Restituisce le formule che si trovano in uno specifico stato.
     *
     * @param status stato della formula
     * @return lista delle formule corrispondenti
     */
    List<ColorFormula> findByStatus(ColorFormulaStatus status);

    /**
     * Verifica se esiste già una formula con lo stesso nome.
     *
     * @param name nome da verificare
     * @return true se il nome è già presente
     */
    boolean existsByNameIgnoreCase(String name);
}
