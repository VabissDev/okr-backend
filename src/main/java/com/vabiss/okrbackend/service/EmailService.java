package com.vabiss.okrbackend.service;

import com.vabiss.okrbackend.entity.User;
import com.vabiss.okrbackend.entity.VerificationToken;
import com.vabiss.okrbackend.exception.VerificationTokenExpiredException;
import com.vabiss.okrbackend.repository.UserRepository;
import com.vabiss.okrbackend.repository.VerificationTokenRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    @Async
    public void sendEmail(MimeMessage email) {
        javaMailSender.send(email);
    }

    public MimeMessage createEmail(User user) {
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

        return mimeMessage;
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
