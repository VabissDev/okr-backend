package com.vabiss.okrbackend.controller;

import com.vabiss.okrbackend.dto.OrganizationDto;
import com.vabiss.okrbackend.dto.SuccessResponseDto;
import com.vabiss.okrbackend.entity.User;
import com.vabiss.okrbackend.service.inter.OrganizationService;
import com.vabiss.okrbackend.service.inter.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin")
@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping("{organizationId}")
    public ResponseEntity<SuccessResponseDto> getOrganization(@PathVariable int organizationId) {
        OrganizationDto organizationDto = organizationService.findOrganizationById(organizationId);
        return ResponseEntity.ok(SuccessResponseDto.of("Org", organizationDto));
    }

    @PutMapping("/{organizationId}")
    public ResponseEntity<SuccessResponseDto> updateOrganization(@PathVariable int organizationId,
                                                                 @RequestBody OrganizationDto organizationDto) {
        OrganizationDto organizationDto1 = organizationService.updateOrganization(organizationId, organizationDto);

        return ResponseEntity.ok(SuccessResponseDto.of("Org updated", organizationDto1));
    }


}
