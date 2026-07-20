package com.generation.hairlab.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.hairlab.model.Appointment;

public interface AppointmentRepository extends JpaRepository <Appointment, Integer> {

}
