package com.vabiss.okrbackend.service.inter;

import com.vabiss.okrbackend.dto.OrganizationDto;
import com.vabiss.okrbackend.entity.Organization;

public interface OrganizationService {

    OrganizationDto updateOrganization(int organizationId, OrganizationDto organizationDto);

    OrganizationDto convertToOrganizationDto(Organization organization);

    Organization convertToOrganization(OrganizationDto organizationDto);

}
