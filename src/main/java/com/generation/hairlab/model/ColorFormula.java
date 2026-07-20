package com.generation.hairlab.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class ColorFormula {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    @JoinColumn (name = "consultation_id")
    private Consultation consultation;

    @ManyToOne
    @JoinColumn (name = "appointmentItem_id")
    private AppointmentItem appointmentItem;

    @Column (nullable = false, unique = true)
    private String name;

    @Column (nullable = false)
    private String targetResult;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private Oxygen volumeDeveloper;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private MixingRatio mixingRatio;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private ColorFormulaStatus status;

    private String notes;

    @Column (nullable = false)
    private LocalDateTime createdAt;
}
