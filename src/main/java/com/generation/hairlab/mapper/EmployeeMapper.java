package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.generation.hairlab.dto.EmployeeDto;
import com.generation.hairlab.model.Employee;

/**
 * Mapper MapStruct utilizzato per convertire Employee in EmployeeDto
 * e viceversa.
 *
 * I campi semplici e gli enum hanno gli stessi nomi e tipi in Entity e DTO,
 * quindi MapStruct li converte automaticamente.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    /** Converte Employee in EmployeeDto. */
    EmployeeDto toDto(Employee entity);

    /** Converte una lista di Employee in una lista di EmployeeDto. */
    List<EmployeeDto> toDtoList(List<Employee> entities);

    /**
     * Converte EmployeeDto in una nuova Entity Employee.
     *
     * L'id viene ignorato perché generato dal database.
     */
    @Mapping(target = "id", ignore = true)
    Employee toEntity(EmployeeDto dto);

    /** Converte una lista di EmployeeDto in una lista di Employee. */
    List<Employee> toEntityList(List<EmployeeDto> dtos);
}
