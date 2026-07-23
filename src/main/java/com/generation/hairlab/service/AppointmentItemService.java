package com.generation.hairlab.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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
 * Service dedicato ai singoli servizi
 * contenuti negli appuntamenti.
 *
 * La regola di sovrapposizione operatori
 * NON è più duplicata qui:
 *
 * viene delegata ad AppointmentAvailabilityService.
 *
 * In questo modo:
 *
 * - verifica preventiva frontend;
 * - insert;
 * - update;
 *
 * utilizzano la stessa identica regola.
 */
@Service
@RequiredArgsConstructor
public class AppointmentItemService {

    private final AppointmentItemRepository
        appointmentItemRepository;

    private final AppointmentRepository
        appointmentRepository;

    private final SalonProductRepository
        salonProductRepository;

    private final EmployeeRepository
        employeeRepository;

    private final AppointmentItemMapper
        appointmentItemMapper;

    private final AppointmentAvailabilityService
        appointmentAvailabilityService;

    /**
     * Restituisce tutti gli item.
     */
    @Transactional(readOnly = true)
    public List<AppointmentItemDto> findAll() {

        return appointmentItemMapper.toDtoList(
            appointmentItemRepository.findAll()
        );
    }

    /**
     * Cerca un item tramite ID.
     */
    @Transactional(readOnly = true)
    public AppointmentItemDto findById(
            Integer id)
            throws ServiceException {

        return appointmentItemMapper.toDto(
            getAppointmentItemById(
                id
            )
        );
    }

    /**
     * Restituisce gli item
     * di un appuntamento.
     */
    @Transactional(readOnly = true)
    public List<AppointmentItemDto>
        findByAppointment(
            Integer appointmentId
        ) {

        return appointmentItemMapper.toDtoList(
            appointmentItemRepository
                .findByAppointment_IdOrderByScheduledTimeAsc(
                    appointmentId
                )
        );
    }

    /**
     * Inserisce un servizio appuntamento.
     */
    @Transactional
    public AppointmentItemDto insert(
            AppointmentItemDto dto)
            throws ServiceException {

        if (
            dto == null
        ) {

            throw new ServiceException(
                "I dati del servizio appuntamento sono obbligatori",
                HttpStatus.BAD_REQUEST
            );
        }

        Appointment appointment =
            getAppointment(
                dto.getAppointmentId()
            );

        SalonProduct salonProduct =
            getSalonProduct(
                dto.getSalonProductId()
            );

        Employee employee =
            getEmployee(
                dto.getEmployeeId()
            );

        validateDuration(
            dto.getDuration()
        );

        /*
         * Controllo autoritativo.
         *
         * Anche se il frontend ha già verificato
         * la disponibilità, il backend ricontrolla.
         */
        appointmentAvailabilityService
            .validateEmployeeAvailability(
                employee.getId(),
                dto.getScheduledTime(),
                dto.getDuration(),
                null
            );

        AppointmentItem item =
            appointmentItemMapper.toEntity(
                dto
            );

        item.setAppointment(
            appointment
        );

        item.setSalonProduct(
            salonProduct
        );

        item.setEmployee(
            employee
        );

        return appointmentItemMapper.toDto(
            appointmentItemRepository.save(
                item
            )
        );
    }

    /**
     * Aggiorna un AppointmentItem.
     */
    @Transactional
    public AppointmentItemDto update(
            Integer id,
            AppointmentItemDto dto)
            throws ServiceException {

        AppointmentItem item =
            getAppointmentItemById(
                id
            );

        Appointment appointment =
            getAppointment(
                dto.getAppointmentId()
            );

        SalonProduct salonProduct =
            getSalonProduct(
                dto.getSalonProductId()
            );

        Employee employee =
            getEmployee(
                dto.getEmployeeId()
            );

        validateDuration(
            dto.getDuration()
        );

        appointmentAvailabilityService
            .validateEmployeeAvailability(
                employee.getId(),
                dto.getScheduledTime(),
                dto.getDuration(),
                id
            );

        item.setAppointment(
            appointment
        );

        item.setSalonProduct(
            salonProduct
        );

        item.setEmployee(
            employee
        );

        item.setScheduledTime(
            dto.getScheduledTime()
        );

        item.setDuration(
            dto.getDuration()
        );

        item.setAgreedPrice(
            dto.getAgreedPrice()
        );

        item.setResultNotes(
            normalizeNullable(
                dto.getResultNotes()
            )
        );

        item.setCompletedAt(
            dto.getCompletedAt()
        );

        return appointmentItemMapper.toDto(
            appointmentItemRepository.save(
                item
            )
        );
    }

    /**
     * Elimina un item.
     */
    @Transactional
    public void delete(
            Integer id)
            throws ServiceException {

        appointmentItemRepository.delete(
            getAppointmentItemById(
                id
            )
        );
    }

    /**
     * Restituisce la Entity.
     */
    @Transactional(readOnly = true)
    public AppointmentItem
        getAppointmentItemById(
            Integer id
        )
        throws ServiceException {

        return appointmentItemRepository
            .findById(
                id
            )
            .orElseThrow(
                () ->
                    new ServiceException(
                        "AppointmentItem non trovato con id: " +
                            id,
                        HttpStatus.NOT_FOUND
                    )
            );
    }

    private void validateDuration(
            int duration)
            throws ServiceException {

        if (
            duration <= 0
        ) {

            throw new ServiceException(
                "La durata del servizio deve essere maggiore di zero",
                HttpStatus.BAD_REQUEST
            );
        }
    }

    private Appointment getAppointment(
            Integer id)
            throws ServiceException {

        if (
            id == null
        ) {

            throw new ServiceException(
                "L'appuntamento è obbligatorio",
                HttpStatus.BAD_REQUEST
            );
        }

        return appointmentRepository
            .findById(
                id
            )
            .orElseThrow(
                () ->
                    new ServiceException(
                        "Appuntamento non trovato con id: " +
                            id,
                        HttpStatus.NOT_FOUND
                    )
            );
    }

    private SalonProduct getSalonProduct(
            Integer id)
            throws ServiceException {

        if (
            id == null
        ) {

            throw new ServiceException(
                "Il servizio è obbligatorio",
                HttpStatus.BAD_REQUEST
            );
        }

        SalonProduct product =
            salonProductRepository
                .findById(
                    id
                )
                .orElseThrow(
                    () ->
                        new ServiceException(
                            "Servizio non trovato con id: " +
                                id,
                            HttpStatus.NOT_FOUND
                        )
                );

        if (
            !product.isActive()
        ) {

            throw new ServiceException(
                "Il servizio selezionato non è attivo",
                HttpStatus.CONFLICT
            );
        }

        return product;
    }

    private Employee getEmployee(
            Integer id)
            throws ServiceException {

        if (
            id == null
        ) {

            throw new ServiceException(
                "Il dipendente è obbligatorio",
                HttpStatus.BAD_REQUEST
            );
        }

        Employee employee =
            employeeRepository
                .findById(
                    id
                )
                .orElseThrow(
                    () ->
                        new ServiceException(
                            "Dipendente non trovato con id: " +
                                id,
                            HttpStatus.NOT_FOUND
                        )
                );

        if (
            !employee.isActive()
        ) {

            throw new ServiceException(
                "Il dipendente selezionato non è attivo",
                HttpStatus.CONFLICT
            );
        }

        return employee;
    }

    private String normalizeNullable(
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
