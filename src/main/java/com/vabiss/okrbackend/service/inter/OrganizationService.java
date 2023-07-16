package com.vabiss.okrbackend.service.inter;

import com.vabiss.okrbackend.dto.OrganizationDto;
import com.vabiss.okrbackend.entity.Organization;

public interface OrganizationService {

    Organization updateAvatar(int organizationId, String avatar);

    OrganizationDto convertToOrganizationDto(Organization organization);

}
