package org.example.task2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDto {
    private String firstname;
    private String lastname;
    private String pinfl;
    private LocalDate hireDate;
    private Integer organizationId;
    //private List<CalculationDto> calculations;
}
