package com.generation.hairlab.mapper;

import java.util.Collections;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.generation.hairlab.dto.CustomerDto;
import com.generation.hairlab.model.Appointment;
import com.generation.hairlab.model.Customer;

/**
 * Mapper MapStruct utilizzato per convertire Customer in CustomerDto
 * e viceversa.
 *
 * La relazione con gli Appointment viene rappresentata nel DTO tramite
 * una lista di identificativi, evitando di trasferire direttamente le Entity
 * e prevenendo riferimenti circolari nella serializzazione JSON.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper {

    /**
     * Converte una Entity Customer nel relativo DTO.
     *
     * @param entity Entity da convertire
     * @return DTO corrispondente
     */
    @Mapping(target = "appointmentIds", source = "appointments")
    CustomerDto toDto(Customer entity);

    /**
     * Converte una lista di Customer in una lista di CustomerDto.
     *
     * @param entities lista delle Entity
     * @return lista dei DTO
     */
    List<CustomerDto> toDtoList(List<Customer> entities);

    /**
     * Converte un CustomerDto in una nuova Entity Customer.
     *
     * L'id viene ignorato perché normalmente viene generato dal database.
     * Anche appointments, createdAt e updatedAt vengono ignorati perché
     * devono essere gestiti rispettivamente dal Service e dal backend.
     *
     * @param dto DTO da convertire
     * @return nuova Entity Customer
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Customer toEntity(CustomerDto dto);

    /**
     * Converte una lista di CustomerDto in una lista di Customer.
     *
     * Le regole definite nel metodo toEntity vengono applicate
     * automaticamente a ogni elemento della lista.
     *
     * @param dtos lista dei DTO
     * @return lista delle Entity
     */
    List<Customer> toEntityList(List<CustomerDto> dtos);

    /**
     * Metodo di supporto usato da MapStruct per convertire
     * List<Appointment> in List<Integer>.
     *
     * @param appointments appuntamenti associati al cliente
     * @return lista degli identificativi
     */
    default List<Integer> mapAppointmentsToIds(List<Appointment> appointments) {
        if (appointments == null) {
            return Collections.emptyList();
        }

        return appointments.stream()
                .map(Appointment::getId)
                .toList();
    }
}
