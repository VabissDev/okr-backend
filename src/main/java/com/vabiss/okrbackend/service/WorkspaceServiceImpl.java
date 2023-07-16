package com.vabiss.okrbackend.service;

import com.vabiss.okrbackend.dto.WorkspaceDto;
import com.vabiss.okrbackend.entity.Organization;
import com.vabiss.okrbackend.entity.Workspace;
import com.vabiss.okrbackend.exception.ResourceNotFoundException;
import com.vabiss.okrbackend.repository.OrganizationRepository;
import com.vabiss.okrbackend.repository.WorkspaceRepository;
import com.vabiss.okrbackend.service.inter.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final OrganizationRepository organizationRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Workspace> findWorkspacesByOrganizationId(int organizationId) {
        if (organizationRepository.findById(organizationId).isEmpty()) {
            throw new ResourceNotFoundException("Organization not found - " + organizationId);
        }
        Organization organization = organizationRepository.findById(organizationId).get();
        return workspaceRepository.findWorkspacesByOrganization(organization);
    }

    @Override
    public Workspace findWorkspaceById(int workspaceId) {
        if (workspaceRepository.findById(workspaceId).isEmpty()) {
            throw new ResourceNotFoundException("Workspace not found - " + workspaceId);
        }
        return workspaceRepository.findById(workspaceId).get();
    }

    @Override
    public Workspace saveWorkspace(Workspace workspace) {
        return workspaceRepository.save(workspace);
    }

    @Override
    public WorkspaceDto convertToWorkspaceDto(Workspace workspace) {
        return modelMapper.map(workspace, WorkspaceDto.class);
    }

    @Override
    public Workspace convertToWorkspace(WorkspaceDto workspaceDto) {
        return modelMapper.map(workspaceDto, Workspace.class);
    }

}
