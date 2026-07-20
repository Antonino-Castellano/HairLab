package com.generation.hairlab.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class AppointmentItem {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDateTime scheduledTime;

    @Column(nullable = false)
    private int duration; // In minuti

    private double agreedPrice;

    private String resultNotes;

    @Column(nullable = false)
    private LocalDateTime completedAt;

    @ManyToMany
    @JoinColumn (name = "appointment_id")
    private Appointment appointment;

    @OneToMany
    @JoinColumn (name = "salonProduct_id")
    private SalonProduct salonProduct;

    @OneToOne
    @JoinColumn (name = "employee_id")
    private Employee employee;
}
