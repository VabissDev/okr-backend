

package com.vabiss.okrbackend.service;

import com.vabiss.okrbackend.dto.*;
import com.vabiss.okrbackend.entity.Organization;
import com.vabiss.okrbackend.entity.Role;
import com.vabiss.okrbackend.entity.User;
import com.vabiss.okrbackend.repository.OrganizationRepository;
import com.vabiss.okrbackend.repository.RoleRepository;
import com.vabiss.okrbackend.repository.UserRepository;
import com.vabiss.okrbackend.service.EmailService;
import com.vabiss.okrbackend.service.JwtService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OrganizationRepository organizationRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthenticationResponse save(RegistrationDto registrationDto) {
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        Organization organization = new Organization(registrationDto.getOrganizationName());
        organizationRepository.save(organization);

        Role role1 = new Role("USER");
        Role role2 = new Role("ADMIN");
        Role role3 = new Role("LEADER");
        Role role4 = new Role("MEMBER");
        Role role5 = new Role("VIEWER");
        List<Role> roles = checkRolesExist(List.of(role1, role2, role3, role4, role5));

        User user = User.builder()
                .email(registrationDto.getEmail())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .fullName(registrationDto.getFullName())
                .roles(roles)
                .organization(organization)
                .isOrganization(true)
                .enabled(false).build();
        userRepository.save(user);

        MimeMessage mimeMessage = emailService.createEmail(user);
        emailService.sendEmail(mimeMessage);

        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(token).build();
    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        User user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(token).build();
    }

    public List<Role> checkRolesExist(List<Role> roles) {
        List<Role> checkedRoles = new ArrayList<>();
        for (Role role : roles) {
            Role tempRole = roleRepository.findRoleByRoleName(role.getRoleName());
            if (tempRole == null) {
                roleRepository.save(role);
                checkedRoles.add(role);
            } else {
                checkedRoles.add(tempRole);
            }
        }
        return checkedRoles;
    }

}
