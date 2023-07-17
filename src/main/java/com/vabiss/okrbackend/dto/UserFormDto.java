package com.vabiss.okrbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFormDto {
    private String fullName;
    private String email;
    private String password;
    private Boolean enabled;
    private Boolean isOrganization;

    private OrganizationDto organization;
    private List<WorkspaceDto> workspaces;
    private List<RoleDto> roles;

}
