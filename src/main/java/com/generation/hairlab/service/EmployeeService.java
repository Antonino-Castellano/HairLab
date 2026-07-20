package com.generation.hairlab.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.generation.hairlab.dto.EmployeeDto;
import com.generation.hairlab.mapper.EmployeeMapper;
import com.generation.hairlab.model.Employee;
import com.generation.hairlab.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato alla gestione dei dipendenti del salone.
 */
@Service
@RequiredArgsConstructor
public class EmployeeService {

    /** Repository per l'accesso ai dati Employee. */
    private EmployeeRepository employeeRepository;

    /** Mapper Employee <-> EmployeeDto. */
    private EmployeeMapper employeeMapper;

    /** Restituisce tutti i dipendenti. */
    public List<EmployeeDto> findAll() {
        return employeeMapper.toDtoList(employeeRepository.findAll());
    }

    /** Restituisce solamente i dipendenti attivi. */
    public List<EmployeeDto> findActive() {
        return employeeMapper.toDtoList(employeeRepository.findByActiveTrue());
    }

    /**
     * Cerca un dipendente tramite ID.
     *
     * @throws ServiceException se il dipendente non esiste
     */
    public EmployeeDto findById(Integer id) throws ServiceException {
        return employeeMapper.toDto(getEmployeeById(id));
    }

    /**
     * Inserisce un nuovo dipendente verificando email e telefono univoci.
     */
    public EmployeeDto insert(EmployeeDto dto) throws ServiceException {

        if (employeeRepository.existsByEmail(dto.getEmail())) {
            throw new ServiceException("Esiste già un dipendente con questa email");
        }

        if (employeeRepository.existsByTelephoneNumber(dto.getTelephoneNumber())) {
            throw new ServiceException("Esiste già un dipendente con questo numero di telefono");
        }

        Employee employee = employeeMapper.toEntity(dto);

        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    /**
     * Aggiorna un dipendente esistente.
     *
     * Le proprietà vengono copiate esplicitamente per preservare l'ID
     * della Entity già presente nel database.
     */
    public EmployeeDto update(Integer id, EmployeeDto dto) throws ServiceException {

        Employee employee = getEmployeeById(id);

        Employee sameEmail = employeeRepository.findByEmail(dto.getEmail()).orElse(null);

        if (sameEmail != null && !sameEmail.getId().equals(id)) {
            throw new ServiceException("Esiste già un altro dipendente con questa email");
        }

        Employee samePhone = employeeRepository
                .findByTelephoneNumber(dto.getTelephoneNumber())
                .orElse(null);

        if (samePhone != null && !samePhone.getId().equals(id)) {
            throw new ServiceException(
                    "Esiste già un altro dipendente con questo numero di telefono");
        }

        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setTelephoneNumber(dto.getTelephoneNumber());
        employee.setJobTitle(dto.getJobTitle());
        employee.setSpecializations(dto.getSpecializations());
        employee.setHireDate(dto.getHireDate());
        employee.setActive(dto.isActive());
        employee.setNotes(dto.getNotes());

        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    /** Elimina un dipendente esistente. */
    public void delete(Integer id) throws ServiceException {
        employeeRepository.delete(getEmployeeById(id));
    }

    /**
     * Restituisce la Entity Employee.
     *
     * Viene esposto per permettere ad altri Service di costruire
     * correttamente le relazioni senza duplicare la ricerca.
     */
    public Employee getEmployeeById(Integer id) throws ServiceException {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        "Dipendente non trovato con id: " + id));
    }
}
