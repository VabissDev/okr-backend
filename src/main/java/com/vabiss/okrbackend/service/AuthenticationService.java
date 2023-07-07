package com.vabiss.okrbackend.service;

import com.vabiss.okrbackend.dto.UserDto;
import com.vabiss.okrbackend.dto.UserRequest;
import com.vabiss.okrbackend.dto.UserResponse;
import com.vabiss.okrbackend.entity.Role;
import com.vabiss.okrbackend.entity.User;
import com.vabiss.okrbackend.repository.RoleRepository;
import com.vabiss.okrbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public UserResponse save(UserDto userDto) {
        Role role = roleRepository.findRoleByRoleName("USER");
        if (role == null) {
            role = new Role("USER");
            roleRepository.save(role);
        }

        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .fullName(userDto.getFullName())
                .roles(List.of(role)).build();
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
