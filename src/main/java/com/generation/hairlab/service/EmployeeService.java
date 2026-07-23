package com.generation.hairlab.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.hairlab.dto.EmployeeDto;
import com.generation.hairlab.mapper.EmployeeMapper;
import com.generation.hairlab.model.Employee;
import com.generation.hairlab.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato alla gestione dei dipendenti HairLab.
 *
 * Distingue chiaramente:
 * - disattivazione logica;
 * - riattivazione;
 * - eliminazione fisica definitiva.
 */
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    /** Restituisce tutti i dipendenti. */
    @Transactional(readOnly = true)
    public List<EmployeeDto> findAll() {
        return employeeMapper.toDtoList(employeeRepository.findAll());
    }

    /** Restituisce solamente i dipendenti attivi. */
    @Transactional(readOnly = true)
    public List<EmployeeDto> findActive() {
        return employeeMapper.toDtoList(employeeRepository.findByActiveTrue());
    }

    /** Restituisce solamente i dipendenti disattivati. */
    @Transactional(readOnly = true)
    public List<EmployeeDto> findInactive() {
        return employeeMapper.toDtoList(employeeRepository.findByActiveFalse());
    }

    /** Cerca un dipendente tramite ID. */
    @Transactional(readOnly = true)
    public EmployeeDto findById(Integer id) throws ServiceException {
        return employeeMapper.toDto(getEmployeeById(id));
    }

    /** Inserisce un nuovo dipendente. Born active per default. */
    @Transactional
    public EmployeeDto insert(EmployeeDto dto) throws ServiceException {
        String email = normalizeEmail(dto.getEmail());
        String telephoneNumber = normalizeText(dto.getTelephoneNumber());

        if (employeeRepository.existsByEmail(email)) {
            throw new ServiceException("Esiste già un dipendente con questa email", HttpStatus.CONFLICT);
        }

        if (employeeRepository.existsByTelephoneNumber(telephoneNumber)) {
            throw new ServiceException("Esiste già un dipendente con questo numero di telefono", HttpStatus.CONFLICT);
        }

        Employee employee = employeeMapper.toEntity(dto);
        employee.setFirstName(normalizeText(dto.getFirstName()));
        employee.setLastName(normalizeText(dto.getLastName()));
        employee.setEmail(email);
        employee.setTelephoneNumber(telephoneNumber);
        employee.setActive(true);

        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toDto(saved);
    }

    /** Aggiorna i dati anagrafici del dipendente. */
    @Transactional
    public EmployeeDto update(Integer id, EmployeeDto dto) throws ServiceException {
        Employee employee = getEmployeeById(id);

        String email = normalizeEmail(dto.getEmail());
        String telephoneNumber = normalizeText(dto.getTelephoneNumber());

        Employee sameEmail = employeeRepository.findByEmail(email).orElse(null);
        if (sameEmail != null && !sameEmail.getId().equals(id)) {
            throw new ServiceException("Esiste già un altro dipendente con questa email", HttpStatus.CONFLICT);
        }

        Employee samePhone = employeeRepository.findByTelephoneNumber(telephoneNumber).orElse(null);
        if (samePhone != null && !samePhone.getId().equals(id)) {
            throw new ServiceException("Esiste già un altro dipendente con questo numero di telefono", HttpStatus.CONFLICT);
        }

        employee.setFirstName(normalizeText(dto.getFirstName()));
        employee.setLastName(normalizeText(dto.getLastName()));
        employee.setEmail(email);
        employee.setTelephoneNumber(telephoneNumber);
        employee.setJobTitle(dto.getJobTitle());
        employee.setSpecializations(dto.getSpecializations());
        employee.setHireDate(dto.getHireDate());
        employee.setNotes(dto.getNotes());
        employee.setProfileImage(dto.getProfileImage());

        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toDto(saved);
    }

    /** Disattiva un dipendente (soft delete/disattivazione logica). */
    @Transactional
    public EmployeeDto deactivate(Integer id) throws ServiceException {
        Employee employee = getEmployeeById(id);

        if (!employee.isActive()) {
            return employeeMapper.toDto(employee);
        }

        employee.setActive(false);
        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    /** Riattiva un dipendente. */
    @Transactional
    public EmployeeDto activate(Integer id) throws ServiceException {
        Employee employee = getEmployeeById(id);

        if (employee.isActive()) {
            return employeeMapper.toDto(employee);
        }

        employee.setActive(true);
        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    /**
 * Elimina DEFINITIVAMENTE il dipendente dal database.
 */
@Transactional
public void delete(Integer id) throws ServiceException {
    Employee employee = getEmployeeById(id);

    // Se in futuro collegherai Employee ad altre entità (es. AppointmentItem o Consultation),
    // potrai inserire qui i controlli prima della cancellazione.

    employeeRepository.delete(employee);
}

    /** Restituisce la Entity Employee. */
    @Transactional(readOnly = true)
    public Employee getEmployeeById(Integer id) throws ServiceException {
        return employeeRepository.findById(id)
            .orElseThrow(() -> new ServiceException("Dipendente non trovato con id: " + id, HttpStatus.NOT_FOUND));
    }

    private String normalizeEmail(String value) {
        return value == null ? null : value.trim().toLowerCase();
    }

    private String normalizeText(String value) {
        return value == null ? null : value.trim();
    }
}