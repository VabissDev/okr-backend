package com.vabiss.okrbackend.service;

import com.vabiss.okrbackend.dto.UserDto;
import com.vabiss.okrbackend.entity.User;
import com.vabiss.okrbackend.entity.VerificationToken;
import com.vabiss.okrbackend.exception.ResourceNotFoundException;
import com.vabiss.okrbackend.repository.UserRepository;
import com.vabiss.okrbackend.repository.VerificationTokenRepository;
import com.vabiss.okrbackend.service.inter.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public String updatePassword(String verificationToken, String newPassword) {
        VerificationToken token = verificationTokenRepository.findByToken(verificationToken);
        if (token == null) {
            throw new RuntimeException("Invalid token!");
        }
        User user = token.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        verificationTokenRepository.delete(token);

        return "Password updated!";
    }

    @Override
    public User updateDisplayName(int userId, String newDisplayName) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new ResourceNotFoundException("User not found - " + userId);
        }
        User user = userRepository.findById(userId).get();
        user.setFullName(newDisplayName);

        return userRepository.save(user);
    }

    @Override
    public User updateAvatar(int userId, String newAvatar) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new ResourceNotFoundException("User not found - " + userId);
        }
        User user = userRepository.findById(userId).get();
        user.setAvatar(newAvatar);

        return userRepository.save(user);
    }

    @Override
    public UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

}
