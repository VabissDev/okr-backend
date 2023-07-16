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
    public OrganizationDto updateOrganization(int organizationId, OrganizationDto organizationDto) {
        if (organizationRepository.findById(organizationId).isEmpty()) {
            throw new ResourceNotFoundException("Organization not found - " + organizationId);
        }
        organizationDto.setId(organizationId);
        Organization organization = convertToOrganization(organizationDto);
        organizationRepository.save(organization);

        return convertToOrganizationDto(organization);
    }

    @Override
    public OrganizationDto convertToOrganizationDto(Organization organization) {
        return modelMapper.map(organization, OrganizationDto.class);
    }

    @Override
    public Organization convertToOrganization(OrganizationDto organizationDto) {
        return modelMapper.map(organizationDto, Organization.class);
    }

}
