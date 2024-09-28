package org.example.task2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizationDto {
    private String name;
    private Integer regionId;
    /*private List<EmployeeDto> employees;
    private List<CalculationDto> calculations;*/

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShortInfo {
        private Integer countOrganization;
        private Integer countAmount;
    }
}
