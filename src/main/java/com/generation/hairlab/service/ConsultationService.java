package com.generation.hairlab.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.generation.hairlab.dto.ConsultationDto;
import com.generation.hairlab.mapper.ConsultationMapper;
import com.generation.hairlab.model.Appointment;
import com.generation.hairlab.model.Consultation;
import com.generation.hairlab.model.Customer;
import com.generation.hairlab.model.Employee;
import com.generation.hairlab.repository.AppointmentRepository;
import com.generation.hairlab.repository.ConsultationRepository;
import com.generation.hairlab.repository.CustomerRepository;
import com.generation.hairlab.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato alle consulenze tecniche.
 *
 * Risolve le relazioni con Customer, Employee e l'eventuale Appointment.
 */
@Service
@RequiredArgsConstructor
public class ConsultationService {

   
    private ConsultationRepository consultationRepository;

    private CustomerRepository customerRepository;


    private EmployeeRepository employeeRepository;

    private AppointmentRepository appointmentRepository;


    private ConsultationMapper consultationMapper;

    /** Restituisce tutte le consulenze. */
    public List<ConsultationDto> findAll() {
        return consultationMapper.toDtoList(consultationRepository.findAll());
    }

    /** Cerca una consulenza tramite ID. */
    public ConsultationDto findById(Integer id) throws ServiceException {
        return consultationMapper.toDto(getConsultationById(id));
    }

    /** Restituisce lo storico delle consulenze di un cliente. */
    public List<ConsultationDto> findByCustomer(Integer customerId) {
        return consultationMapper.toDtoList(
                consultationRepository
                        .findByCustomer_IdOrderByConsultationDateDesc(customerId));
    }

    /** Inserisce una nuova consulenza tecnica. */
    public ConsultationDto insert(ConsultationDto dto) throws ServiceException {

        Customer customer = getCustomer(dto.getCustomerId());
        Employee employee = getEmployee(dto.getEmployeeId());
        Appointment appointment = getOptionalAppointment(dto.getAppointmentId());

        Consultation consultation = consultationMapper.toEntity(dto);

        consultation.setCustomer(customer);
        consultation.setEmployee(employee);
        consultation.setAppointment(appointment);

        return consultationMapper.toDto(consultationRepository.save(consultation));
    }

    /** Aggiorna una consulenza esistente. */
    public ConsultationDto update(Integer id, ConsultationDto dto)
            throws ServiceException {

        Consultation consultation = getConsultationById(id);

        consultation.setCustomer(getCustomer(dto.getCustomerId()));
        consultation.setEmployee(getEmployee(dto.getEmployeeId()));
        consultation.setAppointment(getOptionalAppointment(dto.getAppointmentId()));
        consultation.setConsultationDate(dto.getConsultationDate());
        consultation.setType(dto.getType());
        consultation.setObjective(dto.getObjective());
        consultation.setInitialDiagnosis(dto.getInitialDiagnosis());
        consultation.setCurrentCondition(dto.getCurrentCondition());
        consultation.setFeasibility(dto.getFeasibility());
        consultation.setRisks(dto.getRisks());
        consultation.setProposedProcedure(dto.getProposedProcedure());
        consultation.setTechnicalNotes(dto.getTechnicalNotes());

        return consultationMapper.toDto(consultationRepository.save(consultation));
    }

    /** Elimina una consulenza tramite ID. */
    public void delete(Integer id) throws ServiceException {
        consultationRepository.delete(getConsultationById(id));
    }

    /** Restituisce la Entity Consultation tramite ID. */
    public Consultation getConsultationById(Integer id) throws ServiceException {
        return consultationRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        "Consulenza non trovata con id: " + id));
    }

    private Customer getCustomer(Integer id) throws ServiceException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        "Cliente non trovato con id: " + id));
    }

    private Employee getEmployee(Integer id) throws ServiceException {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        "Dipendente non trovato con id: " + id));
    }

    /**
     * Recupera l'appuntamento se appointmentId è valorizzato.
     *
     * La relazione Consultation -> Appointment è opzionale.
     */
    private Appointment getOptionalAppointment(Integer id) throws ServiceException {

        if (id == null) {
            return null;
        }

        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        "Appuntamento non trovato con id: " + id));
    }
}
