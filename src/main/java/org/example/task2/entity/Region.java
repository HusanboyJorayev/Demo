package org.example.task2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "region")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "regionId",fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    private List<Organization>organizations;
}
