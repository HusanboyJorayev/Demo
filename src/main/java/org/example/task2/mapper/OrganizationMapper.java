package org.example.task2.mapper;

import org.example.task2.dto.OrganizationDto;
import org.example.task2.entity.Organization;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    OrganizationDto toDto(Organization organization);

    @Mapping(ignore = true,target = "parent")
    @Mapping(ignore = true,target = "employees")
    @Mapping(ignore = true,target = "calculations")
    Organization toEntity(OrganizationDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDto(OrganizationDto dto, @MappingTarget Organization organization);

    List<OrganizationDto> toDtoList(List<Organization> list);
}
