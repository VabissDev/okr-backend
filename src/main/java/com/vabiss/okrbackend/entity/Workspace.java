package com.vabiss.okrbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "workspaces")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    int workspaceId;

    @Column(name = "name", nullable = false)
    String workspaceName;

    @Column(name = "owner", nullable = false)
    String owner;

    @Column(name = "description", nullable = false)
    String description;

    @Column(name = "level", nullable = false)
    String level;

    @Column(name = "status", nullable = false)
    String status;

    @ManyToMany(mappedBy = "workspaces", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<User> users;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    Organization organization;
}