package com.vabiss.okrbackend.controller;

import com.vabiss.okrbackend.dto.SuccessResponseDto;
import com.vabiss.okrbackend.dto.WorkspaceDto;
import com.vabiss.okrbackend.entity.Workspace;
import com.vabiss.okrbackend.service.inter.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin")
@RestController
@RequestMapping("/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @GetMapping("/organizations/{organizationId}")
    public ResponseEntity<SuccessResponseDto> getWorkspacesByOrganization(@PathVariable int organizationId) {
        List<Workspace> workspaces = workspaceService.findWorkspacesByOrganizationId(organizationId);
        List<WorkspaceDto> workspaceDtos = workspaces.stream()
                .map(workspaceService::convertToWorkspaceDto).toList();

        return ResponseEntity.ok(SuccessResponseDto.of("Workspaces by organization", workspaceDtos));
    }

    @GetMapping("/{workspaceId}")
    public ResponseEntity<SuccessResponseDto> getSingleWorkspace(@PathVariable int workspaceId) {
        Workspace workspace = workspaceService.findWorkspaceById(workspaceId);
        WorkspaceDto workspaceDto = workspaceService.convertToWorkspaceDto(workspace);

        return ResponseEntity.ok(SuccessResponseDto.of("Workspace - " + workspaceId, workspaceDto));
    }

    @PostMapping
    public ResponseEntity<SuccessResponseDto> createWorkspace(@RequestBody WorkspaceDto workspaceDto) {
        Workspace workspace = workspaceService.convertToWorkspace(workspaceDto);
        workspace = workspaceService.saveWorkspace(workspace);
        workspaceDto = workspaceService.convertToWorkspaceDto(workspace);
        return ResponseEntity.ok(SuccessResponseDto.of("Workspace saved", workspaceDto));
    }

    @PutMapping("/{workspaceId}")
    public ResponseEntity<SuccessResponseDto> updateWorkspace(@PathVariable int workspaceId,
                                                              @RequestBody WorkspaceDto workspaceDto) {
        WorkspaceDto workspaceDto1 = workspaceService.updateWorkspace(workspaceId, workspaceDto);
        return ResponseEntity.ok(SuccessResponseDto.of("Updated", workspaceDto1));
    }

    @PutMapping("/{workspaceId}/owner")
    public ResponseEntity<SuccessResponseDto> updateWorkspaceOwner(@PathVariable int workspaceId,
                                                                   @RequestBody WorkspaceDto workspaceDto) {
        WorkspaceDto workspaceDto1 = workspaceService.updateWorkspaceOwner(workspaceId, workspaceDto.getOwner());
        return ResponseEntity.ok(SuccessResponseDto.of("Owner updated", workspaceDto1));
    }


    @PostMapping("/{workspaceId}/invite/{userId}")
    public ResponseEntity<SuccessResponseDto> inviteUserToWorkspace(@PathVariable int workspaceId, @PathVariable int userId) {
        workspaceService.inviteUserToWorkspace(workspaceId, userId);
        return ResponseEntity.ok(SuccessResponseDto.of("User invited successfully"));
    }

}
