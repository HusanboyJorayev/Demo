package org.example.task2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "organization")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "region_id")
    private Integer regionId;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "parent")
    private Organization parent;

    @ToString.Exclude
    @OneToMany(mappedBy = "organizationId",fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    private List<Employee>employees;

    @ToString.Exclude
    @OneToMany(mappedBy = "organizationId",fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    private List<Calculation>calculations;
}
