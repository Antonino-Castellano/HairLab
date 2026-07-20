package com.generation.hairlab.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.hairlab.model.ColorFormulaItem;

/**
 * Repository dedicato agli elementi che compongono una ColorFormula.
 *
 * Ogni ColorFormulaItem appartiene a una formula e può essere recuperato
 * tramite l'identificativo della formula proprietaria.
 */
public interface ColorFormulaItemRepository extends JpaRepository<ColorFormulaItem, Integer> {

    /**
     * Restituisce tutti gli item appartenenti a una specifica formula.
     *
     * @param colorFormulaId identificativo della formula
     * @return lista degli elementi della formula
     */
    List<ColorFormulaItem> findByColorFormula_Id(Integer colorFormulaId);
}
