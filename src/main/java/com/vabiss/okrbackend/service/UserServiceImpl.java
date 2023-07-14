package com.vabiss.okrbackend.service;

import com.vabiss.okrbackend.entity.User;
import com.vabiss.okrbackend.entity.VerificationToken;
import com.vabiss.okrbackend.repository.UserRepository;
import com.vabiss.okrbackend.repository.VerificationTokenRepository;
import com.vabiss.okrbackend.service.inter.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

}
