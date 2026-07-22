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
 * Coordina Repository e Mapper e applica le regole
 * necessarie prima di leggere o modificare i dati.
 */
@Service
@RequiredArgsConstructor
public class CustomerService {

    /**
     * Repository utilizzato per leggere,
     * salvare, modificare ed eliminare i clienti.
     */
    private final CustomerRepository customerRepository;

    /**
     * Mapper utilizzato per convertire:
     *
     * Customer -> CustomerDto
     *
     * CustomerDto -> Customer
     */
    private final CustomerMapper customerMapper;

    /**
     * Restituisce tutti i clienti presenti nel database.
     *
     * Le Entity vengono convertite in DTO
     * prima di essere restituite al Controller.
     */
    public List<CustomerDto> findAll() {
        return customerMapper.toDtoList(
            customerRepository.findAll()
        );
    }

    /**
     * Cerca un cliente tramite il suo ID.
     *
     * @param id identificativo del cliente
     * @return DTO del cliente trovato
     * @throws ServiceException se il cliente non esiste
     */
    public CustomerDto findById(Integer id)
            throws ServiceException {

        return customerMapper.toDto(
            getCustomerById(id)
        );
    }

    /**
     * Inserisce un nuovo cliente.
     *
     * Prima verifica che non esista già
     * un cliente con la stessa email.
     *
     * Il Mapper converte automaticamente anche
     * profileImage perché il campo possiede
     * lo stesso nome e tipo sia nella Entity
     * sia nel DTO.
     *
     * @param dto dati del nuovo cliente
     * @return cliente salvato convertito in DTO
     * @throws ServiceException se l'email è già utilizzata
     */
    public CustomerDto insert(CustomerDto dto)
            throws ServiceException {

        if (
            customerRepository.existsByEmail(
                dto.getEmail()
            )
        ) {
            throw new ServiceException(
                "Esiste già un cliente con questa email"
            );
        }

        /**
         * Il Mapper crea una nuova Entity.
         *
         * Verranno copiati automaticamente:
         *
         * firstName
         * lastName
         * phoneNumber
         * email
         * dob
         * active
         * profileImage
         *
         * Non vengono invece copiati:
         *
         * id
         * appointments
         * createdAt
         * updatedAt
         *
         * perché nel Mapper sono ignorati.
         */
        Customer customer =
            customerMapper.toEntity(dto);

        /**
         * Le date tecniche vengono gestite
         * direttamente dal backend.
         */
        customer.setCreatedAt(
            LocalDateTime.now()
        );

        customer.setUpdatedAt(
            LocalDateTime.now()
        );

        /**
         * Salviamo il cliente e convertiamo
         * nuovamente il risultato in DTO.
         */
        Customer savedCustomer =
            customerRepository.save(customer);

        return customerMapper.toDto(
            savedCustomer
        );
    }

    /**
     * Aggiorna un cliente esistente.
     *
     * Prima recuperiamo la vera Entity dal database.
     *
     * Questo permette di conservare:
     *
     * - ID;
     * - createdAt;
     * - relazioni;
     * - appuntamenti già associati.
     *
     * Successivamente aggiorniamo solamente
     * i campi modificabili.
     *
     * @param id identificativo del cliente
     * @param dto nuovi dati del cliente
     * @return cliente aggiornato
     * @throws ServiceException se il cliente non esiste
     *                          o l'email appartiene
     *                          a un altro cliente
     */
    public CustomerDto update(
            Integer id,
            CustomerDto dto)
            throws ServiceException {

        /**
         * Recuperiamo la Entity esistente.
         */
        Customer customer =
            getCustomerById(id);

        /**
         * Cerchiamo un eventuale cliente
         * con la stessa email.
         */
        Customer sameEmail =
            customerRepository
                .findByEmail(dto.getEmail())
                .orElse(null);

        /**
         * Se troviamo la stessa email,
         * controlliamo che appartenga
         * allo stesso cliente.
         *
         * In caso contrario impediamo
         * la modifica.
         */
        if (
            sameEmail != null &&
            !sameEmail.getId().equals(id)
        ) {
            throw new ServiceException(
                "Esiste già un altro cliente con questa email"
            );
        }

        /**
         * Aggiorniamo i dati anagrafici.
         */
        customer.setFirstName(
            dto.getFirstName()
        );

        customer.setLastName(
            dto.getLastName()
        );

        customer.setPhoneNumber(
            dto.getPhoneNumber()
        );

        customer.setEmail(
            dto.getEmail()
        );

        customer.setDob(
            dto.getDob()
        );

        customer.setActive(
            dto.isActive()
        );

        /**
         * Aggiorniamo anche la foto profilo.
         *
         * profileImage contiene la stringa Base64
         * generata dal frontend.
         *
         * Se il frontend invia null,
         * la foto verrà rimossa.
         */
        customer.setProfileImage(
            dto.getProfileImage()
        );

        /**
         * Aggiorniamo solamente updatedAt.
         *
         * createdAt rimane invariato perché
         * rappresenta la data originale
         * di creazione del cliente.
         */
        customer.setUpdatedAt(
            LocalDateTime.now()
        );

        /**
         * Salviamo le modifiche.
         */
        Customer updatedCustomer =
            customerRepository.save(customer);

        /**
         * Restituiamo il cliente aggiornato
         * convertito in DTO.
         */
        return customerMapper.toDto(
            updatedCustomer
        );
    }

    /**
     * Elimina un cliente tramite ID.
     *
     * Prima recuperiamo il cliente per verificare
     * che esista realmente.
     *
     * @param id identificativo del cliente
     * @throws ServiceException se il cliente non esiste
     */
    public void delete(Integer id)
            throws ServiceException {

        customerRepository.delete(
            getCustomerById(id)
        );
    }

    /**
     * Restituisce solamente i clienti
     * che hanno active = true.
     *
     * @return lista dei clienti attivi
     */
    public List<CustomerDto> findActive() {

        return customerMapper.toDtoList(
            customerRepository
                .findByActiveTrue()
        );
    }

    /**
     * Restituisce la vera Entity Customer.
     *
     * Questo metodo è utile anche agli altri Service
     * quando devono creare una relazione con Customer.
     *
     * Esempio:
     *
     * HairProfileService
     * AppointmentService
     * ConsultationService
     *
     * possono recuperare la Entity reale tramite ID.
     *
     * @param id identificativo del cliente
     * @return Entity Customer
     * @throws ServiceException se il cliente non esiste
     */
    public Customer getCustomerById(Integer id)
            throws ServiceException {

        return customerRepository
            .findById(id)
            .orElseThrow(
                () -> new ServiceException(
                    "Cliente non trovato con id: " + id
                )
            );
    }
}