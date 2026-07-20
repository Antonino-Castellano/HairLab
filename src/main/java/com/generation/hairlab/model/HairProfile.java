package com.generation.hairlab.model;

import java.util.Set;

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

@Entity
@Data
public class HairProfile {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn (name = "customer_id")
    private Customer customer;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private ToneLevel naturalTone;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private ToneLevel currentTone;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private HairType hairType;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private HairTexture texture;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private PhysicalValue porosity;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private PhysicalValue density;

    @Column (nullable = false)
    private Set<String> scalpCondition;

    @Column (nullable = false)
    private Set<String> chimicalHistory;

    @Column (nullable = false)
    private Set<String> sensitivities;

    @Column (nullable = false)
    private Set<String> contraindications;

    private String notes;
}
