package com.vabiss.okrbackend.service;

import com.vabiss.okrbackend.dto.UserDto;
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

    public MimeMessage createEmail(User user, String subject, String msg, String link) {
        VerificationToken verificationToken = new VerificationToken(user);
        verificationTokenRepository.save(verificationToken);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = msg + link + verificationToken.getToken();
        // http://localhost:8080
        // https://okr-backend-vabiss-c66783e088f5.herokuapp.com
        try {
            helper.setText(htmlMsg, true);
            helper.setTo(user.getUsername());
            helper.setSubject(subject);
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

        MimeMessage mimeMessage = createEmail(user,
                "Complete Registration!",
                "<h3>To confirm your account, please click here : </h3>",
                "https://okr-backend-vabiss-c66783e088f5.herokuapp.com/email-confirm?token=");
        sendEmail(mimeMessage);
        return "Token resend!";
    }

    public String createResetPasswordEmail(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail()).orElseThrow();
        if (user.getVerificationToken() != null) {
            verificationTokenRepository.delete(user.getVerificationToken());
        }
        MimeMessage mimeMessage = createEmail(user,
                "Reset password",
                "<h3>Reset passowrd link : </h3>",
                "https://vabiss-okr.vercel.app/reset-passowrd?token=");
        sendEmail(mimeMessage);
        return "Reset password email sent!";
    }

    public void sendCreatedUserDetails(User user, String subject, String msg, String link) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String htmlMsg = msg + link;
        try {
            helper.setText(htmlMsg, true);
            helper.setTo(user.getUsername());
            helper.setSubject(subject);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        sendEmail(mimeMessage);
    }

}
