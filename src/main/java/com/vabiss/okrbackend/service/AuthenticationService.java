package com.vabiss.okrbackend.service;

import com.vabiss.okrbackend.dto.UserDto;
import com.vabiss.okrbackend.dto.AuthenticationRequest;
import com.vabiss.okrbackend.dto.AuthenticationResponse;
import com.vabiss.okrbackend.entity.Role;
import com.vabiss.okrbackend.entity.User;
import com.vabiss.okrbackend.entity.VerificationToken;
import com.vabiss.okrbackend.exception.VerificationTokenExpiredException;
import com.vabiss.okrbackend.repository.RoleRepository;
import com.vabiss.okrbackend.repository.UserRepository;
import com.vabiss.okrbackend.repository.VerificationTokenRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final EmailService emailService;

    public AuthenticationResponse save(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

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

        createEmail(user);

        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(token).build();
    }

    public AuthenticationResponse auth(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        User user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(token).build();
    }

    public void createEmail(User user) {
        VerificationToken verificationToken = new VerificationToken(user);
        verificationTokenRepository.save(verificationToken);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = "<h3>To confirm your account, please click here : </h3>" +
                "http://localhost:8080/login/confirm-account?token=" + verificationToken.getToken();
        try {
            helper.setText(htmlMsg, true);
            helper.setTo(user.getUsername());
            helper.setSubject("Complete Registration!");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        emailService.sendEmail(mimeMessage);
    }

    public String confirmEmail(String verificationToken) {
        VerificationToken token = verificationTokenRepository.findByToken(verificationToken);

        if(token != null) {

            if (token.getExpiredDate().before(new Date())) {
                throw new VerificationTokenExpiredException("Token expired!");
            }

            User user = userRepository.findByEmail(token.getUser().getEmail()).orElseThrow();
            user.setEnabled(true);
            userRepository.save(user);
            verificationTokenRepository.delete(token);
            return "Email verified successfully!";
        }
        throw new RuntimeException("Error: Couldn't verify email");
    }

    public String resendToken(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        VerificationToken verificationToken = user.getVerificationToken();
        verificationTokenRepository.delete(verificationToken);

        createEmail(user);
        return "Token resend!";
    }

}
