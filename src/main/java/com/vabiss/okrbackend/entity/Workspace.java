package com.vabiss.okrbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "workspaces")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@ToString
@Data
@Builder
@AllArgsConstructor
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    Integer id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "owner", nullable = false)
    String owner;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "level", nullable = false)
    String level;

    @Column(name = "status", nullable = false)
    String status;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "workspaces", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JsonIgnore
    List<User> users;

    @ManyToOne
    @JoinColumn(name = "org_id")
    Organization organization;
}