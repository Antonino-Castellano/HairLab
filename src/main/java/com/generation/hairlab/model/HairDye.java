package com.generation.hairlab.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class HairDye {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "primary_reflection")
    private Reflection primaryReflection;

    @Enumerated(EnumType.STRING)
    @Column(name = "secondary_reflection")
    private Reflection secondaryReflection;

    @Column (nullable = false)
    private String brand;

    @Column (nullable = false)
    private String name;

    @Column (nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    private ToneLevel toneLevel;

    private boolean active;
}
