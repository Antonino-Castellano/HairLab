package com.generation.hairlab.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Configurazione condivisa da tutti i mapper MapStruct di HairLab.
 *
 * componentModel SPRING fa sì che MapStruct generi implementazioni gestite
 * come Bean Spring, quindi i mapper possono essere iniettati nei Service.
 *
 * unmappedTargetPolicy ERROR forza la compilazione a fallire quando viene
 * aggiunto un nuovo campo a una Entity/DTO senza aver deciso esplicitamente
 * come mapparlo. Questa scelta aiuta a evitare conversioni incomplete
 * introdotte accidentalmente durante l'evoluzione del progetto.
 */
@MapperConfig(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface HairLabMapperConfig {
}
