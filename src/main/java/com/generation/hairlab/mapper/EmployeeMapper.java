package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.generation.hairlab.dto.EmployeeDto;
import com.generation.hairlab.model.Employee;

/**
 * Mapper MapStruct per la conversione tra Employee ed EmployeeDto.
 *
 * Tutti i campi funzionali hanno lo stesso significato nei due oggetti,
 * quindi MapStruct può mapparli automaticamente per nome e tipo.
 */
@Mapper(config = HairLabMapperConfig.class)
public interface EmployeeMapper {

    /** Converte Employee in EmployeeDto. */
    EmployeeDto toDto(Employee entity);

    /** Converte una lista di Employee in DTO. */
    List<EmployeeDto> toDtoList(List<Employee> entities);

    /**
     * Crea una nuova Employee dal DTO.
     *
     * L'ID viene ignorato perché generato dal database.
     */
    @Mapping(target = "id", ignore = true)
    Employee toEntity(EmployeeDto dto);

    /**
     * Aggiorna una Employee esistente senza modificarne l'identificativo.
     */
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(EmployeeDto dto, @MappingTarget Employee entity);
}
