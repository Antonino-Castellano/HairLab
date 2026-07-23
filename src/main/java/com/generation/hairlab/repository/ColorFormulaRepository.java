package com.generation.hairlab.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.hairlab.enums.ColorFormulaStatus;
import com.generation.hairlab.model.ColorFormula;

/**
 * Repository delle formule colore.
 *
 * Il collegamento diretto con Customer permette
 * di recuperare lo storico formula della cliente
 * indipendentemente da Consultation e AppointmentItem.
 */
public interface ColorFormulaRepository
    extends JpaRepository<ColorFormula, Integer> {

    Optional<ColorFormula>
        findByNameIgnoreCase(
            String name
        );

    List<ColorFormula>
        findByCustomer_IdOrderByCreatedAtDesc(
            Integer customerId
        );

    Optional<ColorFormula>
        findFirstByCustomer_IdAndStatusOrderByCreatedAtDesc(
            Integer customerId,
            ColorFormulaStatus status
        );

    List<ColorFormula>
        findByConsultation_IdOrderByCreatedAtDesc(
            Integer consultationId
        );

    List<ColorFormula>
        findByAppointmentItem_IdOrderByCreatedAtDesc(
            Integer appointmentItemId
        );

    List<ColorFormula>
        findByStatus(
            ColorFormulaStatus status
        );

    boolean existsByNameIgnoreCase(
        String name
    );
}
