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
public class UserDto {

    private Integer id;
    private String fullName;
    private String avatar;
    private String email;
    private Boolean enabled;
    private Boolean isOrganization;

    private OrganizationDto organization;
    private List<WorkspaceDto> workspaces;
    private List<RoleDto> roles;

    public boolean getIsOrganization() {
        return this.isOrganization;
    }

    public void setIsOrganization(boolean isOrganization) {
        this.isOrganization = isOrganization;
    }

}
