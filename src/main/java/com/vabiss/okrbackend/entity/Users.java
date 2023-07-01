package com.vabiss.okrbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    private String fullName;
    private String email;
    private String password;

//    @ManyToMany
//    @JoinTable(
//            name = "WorkspaceUser",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "workspace_id")
//    )
//    private List<Workspaces> workspaces;
//
//    @ManyToOne
//    @JoinColumn(name = "organization_id", referencedColumnName = "id")
//    private Organizations organizations;
//

    @ManyToOne
    @JoinColumn(name = "permission_id", referencedColumnName = "user_id")
    private Permission permission;



}
