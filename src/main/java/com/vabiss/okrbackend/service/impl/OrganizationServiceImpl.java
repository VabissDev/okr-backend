package com.vabiss.okrbackend.service.impl;

import com.vabiss.okrbackend.dto.OrganizationDto;
import com.vabiss.okrbackend.entity.Organization;
import com.vabiss.okrbackend.entity.User;
import com.vabiss.okrbackend.repository.OrganizationRepository;
import com.vabiss.okrbackend.service.OrganizationService;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final String emailRegex = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{8,20}$";
    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public void addOrganization(OrganizationDto organizationDto) {
        if(!isValidEmail(organizationDto.getEmail())){
            throw new IllegalStateException("Email is not valid");
        }
        if(!isValidPassword(organizationDto.getPassword())){
            throw new IllegalStateException("Password is not valid");
        }
        if(organizationRepository.findByName(organizationDto.getEmail()).isPresent()){
            throw new IllegalStateException("Email has already taken.");
        }
        Organization organization = new Organization();
        organization.setName(organizationDto.getName());
        organization.setEmail(organizationDto.getEmail());
        organizationRepository.save(organization);
    }
    private boolean isValidEmail(String email){
        return email.matches(emailRegex);
    }
    private boolean isValidPassword(String password){
        return password.matches(passwordRegex);
    }
}
