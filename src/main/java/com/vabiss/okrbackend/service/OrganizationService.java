package com.vabiss.okrbackend.service;

import com.vabiss.okrbackend.dto.OrganizationDto;
import com.vabiss.okrbackend.entity.Organization;
import com.vabiss.okrbackend.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

@Service
public interface OrganizationService {
    void addOrganization(OrganizationDto organizationDto);
}
