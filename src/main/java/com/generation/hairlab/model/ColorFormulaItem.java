package com.generation.hairlab.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class ColorFormulaItem {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn (name = "colorFormula_id")
    private ColorFormula colorFormula;

    @OneToMany
    @JoinColumn (name = "hairDye_id")
    private Set<HairDye> hairDyes;

    @Column (nullable = false)
    private double quantity;

    private String notes;
}

