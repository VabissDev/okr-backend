package com.vabiss.okrbackend.service;

import com.vabiss.okrbackend.dto.UserDto;
import com.vabiss.okrbackend.entity.User;
import com.vabiss.okrbackend.registration.RegistrationRequest;
import com.vabiss.okrbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    void addUser(UserDto userDto);

    List<User> getUsers();
    User registerUser(RegistrationRequest request);
    Optional<User> findByEmail(String email);
    void saveUserVerificationToken(User user, String token);

}
