package com.generation.hairlab.model;

import java.time.LocalDate;
import java.util.Set;

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
public class Employee {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (nullable = false)
    private String firstName;

    @Column (nullable = false)
    private String lastName;

    @Column (nullable = false, unique = true)
    private String email;

    @Column (nullable = false, unique = true)
    private String telephoneNumber;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private JobTitle jobTitle;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private Set<Specializzation> specializzation;

    @Column (nullable = false)
    private LocalDate hireDate;

    private boolean active;

    private String notes;
}
