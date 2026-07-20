package com.generation.hairlab.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.hairlab.enums.ProductType;
import com.generation.hairlab.enums.Reflection;
import com.generation.hairlab.enums.ToneLevel;
import com.generation.hairlab.model.HairDye;

/**
 * Repository dedicato alle tinte e ai prodotti tecnici colore HairDye.
 *
 * Oltre al CRUD standard, permette di filtrare i prodotti per codice,
 * marca, tipo, altezza di tono e riflesso principale.
 */

public interface HairDyeRepository extends JpaRepository<HairDye, Integer> {

    /**
     * Cerca un prodotto tramite il codice commerciale univoco.
     *
     * @param code codice del prodotto
     * @return Optional contenente il prodotto se presente
     */
    Optional<HairDye> findByCode(String code);

    /**
     * Restituisce tutti i prodotti attivi.
     *
     * @return lista delle tinte/prodotti tecnici attivi
     */
    List<HairDye> findByActiveTrue();

    /**
     * Cerca i prodotti appartenenti a una determinata marca,
     * ignorando maiuscole e minuscole.
     *
     * @param brand marca da cercare
     * @return lista dei prodotti della marca
     */
    List<HairDye> findByBrandIgnoreCase(String brand);

    /**
     * Filtra i prodotti in base alla loro tipologia tecnica.
     *
     * @param productType tipo di prodotto
     * @return lista dei prodotti del tipo indicato
     */
    List<HairDye> findByProductType(ProductType productType);

    /**
     * Filtra i prodotti in base all'altezza di tono.
     *
     * @param toneLevel altezza di tono da cercare
     * @return lista dei prodotti corrispondenti
     */
    List<HairDye> findByToneLevel(ToneLevel toneLevel);

    /**
     * Filtra i prodotti in base al riflesso cromatico principale.
     *
     * @param primaryReflection riflesso principale
     * @return lista dei prodotti corrispondenti
     */
    List<HairDye> findByPrimaryReflection(Reflection primaryReflection);

    /**
     * Verifica se esiste già un prodotto con lo stesso codice.
     *
     * @param code codice da verificare
     * @return true se il codice è già presente
     */
    boolean existsByCode(String code);
}
