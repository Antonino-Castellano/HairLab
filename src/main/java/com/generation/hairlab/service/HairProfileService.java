package com.generation.hairlab.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import com.generation.hairlab.dto.HairProfileDto;
import com.generation.hairlab.mapper.HairProfileMapper;
import com.generation.hairlab.model.Customer;
import com.generation.hairlab.model.HairProfile;
import com.generation.hairlab.repository.CustomerRepository;
import com.generation.hairlab.repository.HairProfileRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato alla scheda tecnica dei capelli del cliente.
 *
 * Gestisce la relazione OneToOne Customer <-> HairProfile.
 */
@Service
@RequiredArgsConstructor
public class HairProfileService {

    /** Repository delle HairProfile. */
    private final HairProfileRepository hairProfileRepository;

    /** Repository necessario per risolvere customerId. */
    private final CustomerRepository customerRepository;

    /** Mapper HairProfile <-> HairProfileDto. */
    private final HairProfileMapper hairProfileMapper;

    /** Restituisce tutte le schede capelli. */
    @Transactional(readOnly = true)
    public List<HairProfileDto> findAll() {
        return hairProfileMapper.toDtoList(hairProfileRepository.findAll());
    }

    /** Cerca una scheda tramite ID. */
    @Transactional(readOnly = true)
    public HairProfileDto findById(Integer id) throws ServiceException {
        return hairProfileMapper.toDto(getHairProfileById(id));
    }

    /** Cerca la scheda associata a uno specifico cliente. */
    @Transactional(readOnly = true)
    public HairProfileDto findByCustomerId(Integer customerId) throws ServiceException {
        HairProfile profile = hairProfileRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ServiceException(
                        "HairProfile non trovata per il cliente: " + customerId,
                        HttpStatus.NOT_FOUND));

        return hairProfileMapper.toDto(profile);
    }

    /**
     * Inserisce una nuova HairProfile.
     *
     * Verifica che il cliente esista e che non possieda già una scheda,
     * rispettando la relazione OneToOne.
     */
    @Transactional
    public HairProfileDto insert(HairProfileDto dto) throws ServiceException {

        if (hairProfileRepository.existsByCustomerId(dto.getCustomerId())) {
            throw new ServiceException(
                    "Il cliente possiede già una HairProfile",
                    HttpStatus.CONFLICT);
        }

        Customer customer = getCustomer(dto.getCustomerId());

        HairProfile profile = hairProfileMapper.toEntity(dto);
        profile.setCustomer(customer);

        return hairProfileMapper.toDto(hairProfileRepository.save(profile));
    }

    /** Aggiorna una HairProfile esistente. */
    @Transactional
    public HairProfileDto update(Integer id, HairProfileDto dto)
            throws ServiceException {

        HairProfile profile = getHairProfileById(id);

        if (!profile.getCustomer().getId().equals(dto.getCustomerId())) {

            if (hairProfileRepository.existsByCustomerId(dto.getCustomerId())) {
                throw new ServiceException(
                        "Il nuovo cliente possiede già una HairProfile",
                        HttpStatus.CONFLICT);
            }

            profile.setCustomer(getCustomer(dto.getCustomerId()));
        }

        profile.setNaturalTone(dto.getNaturalTone());
        profile.setCurrentTone(dto.getCurrentTone());
        profile.setReflection(dto.getReflection());
        profile.setHairType(dto.getHairType());
        profile.setTexture(dto.getTexture());
        profile.setPorosity(dto.getPorosity());
        profile.setDensity(dto.getDensity());
        profile.setHairCondition(dto.getHairCondition());
        profile.setScalpCondition(dto.getScalpCondition());
        profile.setChemicalHistory(dto.getChemicalHistory());
        profile.setSensitivities(dto.getSensitivities());
        profile.setContraindications(dto.getContraindications());
        profile.setNotes(dto.getNotes());

        return hairProfileMapper.toDto(hairProfileRepository.save(profile));
    }

    /** Elimina una HairProfile tramite ID. */
    @Transactional
    public void delete(Integer id) throws ServiceException {
        hairProfileRepository.delete(getHairProfileById(id));
    }

    /** Restituisce la Entity HairProfile. */
    @Transactional(readOnly = true)
    public HairProfile getHairProfileById(Integer id) throws ServiceException {
        return hairProfileRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        "HairProfile non trovata con id: " + id,
                        HttpStatus.NOT_FOUND));
    }

    /** Recupera il Customer necessario alla relazione OneToOne. */
    private Customer getCustomer(Integer customerId) throws ServiceException {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ServiceException(
                        "Cliente non trovato con id: " + customerId,
                        HttpStatus.NOT_FOUND));

        if (!customer.isActive()) {
            throw new ServiceException(
                    "Il cliente selezionato non è attivo",
                    HttpStatus.CONFLICT);
        }

        return customer;
    }
}
