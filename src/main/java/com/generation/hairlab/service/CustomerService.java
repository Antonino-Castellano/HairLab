package com.generation.hairlab.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.hairlab.dto.CustomerDto;
import com.generation.hairlab.mapper.CustomerMapper;
import com.generation.hairlab.model.Customer;
import com.generation.hairlab.repository.AppointmentRepository;
import com.generation.hairlab.repository.ColorAnalysisRepository;
import com.generation.hairlab.repository.ConsultationRepository;
import com.generation.hairlab.repository.CustomerRepository;
import com.generation.hairlab.repository.FaceProfileRepository;
import com.generation.hairlab.repository.HairProfileRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato alla gestione dei clienti HairLab.
 *
 * Distingue chiaramente:
 * - disattivazione logica;
 * - riattivazione;
 * - eliminazione fisica definitiva.
 *
 * L'eliminazione definitiva viene bloccata quando il cliente possiede dati
 * storici, per evitare la perdita accidentale dello storico professionale.
 */
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AppointmentRepository appointmentRepository;
    private final ConsultationRepository consultationRepository;
    private final HairProfileRepository hairProfileRepository;
    private final FaceProfileRepository faceProfileRepository;
    private final ColorAnalysisRepository colorAnalysisRepository;
    private final CustomerMapper customerMapper;

    /**
     * Restituisce tutti i clienti.
     */
    @Transactional(readOnly = true)
    public List<CustomerDto> findAll() {
        return customerMapper.toDtoList(customerRepository.findAll());
    }

    /**
     * Restituisce solamente i clienti attivi.
     */
    @Transactional(readOnly = true)
    public List<CustomerDto> findActive() {
        return customerMapper.toDtoList(customerRepository.findByActiveTrue());
    }

    /**
     * Restituisce solamente i clienti disattivati.
     */
    @Transactional(readOnly = true)
    public List<CustomerDto> findInactive() {
        return customerMapper.toDtoList(customerRepository.findByActiveFalse());
    }

    /**
     * Cerca un cliente tramite ID.
     */
    @Transactional(readOnly = true)
    public CustomerDto findById(Integer id) throws ServiceException {
        return customerMapper.toDto(getCustomerById(id));
    }

    /**
     * Inserisce un nuovo cliente.
     */
    @Transactional
    public CustomerDto insert(CustomerDto dto) throws ServiceException {
        String normalizedEmail = normalizeEmail(dto.getEmail());

        if (customerRepository.existsByEmail(normalizedEmail)) {
            throw new ServiceException(
                "Esiste già un cliente con questa email", 
                HttpStatus.CONFLICT
            );
        }

        Customer customer = customerMapper.toEntity(dto);
        customer.setFirstName(normalizeText(dto.getFirstName()));
        customer.setLastName(normalizeText(dto.getLastName()));
        customer.setPhoneNumber(dto.getPhoneNumber().trim());
        customer.setEmail(normalizedEmail);
        customer.setActive(true); // Un nuovo cliente nasce sempre attivo

        LocalDateTime now = LocalDateTime.now();
        customer.setCreatedAt(now);
        customer.setUpdatedAt(now);

        Customer saved = customerRepository.save(customer);
        return customerMapper.toDto(saved);
    }

    /**
     * Aggiorna i dati anagrafici.
     * Lo stato active non viene modificato qui: attivazione e disattivazione hanno endpoint dedicati.
     */
    @Transactional
    public CustomerDto update(Integer id, CustomerDto dto) throws ServiceException {
        Customer customer = getCustomerById(id);
        String normalizedEmail = normalizeEmail(dto.getEmail());

        Customer sameEmail = customerRepository.findByEmail(normalizedEmail).orElse(null);

        if (sameEmail != null && !sameEmail.getId().equals(id)) {
            throw new ServiceException(
                "Esiste già un altro cliente con questa email", 
                HttpStatus.CONFLICT
            );
        }

        customer.setFirstName(normalizeText(dto.getFirstName()));
        customer.setLastName(normalizeText(dto.getLastName()));
        customer.setPhoneNumber(dto.getPhoneNumber().trim());
        customer.setEmail(normalizedEmail);
        customer.setDob(dto.getDob());
        customer.setProfileImage(dto.getProfileImage());
        customer.setUpdatedAt(LocalDateTime.now());

        return customerMapper.toDto(customerRepository.save(customer));
    }

    /**
     * Disattiva il cliente.
     * Il record rimane nel database e conserva tutto lo storico.
     */
    @Transactional
    public CustomerDto deactivate(Integer id) throws ServiceException {
        Customer customer = getCustomerById(id);

        if (!customer.isActive()) {
            return customerMapper.toDto(customer);
        }

        customer.setActive(false);
        customer.setUpdatedAt(LocalDateTime.now());

        return customerMapper.toDto(customerRepository.save(customer));
    }

    /**
     * Riattiva un cliente.
     */
    @Transactional
    public CustomerDto activate(Integer id) throws ServiceException {
        Customer customer = getCustomerById(id);

        if (customer.isActive()) {
            return customerMapper.toDto(customer);
        }

        customer.setActive(true);
        customer.setUpdatedAt(LocalDateTime.now());

        return customerMapper.toDto(customerRepository.save(customer));
    }

    /**
     * Elimina DEFINITIVAMENTE il cliente dal database.
     * L'eliminazione viene bloccata quando esistono dati storici collegati.
     */
    @Transactional
    public void delete(Integer id) throws ServiceException {
        Customer customer = getCustomerById(id);

        boolean hasAppointments = !appointmentRepository.findByCustomer_IdOrderByStartDateTimeDesc(id).isEmpty();
        boolean hasConsultations = !consultationRepository.findByCustomer_IdOrderByConsultationDateDesc(id).isEmpty();
        boolean hasHairProfile = hairProfileRepository.existsByCustomerId(id);
        boolean hasFaceProfile = faceProfileRepository.existsByCustomerId(id);
        boolean hasColorAnalysis = colorAnalysisRepository.existsByCustomerId(id);

        if (hasAppointments || hasConsultations || hasHairProfile || hasFaceProfile || hasColorAnalysis) {
            throw new ServiceException(
                "Il cliente possiede dati storici e non può essere eliminato definitivamente. Puoi disattivarlo per conservarne lo storico.",
                HttpStatus.CONFLICT
            );
        }

        // Cancellazione fisica
        customerRepository.delete(customer);
    }

    /**
     * Restituisce la Entity Customer.
     */
    @Transactional(readOnly = true)
    public Customer getCustomerById(Integer id) throws ServiceException {
        return customerRepository.findById(id)
            .orElseThrow(() -> new ServiceException(
                "Cliente non trovato con id: " + id, 
                HttpStatus.NOT_FOUND
            ));
    }

    /**
     * Normalizza l'email.
     */
    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }

    /**
     * Elimina gli spazi esterni.
     */
    private String normalizeText(String value) {
        return value.trim();
    }
}