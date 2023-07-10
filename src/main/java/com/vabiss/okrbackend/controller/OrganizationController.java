package com.vabiss.okrbackend.controller;

import com.vabiss.okrbackend.dto.OrganizationDto;
import com.vabiss.okrbackend.service.OrganizationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/organization")
public class OrganizationController {
    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }
//    @PostMapping("register")
//    public void addOrganization(@RequestBody OrganizationDto organizationDto){
//        organizationService.addOrganization(organizationDto);
//    }
}
