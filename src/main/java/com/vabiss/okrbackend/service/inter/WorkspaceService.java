package com.vabiss.okrbackend.service.inter;

import com.vabiss.okrbackend.dto.WorkspaceDto;
import com.vabiss.okrbackend.entity.Workspace;

import java.util.List;

public interface WorkspaceService {

    List<Workspace> findWorkspacesByOrganizationId(int organizationId);

    Workspace findWorkspaceById(int workspaceId);

    Workspace saveWorkspace(Workspace workspace);

    WorkspaceDto convertToWorkspaceDto(Workspace workspace);

    Workspace convertToWorkspace(WorkspaceDto workspaceDto);

}
