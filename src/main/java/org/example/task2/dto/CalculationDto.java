package org.example.task2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.task2.enums.CalculationType;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CalculationDto {
    private Integer employeeId;
    private Double amount;
    private Double rate;
    private LocalDate date;
    private Integer organizationId;
    private CalculationType calculationType;
}
