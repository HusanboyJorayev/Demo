package org.example.task2.mapper;

import org.example.task2.dto.CalculationDto;
import org.example.task2.entity.Calculation;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CalculationMapper {

    CalculationDto toDto(Calculation calculation);

    Calculation toEntity(CalculationDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(CalculationDto dto, @MappingTarget Calculation calculation);

    List<CalculationDto> toDtoList(List<Calculation> list);
}
