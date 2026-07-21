package com.generation.hairlab.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.hairlab.model.SalonProduct;

/**
 * Repository dedicato ai SalonProduct, cioè i servizi o prodotti
 * presenti nel listino del salone.
 *
 * I metodi aggiuntivi permettono di filtrare il catalogo per stato,
 * categoria e nome senza scrivere manualmente query SQL.
 */
public interface SalonProductRepository extends JpaRepository<SalonProduct, Integer> {

    /**
     * Cerca un servizio o prodotto tramite il suo nome univoco.
     *
     * @param name nome da cercare
     * @return Optional contenente il SalonProduct se presente
     */
    Optional<SalonProduct> findByNameIgnoreCase(String name);

    /**
     * Restituisce tutti i servizi o prodotti attivi.
     *
     * È utile per mostrare nel frontend solamente gli elementi che possono
     * essere utilizzati per nuove prenotazioni.
     *
     * @return lista degli elementi attivi
     */
    List<SalonProduct> findByActiveTrue();

    /**
     * Cerca tutti i SalonProduct appartenenti a una determinata categoria.
     *
     * L'underscore esplicita la navigazione ProductCategory -> id.
     *
     * @param productCategoryId identificativo della categoria
     * @return lista dei servizi o prodotti appartenenti alla categoria
     */
    List<SalonProduct> findByProductCategory_Id(Integer productCategoryId);

    /**
     * Restituisce gli elementi attivi appartenenti a una specifica categoria.
     *
     * @param productCategoryId identificativo della categoria
     * @return lista degli elementi attivi della categoria
     */
    List<SalonProduct> findByProductCategory_IdAndActiveTrue(Integer productCategoryId);

    /**
     * Cerca servizi o prodotti tramite una parte del nome,
     * ignorando maiuscole e minuscole.
     *
     * @param name parte del nome da cercare
     * @return lista dei risultati corrispondenti
     */
    List<SalonProduct> findByNameContainingIgnoreCase(String name);

    /**
     * Verifica se esiste già un SalonProduct con lo stesso nome.
     *
     * @param name nome da verificare
     * @return true se il nome è già presente
     */
    boolean existsByNameIgnoreCase(String name);
}
