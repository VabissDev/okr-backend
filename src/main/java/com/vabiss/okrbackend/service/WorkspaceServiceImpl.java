package com.vabiss.okrbackend.service;

import com.vabiss.okrbackend.dto.WorkspaceDto;
import com.vabiss.okrbackend.entity.Organization;
import com.vabiss.okrbackend.entity.User;
import com.vabiss.okrbackend.entity.Workspace;
import com.vabiss.okrbackend.exception.ResourceNotFoundException;
import com.vabiss.okrbackend.repository.OrganizationRepository;
import com.vabiss.okrbackend.repository.UserRepository;
import com.vabiss.okrbackend.repository.WorkspaceRepository;
import com.vabiss.okrbackend.service.inter.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final OrganizationRepository organizationRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public List<WorkspaceDto> findWorkspacesByOrganizationId(int organizationId) {
        if (organizationRepository.findById(organizationId).isEmpty()) {
            throw new ResourceNotFoundException("Organization not found - " + organizationId);
        }
        Organization organization = organizationRepository.findById(organizationId).get();
        List<Workspace> workspaces = workspaceRepository.findWorkspacesByOrganization(organization);

        return workspaces.stream()
                .map(this::convertToWorkspaceDto).toList();
    }

    @Override
    public WorkspaceDto findWorkspaceById(int workspaceId) {
        if (workspaceRepository.findById(workspaceId).isEmpty()) {
            throw new ResourceNotFoundException("Workspace not found - " + workspaceId);
        }
        Workspace workspace = workspaceRepository.findById(workspaceId).get();
        return convertToWorkspaceDto(workspace);
    }

    @Override
    public WorkspaceDto saveWorkspace(WorkspaceDto workspaceDto) {
        int organizationId = workspaceDto.getOrganizationId();
        if (organizationRepository.findById(organizationId).isEmpty()) {
            throw new ResourceNotFoundException("Organization not found - " + organizationId);
        }
        Workspace workspace = convertToWorkspace(workspaceDto);
        return convertToWorkspaceDto(workspaceRepository.save(workspace));
    }

    @Override
    public WorkspaceDto updateWorkspace(int workspaceId, WorkspaceDto workspaceDto) {
        if (workspaceRepository.findById(workspaceId).isEmpty()) {
            throw new ResourceNotFoundException("Workspace not found - " + workspaceId);
        }
        workspaceDto.setId(workspaceId);
        Workspace workspace = convertToWorkspace(workspaceDto);
        workspaceRepository.save(workspace);
        return convertToWorkspaceDto(workspace);
    }

    @Override
    public WorkspaceDto updateWorkspaceOwner(int workspaceId, String owner) {
        if (workspaceRepository.findById(workspaceId).isEmpty()) {
            throw new ResourceNotFoundException("Workspace not found - " + workspaceId);
        }
        Workspace workspace = workspaceRepository.findById(workspaceId).get();
        workspace.setOwner(owner);
        workspaceRepository.save(workspace);

        return convertToWorkspaceDto(workspace);
    }

    @Override
    public WorkspaceDto convertToWorkspaceDto(Workspace workspace) {
        return modelMapper.map(workspace, WorkspaceDto.class);
    }

    @Override
    public Workspace convertToWorkspace(WorkspaceDto workspaceDto) {
        return modelMapper.map(workspaceDto, Workspace.class);
    }

    @Override
    public void inviteUserToWorkspace(int workspaceId, int userId) {
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(() -> new RuntimeException("Workspace not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (!workspace.getOwner().equals(user.getUsername())) {
            throw new RuntimeException("Only workspace creators can invite other users");
        }

        if (workspace.getUsers().contains(user)) {
            throw new RuntimeException("User is already a member of this workspace");
        }

        if (workspace.getOwner().equals("LEADER") && user.getRoles().get(0).getRoleName().equals("ADMIN")) {
            throw new RuntimeException("Team Leaders cannot invite Admins");
        }

        if (!workspace.getOrganization().equals(user.getOrganization())) {
            throw new RuntimeException("Invited user does not belong to the same organization");
        }

        workspace.getUsers().add(user);
        workspaceRepository.save(workspace);

        String inviteLink = "https://vabiss-okr.vercel.app/workspaces/" + workspaceId;
        emailService.sendInvitationEmail(user, workspace, inviteLink);
    }

    @Override
    public Workspace acceptInvite(int workspaceId, int userId) {
        Workspace workspace = workspaceRepository.getById(workspaceId);

        User user = userRepository.getById(userId);
        List<User> workspaceUsers = workspace.getUsers();
        workspaceUsers.add(user);
        workspace.setUsers(workspaceUsers);
        return workspace;
    }

}
