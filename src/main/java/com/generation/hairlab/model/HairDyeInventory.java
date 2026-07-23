package com.generation.hairlab.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.generation.hairlab.enums.InventoryUnit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Magazzino quantitativo di un prodotto tecnico Color Lab.
 *
 * Separiamo il catalogo tecnico HairDye dallo stock:
 *
 * HairDye
 * -> descrive COS'È il prodotto.
 *
 * HairDyeInventory
 * -> descrive QUANTO ne abbiamo.
 *
 * Questo permette di mantenere stabile lo storico formule
 * anche quando lo stock cambia nel tempo.
 */
@Entity
@Data
public class HairDyeInventory {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Integer id;

    /**
     * Ogni prodotto tecnico possiede
     * al massimo una posizione di magazzino corrente.
     */
    @OneToOne
    @JoinColumn(
        name = "hair_dye_id",
        nullable = false,
        unique = true
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private HairDye hairDye;

    /**
     * Quantità attualmente disponibile.
     */
    @Column(
        name = "quantity_available",
        nullable = false,
        precision = 10,
        scale = 2
    )
    private BigDecimal quantityAvailable;

    /**
     * Unità di misura dello stock.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InventoryUnit unit;

    /**
     * Soglia sotto la quale il prodotto
     * viene considerato in scorta bassa.
     */
    @Column(
        name = "low_stock_threshold",
        nullable = false,
        precision = 10,
        scale = 2
    )
    private BigDecimal lowStockThreshold;

    /**
     * Ultimo aggiornamento della giacenza.
     */
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
