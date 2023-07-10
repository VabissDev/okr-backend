package com.vabiss.okrbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vabiss.okrbackend.dto.UserDto;
import com.vabiss.okrbackend.entity.User;
import com.vabiss.okrbackend.event.RegistrationCompleteEvent;
import com.vabiss.okrbackend.registration.RegistrationRequest;
import com.vabiss.okrbackend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {
    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final ObjectMapper objectMapper;



    @PostMapping
    public String registerUser(@RequestBody RegistrationRequest registrationRequest, final HttpServletRequest request) {
        User user = objectMapper.convertValue(userService.registerUser(registrationRequest), User.class);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return "Success!  Please, check your email for to complete your registration";
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
