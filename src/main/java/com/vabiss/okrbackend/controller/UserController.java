package com.vabiss.okrbackend.controller;

import com.vabiss.okrbackend.dto.ResetPasswordDto;
import com.vabiss.okrbackend.dto.SuccessResponseDto;
import com.vabiss.okrbackend.dto.UserDto;
import com.vabiss.okrbackend.entity.User;
import com.vabiss.okrbackend.service.inter.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{userId}/fullname")
    public ResponseEntity<SuccessResponseDto> updateDisplayName(@PathVariable int userId,
                                                                @RequestBody UserDto userDto) {
        User user = userService.updateDisplayName(userId, userDto.getFullName());
        UserDto userDto2 = userService.convertToUserDto(user);
        return ResponseEntity.ok(SuccessResponseDto.of("Display name updated!", userDto2));
    }

    @PutMapping("/{userId}/avatar")
    public ResponseEntity<SuccessResponseDto> updateProfilePhoto(@PathVariable int userId,
                                                                 @RequestBody UserDto userDto) {
        User user = userService.updateAvatar(userId, userDto.getAvatar());
        UserDto userDto2 = userService.convertToUserDto(user);
        return ResponseEntity.ok(SuccessResponseDto.of("Avatar updated!", userDto2));
    }

    @DeleteMapping("/remove/{userId}/{organizationName}")
    public void removeTeamMemberAndViewer(@PathVariable int userId, @PathVariable String organizationName) {
        userService.deleteTeamMemberAndViewer(userId, organizationName);

    }


}
