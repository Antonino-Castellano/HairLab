package com.generation.hairlab.service;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.stereotype.Service;

import com.generation.hairlab.dto.CustomerDto;
import com.generation.hairlab.mapper.CustomerMapper;
import com.generation.hairlab.model.Customer;
import com.generation.hairlab.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato alla gestione dei clienti.
 *
 * Coordina Repository e Mapper e applica le regole necessarie prima
 * di leggere o modificare i dati.
 */
@Service
@RequiredArgsConstructor
public class CustomerService {

    /** Repository utilizzato per la persistenza dei clienti. */
    private CustomerRepository customerRepository;

    /** Mapper utilizzato per convertire Customer e CustomerDto. */
    private CustomerMapper customerMapper;

    /** Restituisce tutti i clienti presenti nel database. */
    public List<CustomerDto> findAll() {
        return customerMapper.toDtoList(customerRepository.findAll());
    }

    /**
     * Cerca un cliente tramite ID.
     *
     * @throws ServiceException se il cliente non esiste
     */
    public CustomerDto findById(Integer id) throws ServiceException {
        return customerMapper.toDto(getCustomerById(id));
    }

    /**
     * Inserisce un nuovo cliente.
     *
     * Verifica l'unicità dell'email e valorizza automaticamente
     * le date tecniche di creazione e aggiornamento.
     */
    public CustomerDto insert(CustomerDto dto) throws ServiceException {

        if (customerRepository.existsByEmail(dto.getEmail())) {
            throw new ServiceException(
                    "Esiste già un cliente con questa email");
        }

        Customer customer = customerMapper.toEntity(dto);

        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        return customerMapper.toDto(customerRepository.save(customer));
    }

    /**
     * Aggiorna un cliente esistente.
     *
     * L'Entity viene recuperata prima dal database per preservare
     * ID, relazioni e data originale di creazione.
     */
    public CustomerDto update(Integer id, CustomerDto dto)
            throws ServiceException {

        Customer customer = getCustomerById(id);

        Customer sameEmail = customerRepository
                .findByEmail(dto.getEmail())
                .orElse(null);

        if (sameEmail != null && !sameEmail.getId().equals(id)) {
            throw new ServiceException(
                    "Esiste già un altro cliente con questa email");
        }

        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setEmail(dto.getEmail());
        customer.setDob(dto.getDob());
        customer.setActive(dto.isActive());
        customer.setUpdatedAt(LocalDateTime.now());

        return customerMapper.toDto(customerRepository.save(customer));
    }

    /** Elimina un cliente tramite ID. */
    public void delete(Integer id) throws ServiceException {
        customerRepository.delete(getCustomerById(id));
    }

    /** Restituisce solamente i clienti attivi. */
    public List<CustomerDto> findActive() {
        return customerMapper.toDtoList(customerRepository.findByActiveTrue());
    }

    /**
     * Restituisce la vera Entity Customer.
     *
     * Può essere utilizzato da altri Service quando devono costruire
     * una relazione con Customer.
     */
    public Customer getCustomerById(Integer id) throws ServiceException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        "Cliente non trovato con id: " + id));
    }
}
