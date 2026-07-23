package com.generation.hairlab.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.hairlab.dto.ColorFormulaDto;
import com.generation.hairlab.enums.ColorFormulaStatus;
import com.generation.hairlab.enums.MixingRatio;
import com.generation.hairlab.mapper.ColorFormulaMapper;
import com.generation.hairlab.model.Appointment;
import com.generation.hairlab.model.AppointmentItem;
import com.generation.hairlab.model.ColorFormula;
import com.generation.hairlab.model.Consultation;
import com.generation.hairlab.model.Customer;
import com.generation.hairlab.repository.AppointmentItemRepository;
import com.generation.hairlab.repository.ColorFormulaRepository;
import com.generation.hairlab.repository.ConsultationRepository;
import com.generation.hairlab.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service delle formule colore.
 *
 * BLOCCO COLOR LAB 1
 *
 * Regola fondamentale:
 *
 * ogni nuova ColorFormula appartiene direttamente
 * a uno specifico Customer.
 *
 * Consultation e AppointmentItem sono opzionali,
 * ma quando vengono valorizzati devono appartenere
 * allo stesso cliente della formula.
 */
@Service
@RequiredArgsConstructor
public class ColorFormulaService {

    private final ColorFormulaRepository
        colorFormulaRepository;

    private final CustomerRepository
        customerRepository;

    private final ConsultationRepository
        consultationRepository;

    private final AppointmentItemRepository
        appointmentItemRepository;

    private final ColorFormulaMapper
        colorFormulaMapper;

    @Transactional(readOnly = true)
    public List<ColorFormulaDto> findAll() {

        return colorFormulaMapper.toDtoList(
            colorFormulaRepository.findAll()
        );
    }

    @Transactional(readOnly = true)
    public ColorFormulaDto findById(
            Integer id)
            throws ServiceException {

        return colorFormulaMapper.toDto(
            getColorFormulaById(
                id
            )
        );
    }

    /**
     * Storico completo delle formule di una cliente,
     * dalla più recente.
     */
    @Transactional(readOnly = true)
    public List<ColorFormulaDto> findByCustomer(
            Integer customerId)
            throws ServiceException {

        ensureCustomerExists(
            customerId
        );

        return colorFormulaMapper.toDtoList(
            colorFormulaRepository
                .findByCustomer_IdOrderByCreatedAtDesc(
                    customerId
                )
        );
    }

    /**
     * Ultima formula realmente utilizzata
     * sulla cliente.
     */
    @Transactional(readOnly = true)
    public ColorFormulaDto findLatestUsedByCustomer(
            Integer customerId)
            throws ServiceException {

        ensureCustomerExists(
            customerId
        );

        ColorFormula formula =
            colorFormulaRepository
                .findFirstByCustomer_IdAndStatusOrderByCreatedAtDesc(
                    customerId,
                    ColorFormulaStatus.USED
                )
                .orElseThrow(
                    () ->
                        new ServiceException(
                            "Nessuna formula utilizzata trovata per il cliente: "
                                + customerId,
                            HttpStatus.NOT_FOUND
                        )
                );

        return colorFormulaMapper.toDto(
            formula
        );
    }

    @Transactional(readOnly = true)
    public List<ColorFormulaDto> findByConsultation(
            Integer consultationId) {

        return colorFormulaMapper.toDtoList(
            colorFormulaRepository
                .findByConsultation_IdOrderByCreatedAtDesc(
                    consultationId
                )
        );
    }

