package com.generation.hairlab.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.generation.hairlab.dto.HairProfileDto;
import com.generation.hairlab.mapper.HairProfileMapper;
import com.generation.hairlab.model.Customer;
import com.generation.hairlab.model.HairProfile;
import com.generation.hairlab.repository.CustomerRepository;
import com.generation.hairlab.repository.HairProfileRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato alla gestione della scheda tecnica
 * dei capelli di una cliente.
 *
 * Gestisce la relazione:
 *
 * Customer 1 <-> 1 HairProfile
 *
 * Ogni cliente può quindi possedere
 * una sola scheda capelli corrente.
 */
@Service
@RequiredArgsConstructor
public class HairProfileService {

    /**
     * Repository dedicato alle HairProfile.
     */
    private final HairProfileRepository hairProfileRepository;

    /**
     * Repository utilizzato per recuperare
     * il Customer collegato alla scheda.
     */
    private final CustomerRepository customerRepository;

    /**
     * Mapper utilizzato per convertire:
     *
     * HairProfile <-> HairProfileDto.
     */
    private final HairProfileMapper hairProfileMapper;

    /**
     * Restituisce tutte le schede capelli.
     *
     * @return lista delle HairProfile convertite in DTO
     */
    public List<HairProfileDto> findAll() {

        return hairProfileMapper.toDtoList(
            hairProfileRepository.findAll()
        );
    }

    /**
     * Cerca una HairProfile tramite ID.
     *
     * @param id identificativo della scheda
     * @return DTO della scheda trovata
     * @throws ServiceException se la scheda non esiste
     */
    public HairProfileDto findById(
            Integer id)
            throws ServiceException {

        return hairProfileMapper.toDto(
            getHairProfileById(id)
        );
    }

    /**
     * Cerca la HairProfile associata
     * a uno specifico cliente.
     *
     * @param customerId identificativo del cliente
     * @return scheda capelli del cliente
     * @throws ServiceException se il profilo non esiste
     */
    public HairProfileDto findByCustomerId(
            Integer customerId)
            throws ServiceException {

        HairProfile profile =
            hairProfileRepository
                .findByCustomerId(customerId)
                .orElseThrow(
                    () -> new ServiceException(
                        "HairProfile non trovata per il cliente: "
                        + customerId
                    )
                );

        return hairProfileMapper.toDto(
            profile
        );
    }

    /**
     * Inserisce una nuova HairProfile.
     *
     * Prima del salvataggio:
     *
     * 1. verifica che customerId sia valido;
     * 2. verifica che il cliente non possieda già una scheda;
     * 3. recupera la Entity Customer;
     * 4. collega Customer e HairProfile;
     * 5. salva la nuova scheda.
     *
     * @param dto dati della nuova scheda
     * @return HairProfile salvata
     * @throws ServiceException in caso di dati non validi
     */
    public HairProfileDto insert(
            HairProfileDto dto)
            throws ServiceException {

        if (dto.getCustomerId() == null) {

            throw new ServiceException(
                "È necessario specificare il cliente"
            );
        }

        if (
            hairProfileRepository
                .existsByCustomerId(
                    dto.getCustomerId()
                )
        ) {

            throw new ServiceException(
                "Il cliente possiede già una HairProfile"
            );
        }

        Customer customer =
            getCustomer(
                dto.getCustomerId()
            );

        HairProfile profile =
            hairProfileMapper.toEntity(
                dto
            );

        profile.setCustomer(
            customer
        );

        HairProfile saved =
            hairProfileRepository.save(
                profile
            );

        return hairProfileMapper.toDto(
            saved
        );
    }

    /**
     * Aggiorna una HairProfile esistente.
     *
     * Le relazioni vengono risolte dal Service
     * mentre i valori tecnici vengono copiati
     * dal DTO alla Entity già esistente.
     *
     * @param id ID della HairProfile
     * @param dto nuovi dati
     * @return HairProfile aggiornata
     * @throws ServiceException se i dati non sono validi
     */
    public HairProfileDto update(
            Integer id,
            HairProfileDto dto)
            throws ServiceException {

        HairProfile profile =
            getHairProfileById(id);

        if (dto.getCustomerId() == null) {

            throw new ServiceException(
                "È necessario specificare il cliente"
            );
        }

        /*
         * Se viene cambiato il cliente proprietario
         * dobbiamo verificare che il nuovo cliente
         * non possieda già un'altra HairProfile.
         */
        if (
            !profile
                .getCustomer()
                .getId()
                .equals(
                    dto.getCustomerId()
                )
        ) {

            if (
                hairProfileRepository
                    .existsByCustomerId(
                        dto.getCustomerId()
                    )
            ) {

                throw new ServiceException(
                    "Il nuovo cliente possiede già una HairProfile"
                );
            }

            profile.setCustomer(
                getCustomer(
                    dto.getCustomerId()
                )
            );
        }

        /*
         * ========================================================
         * DATI CROMATICI
         * ========================================================
         */

        profile.setNaturalTone(
            dto.getNaturalTone()
        );

        profile.setCurrentTone(
            dto.getCurrentTone()
        );

        profile.setReflection(
            dto.getReflection()
        );

        /*
         * ========================================================
         * STRUTTURA CAPILLARE
         * ========================================================
         */

        profile.setHairType(
            dto.getHairType()
        );

        profile.setTexture(
            dto.getTexture()
        );

        profile.setPorosity(
            dto.getPorosity()
        );

        profile.setDensity(
            dto.getDensity()
        );

        /*
         * IMPORTANTE:
         *
         * Questo campo mancava nella versione precedente.
         *
         * Senza questa riga hairCondition non cambiava
         * durante una modifica della scheda.
         */
        profile.setHairCondition(
            dto.getHairCondition()
        );

        /*
         * ========================================================
         * ANALISI TECNICA
         * ========================================================
         */

        profile.setScalpCondition(
            dto.getScalpCondition()
        );

        profile.setChemicalHistory(
            dto.getChemicalHistory()
        );

        profile.setSensitivities(
            dto.getSensitivities()
        );

        profile.setContraindications(
            dto.getContraindications()
        );

        profile.setNotes(
            dto.getNotes()
        );

        HairProfile saved =
            hairProfileRepository.save(
                profile
            );

        return hairProfileMapper.toDto(
            saved
        );
    }

    /**
     * Elimina una HairProfile tramite ID.
     *
     * Per ora manteniamo questa funzione.
     * In futuro valuteremo se preferire
     * una gestione dello storico senza eliminazione fisica.
     *
     * @param id ID della scheda
     * @throws ServiceException se la scheda non esiste
     */
    public void delete(
            Integer id)
            throws ServiceException {

        HairProfile profile =
            getHairProfileById(id);

        hairProfileRepository.delete(
            profile
        );
    }

    /**
     * Recupera direttamente la Entity HairProfile.
     *
     * Metodo riutilizzato internamente dal Service.
     */
    public HairProfile getHairProfileById(
            Integer id)
            throws ServiceException {

        return hairProfileRepository
            .findById(id)
            .orElseThrow(
                () -> new ServiceException(
                    "HairProfile non trovata con id: "
                    + id
                )
            );
    }

    /**
     * Recupera il Customer collegato.
     */
    private Customer getCustomer(
            Integer customerId)
            throws ServiceException {

        return customerRepository
            .findById(customerId)
            .orElseThrow(
                () -> new ServiceException(
                    "Cliente non trovato con id: "
                    + customerId
                )
            );
    }
}