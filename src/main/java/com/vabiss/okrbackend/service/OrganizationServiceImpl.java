package com.vabiss.okrbackend.service;

import com.vabiss.okrbackend.dto.OrganizationDto;
import com.vabiss.okrbackend.entity.Organization;
import com.vabiss.okrbackend.exception.ResourceNotFoundException;
import com.vabiss.okrbackend.repository.OrganizationRepository;
import com.vabiss.okrbackend.service.inter.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final ModelMapper modelMapper;

    @Override
    public Organization updateAvatar(int organizationId, String avatar) {
        if (organizationRepository.findById(organizationId).isEmpty()) {
            throw new ResourceNotFoundException("Organization not found - " + organizationId);
        }
        Organization organization = organizationRepository.findById(organizationId).get();
        organization.setAvatar(avatar);
        return organizationRepository.save(organization);
    }

    @Override
    public OrganizationDto convertToOrganizationDto(Organization organization) {
        return modelMapper.map(organization, OrganizationDto.class);
    }

}
