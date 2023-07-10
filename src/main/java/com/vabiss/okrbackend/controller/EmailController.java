package com.vabiss.okrbackend.controller;

import com.vabiss.okrbackend.dto.UserDto;
import com.vabiss.okrbackend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class EmailController {

    private final EmailService emailService;

    @RequestMapping(value = "/email-confirm", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> confirmUserAccount(@RequestParam("token") String verificationToken) {
        return ResponseEntity.ok(emailService.confirmEmail(verificationToken));
    }

    @PostMapping("/resend-email")
    public ResponseEntity<String> resendToken(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(emailService.resendToken(userDto.getEmail()));
    }

}
