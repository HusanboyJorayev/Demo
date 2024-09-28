package org.example.task2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeOrganizationResponse {
    private Integer eId;
    private Integer eOrganizationId;
    private String ePrinfl;
    private String eFirstname;
    private String eLastname;
    private LocalDate eHireDate;
    private String oName;
    private Integer oRegionId;
    private Integer oId;
    private Double oAmount;
}