    /**
     * Inserisce una nuova formula.
     */
    @Transactional
    public ColorFormulaDto insert(
            ColorFormulaDto dto)
            throws ServiceException {

        validateMixingRatio(
            dto
        );

        validateUniqueName(
            null,
            dto.getName()
        );

        Customer customer =
            getActiveCustomer(
                dto.getCustomerId()
            );

        Consultation consultation =
            getOptionalConsultation(
                dto.getConsultationId()
            );

        AppointmentItem appointmentItem =
            getOptionalAppointmentItem(
                dto.getAppointmentItemId()
            );

        validateOwnership(
            customer,
            consultation,
            appointmentItem
        );

        ColorFormula formula =
            colorFormulaMapper.toEntity(
                dto
            );

        formula.setCustomer(
            customer
        );

        formula.setConsultation(
            consultation
        );

        formula.setAppointmentItem(
            appointmentItem
        );

        formula.setName(
            normalizeRequiredText(
                dto.getName()
            )
        );

        formula.setTargetResult(
            normalizeRequiredText(
                dto.getTargetResult()
            )
        );

        formula.setNotes(
            normalizeNullableText(
                dto.getNotes()
            )
        );

        formula.setCustomDeveloperRatio(
            normalizeCustomRatio(
                dto
            )
        );

        formula.setCreatedAt(
            LocalDateTime.now()
        );

        return colorFormulaMapper.toDto(
            colorFormulaRepository.save(
                formula
            )
        );
    }

    /**
     * Aggiorna una formula esistente.
     *
     * Il collegamento al Customer rimane esplicito
     * e viene nuovamente validato.
     */
    @Transactional
    public ColorFormulaDto update(
            Integer id,
            ColorFormulaDto dto)
            throws ServiceException {

        ColorFormula formula =
            getColorFormulaById(
                id
            );

        validateMixingRatio(
            dto
        );

        validateUniqueName(
            id,
            dto.getName()
        );

        Customer customer =
            getActiveCustomer(
                dto.getCustomerId()
            );

        Consultation consultation =
            getOptionalConsultation(
                dto.getConsultationId()
            );

        AppointmentItem appointmentItem =
            getOptionalAppointmentItem(
                dto.getAppointmentItemId()
            );

        validateOwnership(
            customer,
            consultation,
            appointmentItem
        );

        formula.setCustomer(
            customer
        );

        formula.setConsultation(
            consultation
        );

        formula.setAppointmentItem(
            appointmentItem
        );

        formula.setName(
            normalizeRequiredText(
                dto.getName()
            )
        );

        formula.setTargetResult(
            normalizeRequiredText(
                dto.getTargetResult()
            )
        );

        formula.setTargetToneLevel(
            dto.getTargetToneLevel()
        );

        formula.setTargetPrimaryReflection(
            dto.getTargetPrimaryReflection()
        );

        formula.setTargetSecondaryReflection(
            dto.getTargetSecondaryReflection()
        );

        formula.setApplicationType(
            dto.getApplicationType()
        );

        formula.setVolumeDeveloper(
            dto.getVolumeDeveloper()
        );

        formula.setMixingRatio(
            dto.getMixingRatio()
        );

        formula.setCustomDeveloperRatio(
            normalizeCustomRatio(
                dto
            )
        );

        formula.setStatus(
            dto.getStatus()
        );

        formula.setNotes(
            normalizeNullableText(
                dto.getNotes()
            )
        );

        return colorFormulaMapper.toDto(
            colorFormulaRepository.save(
                formula
            )
        );
    }

    /**
     * Eliminazione fisica.
     *
     * La gestione dello storico e l'eventuale archiviazione
     * verranno raffinati nel blocco dedicato alla UI formule.
     */
    @Transactional
    public void delete(
            Integer id)
            throws ServiceException {

        colorFormulaRepository.delete(
            getColorFormulaById(
                id
            )
        );
    }

    @Transactional(readOnly = true)
    public ColorFormula getColorFormulaById(
            Integer id)
            throws ServiceException {

        return colorFormulaRepository
            .findById(
                id
            )
            .orElseThrow(
                () ->
                    new ServiceException(
                        "Formula colore non trovata con id: "
                            + id,
                        HttpStatus.NOT_FOUND
                    )
            );
    }

    private void validateUniqueName(
            Integer currentId,
            String name)
            throws ServiceException {

        ColorFormula sameName =
            colorFormulaRepository
                .findByNameIgnoreCase(
                    normalizeRequiredText(
                        name
                    )
                )
                .orElse(
                    null
                );

        if (
            sameName != null
            &&
            (
                currentId == null
                ||
                !sameName.getId()
                    .equals(
                        currentId
                    )
            )
        ) {

            throw new ServiceException(
                "Esiste già una formula con questo nome",
                HttpStatus.CONFLICT
            );
        }
    }

