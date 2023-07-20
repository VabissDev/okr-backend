package com.vabiss.okrbackend.service.inter;

import com.vabiss.okrbackend.dto.WorkspaceDto;
import com.vabiss.okrbackend.entity.Workspace;

import java.util.List;

public interface WorkspaceService {

    List<WorkspaceDto> findWorkspacesByOrganizationId(int organizationId);

    WorkspaceDto findWorkspaceById(int workspaceId);

    WorkspaceDto saveWorkspace(WorkspaceDto workspaceDto);

    WorkspaceDto updateWorkspace(int workspaceId, WorkspaceDto workspaceDto);

    WorkspaceDto updateWorkspaceOwner(int workspaceId, String owner);

    WorkspaceDto convertToWorkspaceDto(Workspace workspace);

    Workspace convertToWorkspace(WorkspaceDto workspaceDto);

    void inviteUserToWorkspace(int workspaceId, int userId);

}
