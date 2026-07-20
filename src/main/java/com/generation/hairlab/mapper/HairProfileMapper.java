package com.generation.hairlab.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.generation.hairlab.dto.HairProfileDto;
import com.generation.hairlab.model.HairProfile;

/**
 * Mapper MapStruct per HairProfile.
 *
 * In uscita la relazione OneToOne con Customer viene ridotta a customerId.
 * In ingresso il mapper non crea un Customer fittizio: sarà il Service a
 * recuperare il Customer reale tramite CustomerRepository.
 */
@Mapper(config = HairLabMapperConfig.class)
public interface HairProfileMapper {

    /** Converte HairProfile in HairProfileDto. */
    @Mapping(target = "customerId", source = "customer.id")
    HairProfileDto toDto(HairProfile entity);

    /** Converte una lista di HairProfile in DTO. */
    List<HairProfileDto> toDtoList(List<HairProfile> entities);

    /**
     * Crea una nuova HairProfile dai campi semplici del DTO.
     *
     * customer viene ignorato perché la relazione deve essere risolta
     * dal Service usando customerId.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    HairProfile toEntity(HairProfileDto dto);

    /**
     * Aggiorna i dati tecnici della HairProfile senza cambiare
     * automaticamente cliente proprietario o ID.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    void updateEntityFromDto(HairProfileDto dto, @MappingTarget HairProfile entity);
}
