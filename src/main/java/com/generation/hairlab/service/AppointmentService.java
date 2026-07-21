package com.generation.hairlab.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.generation.hairlab.dto.AppointmentDto;
import com.generation.hairlab.enums.AppointmentStatus;
import com.generation.hairlab.mapper.AppointmentMapper;
import com.generation.hairlab.model.Appointment;
import com.generation.hairlab.model.Customer;
import com.generation.hairlab.repository.AppointmentRepository;
import com.generation.hairlab.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato alla gestione degli appuntamenti.
 *
 * Risolve la relazione con Customer e gestisce i timestamp tecnici.
 */
@Service
@RequiredArgsConstructor
public class AppointmentService {

    /** Repository degli appuntamenti. */
    private final AppointmentRepository appointmentRepository;

    /** Repository necessario per risolvere customerId. */
    private final CustomerRepository customerRepository;

    /** Mapper Appointment <-> AppointmentDto. */
    private final AppointmentMapper appointmentMapper;

    /** Restituisce tutti gli appuntamenti. */
    public List<AppointmentDto> findAll() {
        return appointmentMapper.toDtoList(appointmentRepository.findAll());
    }

    /** Cerca un appuntamento tramite ID. */
    public AppointmentDto findById(Integer id) throws ServiceException {
        return appointmentMapper.toDto(getAppointmentById(id));
    }

    /** Restituisce lo storico degli appuntamenti di un cliente. */
    public List<AppointmentDto> findByCustomer(Integer customerId) {
        return appointmentMapper.toDtoList(
                appointmentRepository.findByCustomer_IdOrderByStartDateTimeDesc(customerId));
    }

    /** Restituisce gli appuntamenti con uno specifico stato. */
    public List<AppointmentDto> findByStatus(AppointmentStatus status) {
        return appointmentMapper.toDtoList(appointmentRepository.findByStatus(status));
    }

    /** Restituisce gli appuntamenti compresi in un intervallo temporale. */
    public List<AppointmentDto> findBetween(
            LocalDateTime start,
            LocalDateTime end) {

        return appointmentMapper.toDtoList(
                appointmentRepository
                        .findByStartDateTimeBetweenOrderByStartDateTimeAsc(start, end));
    }

    /**
     * Inserisce un nuovo appuntamento.
     *
     * Il Customer viene recuperato tramite customerId e i timestamp
     * vengono gestiti dal backend.
     */
    public AppointmentDto insert(AppointmentDto dto) throws ServiceException {

        Customer customer = getCustomer(dto.getCustomerId());

        Appointment appointment = appointmentMapper.toEntity(dto);

        appointment.setCustomer(customer);
        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setUpdatedAt(LocalDateTime.now());

        return appointmentMapper.toDto(appointmentRepository.save(appointment));
    }

    /** Aggiorna un appuntamento esistente. */
    public AppointmentDto update(Integer id, AppointmentDto dto)
            throws ServiceException {

        Appointment appointment = getAppointmentById(id);
        Customer customer = getCustomer(dto.getCustomerId());

        appointment.setCustomer(customer);
        appointment.setStartDateTime(dto.getStartDateTime());
        appointment.setStatus(dto.getStatus());
        appointment.setNotes(dto.getNotes());
        appointment.setUpdatedAt(LocalDateTime.now());

        return appointmentMapper.toDto(appointmentRepository.save(appointment));
    }

    /** Elimina un appuntamento esistente. */
    public void delete(Integer id) throws ServiceException {
        appointmentRepository.delete(getAppointmentById(id));
    }

    /** Restituisce la Entity Appointment tramite ID. */
    public Appointment getAppointmentById(Integer id) throws ServiceException {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        "Appuntamento non trovato con id: " + id));
    }

    /** Recupera il Customer necessario alla relazione. */
    private Customer getCustomer(Integer customerId) throws ServiceException {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ServiceException(
                        "Cliente non trovato con id: " + customerId));

        if (!customer.isActive()) {
            throw new ServiceException(
                    "Non è possibile creare/modificare un appuntamento per un cliente non attivo");
        }

        return customer;
    }
}
