package com.vabiss.okrbackend.service;

import com.vabiss.okrbackend.dto.UserDto;
import com.vabiss.okrbackend.dto.UserFormDto;
import com.vabiss.okrbackend.entity.Role;
import com.vabiss.okrbackend.entity.User;
import com.vabiss.okrbackend.entity.VerificationToken;
import com.vabiss.okrbackend.entity.Workspace;
import com.vabiss.okrbackend.exception.CurrentStateResourceException;
import com.vabiss.okrbackend.exception.ResourceNotFoundException;
import com.vabiss.okrbackend.repository.*;
import com.vabiss.okrbackend.service.inter.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Builder
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final OrganizationRepository organizationRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;

    @Override
    public List<User> findUsersByWorkspaceId(int workspaceId) {
        if (workspaceRepository.findById(workspaceId).isEmpty()) {
            throw new ResourceNotFoundException("Workspace not found - " + workspaceId);
        }
        Workspace workspace = workspaceRepository.findById(workspaceId).get();
        return workspace.getUsers();
    }

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

    @Override
    public User updateFullName(int userId, String newFullName) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new ResourceNotFoundException("User not found - " + userId);
        }
        User user = userRepository.findById(userId).get();
        user.setFullName(newFullName);

        return userRepository.save(user);
    }

    @Override
    public User updateAvatar(int userId, String newAvatar) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new ResourceNotFoundException("User not found - " + userId);
        }
        User user = userRepository.findById(userId).get();
        user.setAvatar(newAvatar);

        return userRepository.save(user);
    }

    @Override
    public UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getById(int userId) {
        return userRepository.findById(userId).get();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteTeamMemberAndViewer(int userId, int workspaceId) {
        userRepository.deleteMemberAndUser(userId, workspaceId);

    }

    @Override
    public User addTeamMemberAndViewer(int userId, int workspaceId) {

        User user = userRepository.getById(userId);
        Workspace workspace = workspaceRepository.getById(workspaceId);
        List<Workspace> workspaceList = user.getWorkspaces();
        workspaceList.add(workspace);

        user.setWorkspaces(workspaceList);
        return userRepository.save(user);
    }

    @Override
    public User createUser(int organizationId, UserFormDto userFormDto) {
        if (userRepository.findByEmail(userFormDto.getEmail()).isEmpty()) {
            throw new CurrentStateResourceException("Email is already in use!");
        }
        List<String> rolesStr = new ArrayList<>(List.of("ADMIN", "LEADER", "MEMBER", "VIEWER", "USER"));
        String userRole = userFormDto.getRole().toUpperCase(Locale.ENGLISH);
        List<Role> roles = rolesStr.stream()
                .skip(rolesStr.indexOf(userRole))
                .map(roleRepository::findRoleByRoleName)
                .toList();

        String password = UUID.randomUUID().toString();

        User user = User.builder()
                .fullName(userFormDto.getFullName())
                .email(userFormDto.getEmail())
                .password(passwordEncoder.encode(password))
                .enabled(true)
                .isOrganization(false)
                .organization(organizationRepository.findById(organizationId).get())
                .roles(roles)
                .build();

        emailService.sendCreatedUserDetails(user,
                "OKR information",
                "<p>Email: " + user.getEmail() + "</p>" + "<p>Password: " + password + "</p>",
                "https://vabiss-okr.vercel.app/login");

        return userRepository.save(user);
    }

    @Override
    public UserFormDto convertToUserFormDto(User user) {
        return modelMapper.map(user, UserFormDto.class);
    }

    @Override
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }


//    @Override
//    public User updateUser(UserDto userDto, int userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found - " + userId));
//
//        user.setFullName(userDto.getFullName());
//        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        return userRepository.save(user);
//    }
}
