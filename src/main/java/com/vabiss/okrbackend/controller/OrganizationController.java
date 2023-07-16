package com.vabiss.okrbackend.controller;

import com.vabiss.okrbackend.dto.OrganizationDto;
import com.vabiss.okrbackend.dto.SuccessResponseDto;
import com.vabiss.okrbackend.entity.Organization;
import com.vabiss.okrbackend.service.inter.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    @PutMapping("/{organizationId}/avatar")
    public ResponseEntity<SuccessResponseDto> updateOrganizationAvatar(@PathVariable int organizationId,
                                                                       @RequestBody OrganizationDto organizationDto) {
        Organization organization = organizationService.updateAvatar(organizationId, organizationDto.getAvatar());
        OrganizationDto organizationDto1 = organizationService.convertToOrganizationDto(organization);

        return ResponseEntity.ok(SuccessResponseDto.of("Avatar updated", organizationDto1));
    }

}
