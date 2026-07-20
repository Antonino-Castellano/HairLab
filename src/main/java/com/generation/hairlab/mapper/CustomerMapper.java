package com.generation.hairlab.mapper;

import java.util.Collections;
import java.util.List;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.generation.hairlab.dto.CustomerDto;
import com.generation.hairlab.model.Appointment;
import com.generation.hairlab.model.Customer;

import org.mapstruct.Mapper;

/**
 * Mapper MapStruct per la conversione tra Customer e CustomerDto.
 *
 * La lista di Appointment della Entity viene trasformata in una lista
 * di soli identificativi nel DTO. Nel percorso inverso la relazione viene
 * ignorata perché deve essere gestita dal Service e non dal mapper.
 */
@Mapper(config = HairLabMapperConfig.class)
public interface CustomerMapper {

    /**
     * Converte una Entity Customer in CustomerDto.
     *
     * @param entity Customer da convertire
     * @return DTO corrispondente
     */
    @Mapping(target = "appointmentIds", source = "appointments")
    CustomerDto toDto(Customer entity);

    /**
     * Converte una lista di Customer in una lista di DTO.
     *
     * @param entities lista di Entity
     * @return lista di DTO
     */
    List<CustomerDto> toDtoList(List<Customer> entities);

    /**
     * Crea una nuova Entity Customer a partire dal DTO.
     *
     * id, createdAt e updatedAt vengono ignorati perché devono essere
     * generati/gestiti dal database o dal Service.
     * La relazione appointments non viene costruita dal mapper.
     *
     * @param dto dati da convertire
     * @return nuova Entity Customer
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Customer toEntity(CustomerDto dto);

    /**
     * Aggiorna una Customer esistente utilizzando i dati del DTO.
     *
     * L'identità, le date tecniche e le relazioni non vengono modificate
     * automaticamente dal mapper.
     *
     * @param dto dati aggiornati
     * @param entity Entity esistente da aggiornare
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(CustomerDto dto, @MappingTarget Customer entity);

    /**
     * Metodo di supporto utilizzato da MapStruct per convertire
     * List<Appointment> in List<Integer>.
     *
     * @param appointments appuntamenti del cliente
     * @return lista degli ID degli appuntamenti
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
