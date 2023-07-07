package com.vabiss.okrbackend.service;

import com.vabiss.okrbackend.dto.UserDto;
import com.vabiss.okrbackend.dto.UserRequest;
import com.vabiss.okrbackend.dto.UserResponse;
import com.vabiss.okrbackend.entity.User;
import com.vabiss.okrbackend.enums.RoleEnum;
import com.vabiss.okrbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    public UserResponse save(UserDto userDto) {
        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .fullName(userDto.getFullName()).build();
        userRepository.save(user);
        var token = jwtService.generateToken(user);
        return UserResponse.builder().token(token).build();

    }

    public UserResponse auth(UserRequest userRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));
        User user = userRepository.findByEmail(userRequest.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);
        return UserResponse.builder().token(token).build();
    }

}
