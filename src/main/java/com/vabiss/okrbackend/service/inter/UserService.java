package com.vabiss.okrbackend.service.inter;

import com.vabiss.okrbackend.dto.UserDto;
import com.vabiss.okrbackend.entity.User;

public interface UserService {

    String updatePassword(String verificationToken, String newPassword);

    User updateDisplayName(int userId, String newDisplayName);

    UserDto convertToUserDto(User user);

}