    private Customer getActiveCustomer(
            Integer id)
            throws ServiceException {

        Customer customer =
            customerRepository
                .findById(
                    id
                )
                .orElseThrow(
                    () ->
                        new ServiceException(
                            "Cliente non trovato con id: "
                                + id,
                            HttpStatus.NOT_FOUND
                        )
                );

        if (
            !customer.isActive()
        ) {

            throw new ServiceException(
                "Non è possibile creare o modificare una formula "
                    + "per un cliente disattivato",
                HttpStatus.CONFLICT
            );
        }

        return customer;
    }

    private void ensureCustomerExists(
            Integer id)
            throws ServiceException {

        if (
            id == null
            ||
            !customerRepository.existsById(
                id
            )
        ) {

            throw new ServiceException(
                "Cliente non trovato con id: "
                    + id,
                HttpStatus.NOT_FOUND
            );
        }
    }

    /**
     * Consultation è opzionale.
     */
    private Consultation getOptionalConsultation(
            Integer id)
            throws ServiceException {

        if (
            id == null
        ) {

            return null;
        }

        return consultationRepository
            .findById(
                id
            )
            .orElseThrow(
                () ->
                    new ServiceException(
                        "Consulenza non trovata con id: "
                            + id,
                        HttpStatus.NOT_FOUND
                    )
            );
    }

    /**
     * AppointmentItem è opzionale.
     */
    private AppointmentItem getOptionalAppointmentItem(
            Integer id)
            throws ServiceException {

        if (
            id == null
        ) {

            return null;
        }

        return appointmentItemRepository
            .findById(
                id
            )
            .orElseThrow(
                () ->
                    new ServiceException(
                        "AppointmentItem non trovato con id: "
                            + id,
                        HttpStatus.NOT_FOUND
                    )
            );
    }

    /**
     * Evita collegamenti incoerenti:
     *
     * formula di Maria
     * ->
     * consulenza o appuntamento di Giulia.
     */
    private void validateOwnership(
            Customer customer,
            Consultation consultation,
            AppointmentItem appointmentItem)
            throws ServiceException {

        if (
            consultation != null
            &&
            consultation.getCustomer() != null
            &&
            !customer.getId()
                .equals(
                    consultation
                        .getCustomer()
                        .getId()
                )
        ) {

            throw new ServiceException(
                "La consulenza selezionata appartiene a un altro cliente",
                HttpStatus.CONFLICT
            );
        }

        if (
            appointmentItem != null
        ) {

            Appointment appointment =
                appointmentItem
                    .getAppointment();

            if (
                appointment == null
                ||
                appointment.getCustomer() == null
                ||
                !customer.getId()
                    .equals(
                        appointment
                            .getCustomer()
                            .getId()
                    )
            ) {

                throw new ServiceException(
                    "L'AppointmentItem selezionato appartiene "
                        + "a un altro cliente",
                    HttpStatus.CONFLICT
                );
            }
        }
    }

    /**
     * CUSTOM richiede un moltiplicatore maggiore di zero.
     *
     * Per i rapporti standard il valore custom viene ignorato.
     */
    private void validateMixingRatio(
            ColorFormulaDto dto)
            throws ServiceException {

        if (
            dto.getMixingRatio() ==
                MixingRatio.CUSTOM
        ) {

            BigDecimal ratio =
                dto.getCustomDeveloperRatio();

            if (
                ratio == null
                ||
                ratio.compareTo(
                    BigDecimal.ZERO
                ) <= 0
            ) {

                throw new ServiceException(
                    "Inserisci un rapporto developer personalizzato maggiore di zero",
                    HttpStatus.BAD_REQUEST
                );
            }
        }
    }

    private BigDecimal normalizeCustomRatio(
            ColorFormulaDto dto) {

        return dto.getMixingRatio() ==
            MixingRatio.CUSTOM

            ? dto.getCustomDeveloperRatio()

            : null;
    }

    private String normalizeRequiredText(
            String value) {

        return value == null
            ? null
            : value.trim();
    }

    private String normalizeNullableText(
            String value) {

        if (
            value == null
        ) {

            return null;
        }

        String normalized =
            value.trim();

        return normalized.isEmpty()
            ? null
            : normalized;
    }
}
