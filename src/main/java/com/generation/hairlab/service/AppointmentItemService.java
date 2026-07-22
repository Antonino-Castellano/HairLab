package com.generation.hairlab.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import com.generation.hairlab.dto.AppointmentItemDto;
import com.generation.hairlab.mapper.AppointmentItemMapper;
import com.generation.hairlab.model.Appointment;
import com.generation.hairlab.model.AppointmentItem;
import com.generation.hairlab.model.Employee;
import com.generation.hairlab.model.SalonProduct;
import com.generation.hairlab.repository.AppointmentItemRepository;
import com.generation.hairlab.repository.AppointmentRepository;
import com.generation.hairlab.repository.EmployeeRepository;
import com.generation.hairlab.repository.SalonProductRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service dedicato ai singoli servizi contenuti negli appuntamenti.
 *
 * È il punto in cui vengono risolte le tre relazioni del DTO:
 * appointmentId, salonProductId ed employeeId.
 *
 * Contiene inoltre la regola di controllo delle sovrapposizioni
 * degli impegni di un operatore.
 */
@Service
@RequiredArgsConstructor
public class AppointmentItemService {
    
    private final AppointmentItemRepository appointmentItemRepository;
    
    private final AppointmentRepository appointmentRepository;
    
    private final SalonProductRepository salonProductRepository;

    private final EmployeeRepository employeeRepository;

    private final AppointmentItemMapper appointmentItemMapper;

    /** Restituisce tutti gli item. */
    @Transactional(readOnly = true)
    public List<AppointmentItemDto> findAll() {
        return appointmentItemMapper.toDtoList(appointmentItemRepository.findAll());
    }

    /** Cerca un item tramite ID. */
    @Transactional(readOnly = true)
    public AppointmentItemDto findById(Integer id) throws ServiceException {
        return appointmentItemMapper.toDto(getAppointmentItemById(id));
    }

    /** Restituisce gli item appartenenti a un appuntamento. */
    @Transactional(readOnly = true)
    public List<AppointmentItemDto> findByAppointment(Integer appointmentId) {
        return appointmentItemMapper.toDtoList(
                appointmentItemRepository
                        .findByAppointment_IdOrderByScheduledTimeAsc(appointmentId));
    }

    /**
     * Inserisce un AppointmentItem.
     *
     * Recupera le Entity collegate, verifica che dipendente e servizio
     * siano attivi e controlla che l'operatore non abbia sovrapposizioni.
     */
    @Transactional
    public AppointmentItemDto insert(AppointmentItemDto dto)
            throws ServiceException {

        Appointment appointment = getAppointment(dto.getAppointmentId());
        SalonProduct salonProduct = getSalonProduct(dto.getSalonProductId());
        Employee employee = getEmployee(dto.getEmployeeId());

        validateDuration(dto.getDuration());
        validateEmployeeAvailability(
                null,
                employee.getId(),
                dto.getScheduledTime(),
                dto.getDuration());

        AppointmentItem item = appointmentItemMapper.toEntity(dto);

        item.setAppointment(appointment);
        item.setSalonProduct(salonProduct);
        item.setEmployee(employee);

        return appointmentItemMapper.toDto(appointmentItemRepository.save(item));
    }

    /** Aggiorna un AppointmentItem esistente. */
    @Transactional
    public AppointmentItemDto update(Integer id, AppointmentItemDto dto)
            throws ServiceException {

        AppointmentItem item = getAppointmentItemById(id);

        Appointment appointment = getAppointment(dto.getAppointmentId());
        SalonProduct salonProduct = getSalonProduct(dto.getSalonProductId());
        Employee employee = getEmployee(dto.getEmployeeId());

        validateDuration(dto.getDuration());
        validateEmployeeAvailability(
                id,
                employee.getId(),
                dto.getScheduledTime(),
                dto.getDuration());

        item.setAppointment(appointment);
        item.setSalonProduct(salonProduct);
        item.setEmployee(employee);
        item.setScheduledTime(dto.getScheduledTime());
        item.setDuration(dto.getDuration());
        item.setAgreedPrice(dto.getAgreedPrice());
        item.setResultNotes(dto.getResultNotes());
        item.setCompletedAt(dto.getCompletedAt());

        return appointmentItemMapper.toDto(appointmentItemRepository.save(item));
    }

    /** Elimina un AppointmentItem tramite ID. */
    @Transactional
    public void delete(Integer id) throws ServiceException {
        appointmentItemRepository.delete(getAppointmentItemById(id));
    }

    /** Restituisce la Entity AppointmentItem tramite ID. */
    @Transactional(readOnly = true)
    public AppointmentItem getAppointmentItemById(Integer id)
            throws ServiceException {

        return appointmentItemRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        "AppointmentItem non trovato con id: " + id,
                        HttpStatus.NOT_FOUND));
    }

    /**
     * Verifica che il dipendente non abbia già un servizio sovrapposto.
     *
     * Due intervalli si sovrappongono quando:
     * nuovoInizio < fineEsistente
     * e
     * nuovaFine > inizioEsistente
     */
    private void validateEmployeeAvailability(
            Integer currentItemId,
            Integer employeeId,
            LocalDateTime newStart,
            int duration) throws ServiceException {

        if (newStart == null) {
            throw new ServiceException("L'orario del servizio è obbligatorio");
        }

        LocalDateTime newEnd = newStart.plusMinutes(duration);

        /*
         * Si cerca una finestra ampia un giorno intorno all'orario richiesto.
         * La verifica precisa dell'overlap viene poi effettuata nel Service,
         * considerando anche la durata di ogni AppointmentItem.
         */
        List<AppointmentItem> items =
                appointmentItemRepository
                        .findByEmployee_IdAndScheduledTimeBetweenOrderByScheduledTimeAsc(
                                employeeId,
                                newStart.minusDays(1),
                                newEnd.plusDays(1));

        for (AppointmentItem existing : items) {

            if (currentItemId != null && existing.getId().equals(currentItemId)) {
                continue;
            }

            LocalDateTime existingStart = existing.getScheduledTime();
            LocalDateTime existingEnd =
                    existingStart.plusMinutes(existing.getDuration());

            boolean overlap =
                    newStart.isBefore(existingEnd)
                    && newEnd.isAfter(existingStart);

            if (overlap) {
                throw new ServiceException(
                        "Il dipendente è già occupato nell'orario richiesto",
                        HttpStatus.CONFLICT);
            }
        }
    }

    /** Verifica che la durata sia positiva. */
    private void validateDuration(int duration) throws ServiceException {
        if (duration <= 0) {
            throw new ServiceException("La durata del servizio deve essere maggiore di zero");
        }
    }

    private Appointment getAppointment(Integer id) throws ServiceException {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        "Appuntamento non trovato con id: " + id,
                        HttpStatus.NOT_FOUND));
    }

    private SalonProduct getSalonProduct(Integer id) throws ServiceException {

        SalonProduct product = salonProductRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        "Servizio non trovato con id: " + id,
                        HttpStatus.NOT_FOUND));

        if (!product.isActive()) {
            throw new ServiceException(
                    "Il servizio selezionato non è attivo",
                    HttpStatus.CONFLICT);
        }

        return product;
    }

    private Employee getEmployee(Integer id) throws ServiceException {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ServiceException(
                        "Dipendente non trovato con id: " + id,
                        HttpStatus.NOT_FOUND));

        if (!employee.isActive()) {
            throw new ServiceException(
                    "Il dipendente selezionato non è attivo",
                    HttpStatus.CONFLICT);
        }

        return employee;
    }
}
