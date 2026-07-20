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
 * Service dedicato alla scheda tecnica dei capelli del cliente.
 *
 * Gestisce la relazione OneToOne Customer <-> HairProfile.
 */
@Service
@RequiredArgsConstructor
public class HairProfileService {

    /** Repository delle HairProfile. */
    private HairProfileRepository hairProfileRepository;

    /** Repository necessario per risolvere customerId. */
    private CustomerRepository customerRepository;

    /** Mapper HairProfile <-> HairProfileDto. */
    private HairProfileMapper hairProfileMapper;

    /** Restituisce tutte le schede capelli. */
    public List<HairProfileDto> findAll() {
        return hairProfileMapper.toDtoList(hairProfileRepository.findAll());
    }

    /** Cerca una scheda tramite ID. */
    public HairProfileDto findById(Integer id) throws ServiceException {
        return hairProfileMapper.toDto(getHairProfileById(id));
    }

    /** Cerca la scheda associata a uno specifico cliente. */
    public HairProfileDto findByCustomerId(Integer customerId) throws ServiceException {
        HairProfile profile = hairProfileRepository.findByCustomer_Id(customerId)
                .orElseThrow(() -> new ServiceException(
                        "HairProfile non trovata per il cliente: " + customerId));

        return hairProfileMapper.toDto(profile);
    }

    /**
     * Inserisce una nuova HairProfile.
     *
     * Verifica che il cliente esista e che non possieda già una scheda,
     * rispettando la relazione OneToOne.
     */
    public HairProfileDto insert(HairProfileDto dto) throws ServiceException {

        if (hairProfileRepository.existsByCustomer_Id(dto.getCustomerId())) {
            throw new ServiceException("Il cliente possiede già una HairProfile");
        }

        Customer customer = getCustomer(dto.getCustomerId());

        HairProfile profile = hairProfileMapper.toEntity(dto);
        profile.setCustomer(customer);

        return hairProfileMapper.toDto(hairProfileRepository.save(profile));
    }

    /** Aggiorna una HairProfile esistente. */
    public HairProfileDto update(Integer id, HairProfileDto dto)
            throws ServiceException {

        HairProfile profile = getHairProfileById(id);

        if (!profile.getCustomer().getId().equals(dto.getCustomerId())) {

            if (hairProfileRepository.existsByCustomer_Id(dto.getCustomerId())) {
                throw new ServiceException(
                        "Il nuovo cliente possiede già una HairProfile");
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
        profile.setScalpCondition(dto.getScalpCondition());
        profile.setChemicalHistory(dto.getChemicalHistory());
        profile.setSensitivities(dto.getSensitivities());
        profile.setContraindications(dto.getContraindications());
        profile.setNotes(dto.getNotes());

        return hairProfileMapper.toDto(hairProfileRepository.save(profile));
    }

    /** Elimina una HairProfile tramite ID. */
    public void delete(Integer id) throws ServiceException {
        hairProfileRepository.delete(getHairProfileById(id));
    }

    /** Restituisce la Entity HairProfile. */
    public HairProfile getHairProfileById(Integer id) throws ServiceException {
        return hairProfileRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        "HairProfile non trovata con id: " + id));
    }

    /** Recupera il Customer necessario alla relazione OneToOne. */
    private Customer getCustomer(Integer customerId) throws ServiceException {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ServiceException(
                        "Cliente non trovato con id: " + customerId));
    }
}
