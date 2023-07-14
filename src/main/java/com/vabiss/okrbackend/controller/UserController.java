package com.vabiss.okrbackend.controller;

import com.vabiss.okrbackend.dto.ResetPasswordDto;
import com.vabiss.okrbackend.dto.SuccessResponseDto;
import com.vabiss.okrbackend.dto.UserDto;
import com.vabiss.okrbackend.entity.User;
import com.vabiss.okrbackend.service.inter.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PutMapping("/reset-password")
    public ResponseEntity<SuccessResponseDto> updatePassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        return ResponseEntity.ok(SuccessResponseDto.of(userService.updatePassword(resetPasswordDto.getVerificationToken(), resetPasswordDto.getNewPassword())));
    }

    @PutMapping("/{userId}/displayname")
    public ResponseEntity<SuccessResponseDto> updateDisplayName(@PathVariable int userId,
                                                                @RequestBody Map<String, String> displayName) {
        User user = userService.updateDisplayName(userId, displayName.get("displayName"));
        UserDto userDto = userService.convertToUserDto(user);
        return ResponseEntity.ok(SuccessResponseDto.of("Display name updated!", userDto));
    }

}
