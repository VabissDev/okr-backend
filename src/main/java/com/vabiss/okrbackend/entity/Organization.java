package com.vabiss.okrbackend.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="organizations")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="organization_id")
    private Integer organizationId;
    private String name;
}
