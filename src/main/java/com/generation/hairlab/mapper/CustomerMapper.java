package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.generation.hairlab.dto.CustomerDto;
import com.generation.hairlab.model.Appointment;
import com.generation.hairlab.model.Customer;

/**
 * Mapper MapStruct utilizzato per convertire:
 *
 * Customer <-> CustomerDto.
 *
 * La relazione:
 *
 * Customer -> List<Appointment>
 *
 * viene esposta nel DTO come:
 *
 * List<Integer> appointmentIds.
 *
 * In questo modo evitiamo strutture JSON ricorsive:
 *
 * Customer
 * -> Appointment
 * -> Customer
 * -> Appointment
 * ...
 */
@Mapper(
    componentModel = "spring"
)
public interface CustomerMapper {

    /**
     * Converte Customer in CustomerDto.
     *
     * appointments viene convertito
     * in appointmentIds utilizzando
     * il metodo mapAppointmentToId().
     */
    @Mapping(
        target = "appointmentIds",
        source = "appointments"
    )
    CustomerDto toDto(
        Customer entity
    );

    /**
     * Converte una lista di Customer
     * in una lista di DTO.
     */
    List<CustomerDto> toDtoList(
        List<Customer> entities
    );

    /**
     * Converte CustomerDto
     * in una nuova Entity Customer.
     *
     * Le relazioni e i campi gestiti
     * dal backend vengono ignorati.
     */
    @Mapping(
        target = "id",
        ignore = true
    )
    @Mapping(
        target = "appointments",
        ignore = true
    )
    @Mapping(
        target = "createdAt",
        ignore = true
    )
    @Mapping(
        target = "updatedAt",
        ignore = true
    )
    Customer toEntity(
        CustomerDto dto
    );

    /**
     * Converte una lista di DTO
     * in una lista di Entity.
     */
    List<Customer> toEntityList(
        List<CustomerDto> dtos
    );

    /**
     * Metodo helper utilizzato automaticamente
     * da MapStruct durante la conversione:
     *
     * Appointment
     * ->
     * Integer
     *
     * Restituisce solamente l'ID
     * dell'appuntamento.
     */
    default Integer mapAppointmentToId(
        Appointment appointment
    ) {

        if (appointment == null) {

            return null;
        }

        return appointment.getId();
    }
}