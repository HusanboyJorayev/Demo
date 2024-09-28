package org.example.task2.mapper;

import org.example.task2.dto.EmployeeDto;
import org.example.task2.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDto toDto(Employee employee);


    @Mapping(ignore = true,target = "calculations")
    Employee toEntity(EmployeeDto dto);

    void update(EmployeeDto dto, @MappingTarget Employee employee);

    List<EmployeeDto> toDtoLis(List<Employee> list);
}
