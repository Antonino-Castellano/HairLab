package com.generation.hairlab.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.generation.hairlab.dto.ColorFormulaDto;
import com.generation.hairlab.mapper.ColorFormulaMapper;
import com.generation.hairlab.model.AppointmentItem;
import com.generation.hairlab.model.ColorFormula;
import com.generation.hairlab.model.Consultation;
import com.generation.hairlab.repository.AppointmentItemRepository;
import com.generation.hairlab.repository.ColorFormulaRepository;
import com.generation.hairlab.repository.ConsultationRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato alle formule colore.
 *
 * Risolve le relazioni con Consultation e AppointmentItem e gestisce
 * la data di creazione della formula.
 */
@Service
@RequiredArgsConstructor
public class ColorFormulaService {

    private ColorFormulaRepository colorFormulaRepository;

    private ConsultationRepository consultationRepository;

    private AppointmentItemRepository appointmentItemRepository;

    private ColorFormulaMapper colorFormulaMapper;

    /** Restituisce tutte le formule colore. */
    public List<ColorFormulaDto> findAll() {
        return colorFormulaMapper.toDtoList(colorFormulaRepository.findAll());
    }

    /** Cerca una formula tramite ID. */
    public ColorFormulaDto findById(Integer id) throws ServiceException {
        return colorFormulaMapper.toDto(getColorFormulaById(id));
    }

    /** Restituisce le formule associate a una consulenza. */
    public List<ColorFormulaDto> findByConsultation(Integer consultationId) {
        return colorFormulaMapper.toDtoList(
                colorFormulaRepository
                        .findByConsultation_IdOrderByCreatedAtDesc(consultationId));
    }

    /**
     * Inserisce una nuova formula colore.
     *
     * Verifica l'unicità del nome e risolve le relazioni tramite ID.
     */
    public ColorFormulaDto insert(ColorFormulaDto dto) throws ServiceException {

        if (colorFormulaRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new ServiceException("Esiste già una formula con questo nome");
        }

        Consultation consultation = getConsultation(dto.getConsultationId());
        AppointmentItem appointmentItem =
                getAppointmentItem(dto.getAppointmentItemId());

        ColorFormula formula = colorFormulaMapper.toEntity(dto);

        formula.setConsultation(consultation);
        formula.setAppointmentItem(appointmentItem);
        formula.setCreatedAt(LocalDateTime.now());

        return colorFormulaMapper.toDto(colorFormulaRepository.save(formula));
    }

    /** Aggiorna una formula colore esistente. */
    public ColorFormulaDto update(Integer id, ColorFormulaDto dto)
            throws ServiceException {

        ColorFormula formula = getColorFormulaById(id);

        ColorFormula sameName = colorFormulaRepository
                .findByNameIgnoreCase(dto.getName())
                .orElse(null);

        if (sameName != null && !sameName.getId().equals(id)) {
            throw new ServiceException("Esiste già un'altra formula con questo nome");
        }

        formula.setConsultation(getConsultation(dto.getConsultationId()));
        formula.setAppointmentItem(getAppointmentItem(dto.getAppointmentItemId()));
        formula.setName(dto.getName());
        formula.setTargetResult(dto.getTargetResult());
        formula.setVolumeDeveloper(dto.getVolumeDeveloper());
        formula.setMixingRatio(dto.getMixingRatio());
        formula.setStatus(dto.getStatus());
        formula.setNotes(dto.getNotes());

        return colorFormulaMapper.toDto(colorFormulaRepository.save(formula));
    }

    /** Elimina una formula colore tramite ID. */
    public void delete(Integer id) throws ServiceException {
        colorFormulaRepository.delete(getColorFormulaById(id));
    }

    /** Restituisce la Entity ColorFormula tramite ID. */
    public ColorFormula getColorFormulaById(Integer id) throws ServiceException {
        return colorFormulaRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        "Formula colore non trovata con id: " + id));
    }

    private Consultation getConsultation(Integer id) throws ServiceException {
        return consultationRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        "Consulenza non trovata con id: " + id));
    }

    private AppointmentItem getAppointmentItem(Integer id)
            throws ServiceException {

        return appointmentItemRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        "AppointmentItem non trovato con id: " + id));
    }
}
