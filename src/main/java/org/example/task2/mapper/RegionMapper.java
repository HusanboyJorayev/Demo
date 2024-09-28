package org.example.task2.mapper;

import org.example.task2.dto.RegionDto;
import org.example.task2.entity.Region;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegionMapper {


    @Mapping(ignore = true,target = "organizations")
    Region toEntity(RegionDto regionDto);

    RegionDto toDto(Region region);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRegion(RegionDto regionDto, @MappingTarget Region region);

    List<RegionDto> toDtoList(List<Region> regions);
}
