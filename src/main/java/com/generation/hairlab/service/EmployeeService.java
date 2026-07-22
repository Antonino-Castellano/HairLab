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
 * Service dedicato alla gestione dei dipendenti del salone.
 *
 * Gestisce:
 * - lettura dei dipendenti;
 * - controllo di email e telefono univoci;
 * - normalizzazione dei dati principali;
 * - disattivazione logica per preservare lo storico;
 * - riattivazione esplicita.
 */
@Service
@RequiredArgsConstructor
public class EmployeeService {

    /** Repository per l'accesso ai dati Employee. */
    private final EmployeeRepository employeeRepository;

    /** Mapper Employee <-> EmployeeDto. */
    private final EmployeeMapper employeeMapper;

    /** Restituisce tutti i dipendenti, attivi e disattivati. */
    @Transactional(readOnly = true)
    public List<EmployeeDto> findAll() {
        return employeeMapper.toDtoList(
            employeeRepository.findAll()
        );
    }

    /** Restituisce solamente i dipendenti attivi. */
    @Transactional(readOnly = true)
    public List<EmployeeDto> findActive() {
        return employeeMapper.toDtoList(
            employeeRepository.findByActiveTrue()
        );
    }

    /** Cerca un dipendente tramite ID. */
    @Transactional(readOnly = true)
    public EmployeeDto findById(Integer id)
            throws ServiceException {
        return employeeMapper.toDto(
            getEmployeeById(id)
        );
    }

    /**
     * Inserisce un nuovo dipendente verificando
     * email e telefono univoci.
     */
    @Transactional
    public EmployeeDto insert(EmployeeDto dto)
            throws ServiceException {

        String email = normalizeEmail(dto.getEmail());
        String telephoneNumber = normalizeText(dto.getTelephoneNumber());

        if (employeeRepository.existsByEmail(email)) {
            throw new ServiceException(
                "Esiste già un dipendente con questa email",
                HttpStatus.CONFLICT
            );
        }

        if (employeeRepository.existsByTelephoneNumber(telephoneNumber)) {
            throw new ServiceException(
                "Esiste già un dipendente con questo numero di telefono",
                HttpStatus.CONFLICT
            );
        }

        Employee employee = employeeMapper.toEntity(dto);

        employee.setFirstName(normalizeText(dto.getFirstName()));
        employee.setLastName(normalizeText(dto.getLastName()));
        employee.setEmail(email);
        employee.setTelephoneNumber(telephoneNumber);

        Employee saved = employeeRepository.save(employee);

        return employeeMapper.toDto(saved);
    }

    /**
     * Aggiorna un dipendente esistente.
     *
     * L'Entity viene modificata in-place per preservare
     * ID e relazioni storiche.
     */
    @Transactional
    public EmployeeDto update(
            Integer id,
            EmployeeDto dto)
            throws ServiceException {

        Employee employee = getEmployeeById(id);

        String email = normalizeEmail(dto.getEmail());
        String telephoneNumber = normalizeText(dto.getTelephoneNumber());

        Employee sameEmail = employeeRepository
            .findByEmail(email)
            .orElse(null);

        if (sameEmail != null && !sameEmail.getId().equals(id)) {
            throw new ServiceException(
                "Esiste già un altro dipendente con questa email",
                HttpStatus.CONFLICT
            );
        }

        Employee samePhone = employeeRepository
            .findByTelephoneNumber(telephoneNumber)
            .orElse(null);

        if (samePhone != null && !samePhone.getId().equals(id)) {
            throw new ServiceException(
                "Esiste già un altro dipendente con questo numero di telefono",
                HttpStatus.CONFLICT
            );
        }

        employee.setFirstName(normalizeText(dto.getFirstName()));
        employee.setLastName(normalizeText(dto.getLastName()));
        employee.setEmail(email);
        employee.setTelephoneNumber(telephoneNumber);
        employee.setJobTitle(dto.getJobTitle());
        employee.setSpecializations(dto.getSpecializations());
        employee.setHireDate(dto.getHireDate());
        employee.setActive(dto.isActive());
        employee.setNotes(dto.getNotes());

        Employee saved = employeeRepository.save(employee);

        return employeeMapper.toDto(saved);
    }

    /**
     * Disattiva logicamente un dipendente.
     *
     * Non viene cancellato fisicamente perché può essere
     * collegato ad appuntamenti, servizi e consulenze storiche.
     */
    @Transactional
    public void delete(Integer id)
            throws ServiceException {

        Employee employee = getEmployeeById(id);

        if (!employee.isActive()) {
            return;
        }

        employee.setActive(false);
        employeeRepository.save(employee);
    }

    /** Riattiva un dipendente precedentemente disattivato. */
    @Transactional
    public EmployeeDto activate(Integer id)
            throws ServiceException {

        Employee employee = getEmployeeById(id);

        employee.setActive(true);

        return employeeMapper.toDto(
            employeeRepository.save(employee)
        );
    }

    /**
     * Restituisce la Entity Employee.
     *
     * Viene usato anche dagli altri Service per costruire
     * relazioni senza duplicare la logica di ricerca.
     */
    @Transactional(readOnly = true)
    public Employee getEmployeeById(Integer id)
            throws ServiceException {

        return employeeRepository.findById(id)
            .orElseThrow(
                () -> new ServiceException(
                    "Dipendente non trovato con id: " + id,
                    HttpStatus.NOT_FOUND
                )
            );
    }

    /** Normalizza email eliminando spazi e differenze di maiuscole. */
    private String normalizeEmail(String value) {
        return value == null
            ? null
            : value.trim().toLowerCase();
    }

    /** Elimina gli spazi esterni dai campi testuali. */
    private String normalizeText(String value) {
        return value == null
            ? null
            : value.trim();
    }
}
