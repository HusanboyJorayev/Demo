package org.example.task2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstname;
    @Column(name = "last_name")
    private String lastname;
    private String pinfl;
    @Column(name = "hire_date")
    private LocalDate hireDate;
    @Column(name = "organization_id")
    private Integer organizationId;


    @ToString.Exclude
    @OneToMany(mappedBy = "employeeId",fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    private List<Calculation> calculations;
}
