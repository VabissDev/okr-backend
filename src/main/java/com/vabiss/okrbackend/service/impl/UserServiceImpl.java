package com.vabiss.okrbackend.service.impl;

import com.vabiss.okrbackend.dto.RoleDto;
import com.vabiss.okrbackend.dto.UserDto;
import com.vabiss.okrbackend.entity.Role;
import com.vabiss.okrbackend.entity.User;
import com.vabiss.okrbackend.exception.UserAlreadyExistsException;
import com.vabiss.okrbackend.registration.RegistrationRequest;
import com.vabiss.okrbackend.registration.token.VerificationToken;
import com.vabiss.okrbackend.registration.token.VerificationTokenRepository;
import com.vabiss.okrbackend.repository.RoleRepository;
import com.vabiss.okrbackend.repository.UserRepository;
import com.vabiss.okrbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    //    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;


    @Override
    public void addUser(UserDto userDto) {

    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();

    }

    @Override
    public User registerUser(RegistrationRequest request) {

        Optional<User> user = this.findByEmail(request.getEmail());



        if (user.isPresent()) {
            throw new UserAlreadyExistsException(
                    "User with email " + request.getEmail() + " already exists");
        }
        Role role = roleRepository.findRoleByRoleName("MEMBER");
        User newUser = new User();
        newUser.setFullName(request.getFullName());
        newUser.setEmail(request.getEmail());
        newUser.setRoles(List.of(role));

//        newUser.setPassword(passwordEncoder.encode(request.password()));
        System.out.println("hi");

        return userRepository.save(newUser);

    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUserVerificationToken(User user, String token) {
        var verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
    }

//    public UserServiceImpl(UserRepository userRepository){
//        this.userRepository=userRepository;
//    }
//    private final String emailRegex = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
//    private final String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{8,20}$";
//
//    @Override
//    public void addUser(UserDto userDto) {
//        if(!isValidEmail(userDto.getEmail())){
//            throw new IllegalStateException("Email is not valid");
//        }
//        if(!isValidPassword(userDto.getPassword())){
//            throw new IllegalStateException("Password is not valid");
//        }
//        if(userRepository.findByEmail(userDto.getEmail()).isPresent()){
//            throw new IllegalStateException("Email has already taken.");
//        }
//        User user = new User();
//        user.setEmail(userDto.getEmail());
//        user.setPassword(user.getPassword());
//        user.setFullName(user.getFullName());
//        userRepository.save(user);
//    }
//    private boolean isValidEmail(String email){
//        return email.matches(emailRegex);
//    }
//    private boolean isValidPassword(String password){
//        return password.matches(passwordRegex);
//    }


}
