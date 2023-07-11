package com.vabiss.okrbackend.controller;

import com.vabiss.okrbackend.dto.AuthenticationRequest;
import com.vabiss.okrbackend.dto.AuthenticationResponse;
import com.vabiss.okrbackend.dto.OrganizationDto;
import com.vabiss.okrbackend.dto.UserDto;
import com.vabiss.okrbackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/save")
    public ResponseEntity<AuthenticationResponse> save(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(authenticationService.save(userDto));
    }

    @PostMapping("/save/organization")
    public ResponseEntity<AuthenticationResponse> saveOrganization(@RequestBody OrganizationDto organizationDto) {
        return ResponseEntity.ok(authenticationService.saveOrganization(organizationDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> auth(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.login(authenticationRequest));
    }

}
