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
public class EmployeeCalculationResponse {
    private Integer cId;
    private Double cRate;
    private LocalDate cDate;
    private Double cAmount;
    private CalculationType calculationType;
    private Integer cOrganizationId;
    private Integer eId;
    private Integer eOrganizationId;
    private String ePrinfl;
    private String eFirstname;
    private String eLastname;
    private LocalDate eHireDate;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ShortInfo {
        private Integer eId;
        private Integer eOrganizationId;
        private String ePrinfl;
        private String eFirstname;
        private String eLastname;
        private LocalDate eHireDate;
        private Double cAmount;
        private CalculationType calculationType;
    }
}
