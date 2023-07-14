package com.vabiss.okrbackend.service.inter;

public interface UserService {

    String updatePassword(String verificationToken, String newPassword);

}
