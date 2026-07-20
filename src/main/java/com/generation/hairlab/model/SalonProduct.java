package com.generation.hairlab.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class SalonProduct {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany
    @JoinColumn (name = "productCategory_id")
    private ProductCategory productCategory;

    @Column (nullable = false, unique = true)
    private String name;

    private String desc;

    @Column (nullable = false)
    private int duration;

    private double basePrice;

    private boolean active;
}
