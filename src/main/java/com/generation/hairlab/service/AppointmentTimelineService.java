package com.generation.hairlab.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.generation.hairlab.dto.AppointmentTimelineItemDto;
import com.generation.hairlab.enums.AppointmentStatus;
import com.generation.hairlab.model.Appointment;
import com.generation.hairlab.model.AppointmentItem;
import com.generation.hairlab.model.Customer;
import com.generation.hairlab.model.Employee;
import com.generation.hairlab.model.SalonProduct;
import com.generation.hairlab.repository.AppointmentItemRepository;

import lombok.RequiredArgsConstructor;

/**
 * Query aggregata per la Timeline operatori.
 *
 * Prima del Blocco 8 il frontend eseguiva:
 *
 * - GET appuntamenti del giorno;
 * - GET item appuntamento 1;
 * - GET item appuntamento 2;
 * - ...
 *
 * Ora il backend restituisce direttamente
 * tutti gli AppointmentItem della giornata
 * già arricchiti con i dati visuali necessari.
 */
@Service
@RequiredArgsConstructor
public class AppointmentTimelineService {

    private final AppointmentItemRepository
        appointmentItemRepository;

    /**
     * Restituisce gli item operativi
     * della giornata richiesta.
     *
     * CANCELLED e NO_SHOW:
     *
     * - restano nel database;
     * - restano nello storico;
     * - non occupano la timeline operativa.
     */
    @Transactional(readOnly = true)
    public List<AppointmentTimelineItemDto>
        findByDate(
            LocalDate date
        ) {

        LocalDateTime start =
            date.atStartOfDay();

        LocalDateTime end =
            date
                .plusDays(1)
                .atStartOfDay();

        return appointmentItemRepository
            .findByScheduledTimeGreaterThanEqualAndScheduledTimeLessThanOrderByScheduledTimeAsc(
                start,
                end
            )
            .stream()
            .filter(
                this::isOperationalItem
            )
            .map(
                this::toTimelineDto
            )
            .toList();
    }

    /**
     * Verifica che l'item appartenga
     * a un appuntamento visualizzabile.
     */
    private boolean isOperationalItem(
            AppointmentItem item) {

        Appointment appointment =
            item.getAppointment();

        if (
            appointment == null ||
            appointment.getStatus() == null
        ) {

            return false;
        }

        return (
            appointment.getStatus() !=
                AppointmentStatus.CANCELLED
            &&
            appointment.getStatus() !=
                AppointmentStatus.NO_SHOW
        );
    }

    /**
     * Mapping manuale intenzionale.
     *
     * Questo DTO è specifico per una query aggregata
     * e non rappresenta direttamente una singola Entity.
     */
    private AppointmentTimelineItemDto
        toTimelineDto(
            AppointmentItem item
        ) {

        Appointment appointment =
            item.getAppointment();

        Customer customer =
            appointment.getCustomer();

        Employee employee =
            item.getEmployee();

        SalonProduct product =
            item.getSalonProduct();

        AppointmentTimelineItemDto dto =
            new AppointmentTimelineItemDto();

        dto.setAppointmentItemId(
            item.getId()
        );

        dto.setAppointmentId(
            appointment.getId()
        );

        dto.setCustomerId(
            customer != null
                ? customer.getId()
                : null
        );

        dto.setCustomerName(
            customer != null
                ? joinName(
                    customer.getFirstName(),
                    customer.getLastName()
                )
                : "Cliente non disponibile"
        );

        dto.setEmployeeId(
            employee != null
                ? employee.getId()
                : null
        );

        dto.setEmployeeName(
            employee != null
                ? joinName(
                    employee.getFirstName(),
                    employee.getLastName()
                )
                : "Operatore non disponibile"
        );

        dto.setSalonProductId(
            product != null
                ? product.getId()
                : null
        );

        dto.setServiceName(
            product != null
                ? product.getName()
                : "Servizio non disponibile"
        );

        dto.setScheduledTime(
            item.getScheduledTime()
        );

        dto.setDuration(
            item.getDuration()
        );

        dto.setStatus(
            appointment.getStatus()
        );

        return dto;
    }

    private String joinName(
            String firstName,
            String lastName) {

        String first =
            firstName != null
                ? firstName.trim()
                : "";

        String last =
            lastName != null
                ? lastName.trim()
                : "";

        return (
            first +
            " " +
            last
        ).trim();
    }
}
