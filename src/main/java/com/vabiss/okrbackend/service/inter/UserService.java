package com.vabiss.okrbackend.service.inter;

import com.vabiss.okrbackend.dto.UserDto;
import com.vabiss.okrbackend.dto.UserFormDto;
import com.vabiss.okrbackend.entity.User;

import java.util.List;

public interface UserService {

    List<User> findUsersByWorkspaceId(int workspaceId);

    String updatePassword(String verificationToken, String newPassword);

    User updateDisplayName(int userId, String newDisplayName);

    User updateAvatar(int userId, String newAvatar);

    UserDto convertToUserDto(User user);

    User getById(int userId);

    User save(User user);

    void deleteTeamMemberAndViewer(int userId, int workspaceId);

    User addTeamMemberAndViewer(int userId, int workspaceId);

    User createUser(int organizationId, UserFormDto userFormDto);

    UserFormDto convertToUserFormDto(User user);

//    User updateUser(UserDto userDto, int userId);

    void deleteUser(int userId);
}
