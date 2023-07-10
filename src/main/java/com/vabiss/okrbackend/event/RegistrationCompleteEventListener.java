package com.vabiss.okrbackend.event;

import com.vabiss.okrbackend.entity.User;
import com.vabiss.okrbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {


    private final UserService userService;

//    private final JavaMailSender mailSender;
    private User user;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // registered user
        user = event.getUser();
        //Create verification token
        String verificationToken = UUID.randomUUID().toString();
        //Save token
        userService.saveUserVerificationToken(user, verificationToken);
        //verification url to be sent
        String url = event.getApplicationUrl()+"/register/verifyEmail?token="+verificationToken;
        //Send the email
//        try {
//            sendVerificationEmail(url);
//        } catch (MessagingException | UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
        log.info("Click the link to verify your registration :  {}", url);
    }
}
