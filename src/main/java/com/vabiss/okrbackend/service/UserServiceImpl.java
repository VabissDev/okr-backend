package com.vabiss.okrbackend.service;

import com.vabiss.okrbackend.dto.UserDto;
import com.vabiss.okrbackend.dto.UserFormDto;
import com.vabiss.okrbackend.entity.*;
import com.vabiss.okrbackend.exception.ResourceNotFoundException;
import com.vabiss.okrbackend.repository.*;
import com.vabiss.okrbackend.service.inter.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public User updateDisplayName(int userId, String newDisplayName) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new ResourceNotFoundException("User not found - " + userId);
        }
        User user = userRepository.findById(userId).get();
        user.setFullName(newDisplayName);

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
    public void deleteTeamMemberAndViewer(int userId, int organizationId) {

        User user = userRepository.getByOrganizationId(userId, organizationId);

        if (user == null) {
            throw new ResourceNotFoundException("You do not have permission to delete this user");
        }
        userRepository.delete(user);

    }

    @Override
    public User addTeamMemberAndViewer(int userId, int organizationId) {
        User user = userRepository.findById(userId).get();
        user.getOrganization().setId(organizationId);
        return userRepository.save(user);
    }

    @Override
    public User createUser(int organizationId, UserFormDto userFormDto) {
        List<Role> roles = roleRepository.findAll();
        List<Role> userRoles = roles.stream()
                .dropWhile(role -> !role.getRoleName().equals(userFormDto.getRoles().get(0).getRoleName()))
                .collect(Collectors.toList());
        return User.builder()
                .fullName(userFormDto.getFullName())
                .email(userFormDto.getFullName())
                .password(userFormDto.getEmail())
                .enabled(true)
                .isOrganization(false)
                .organization(organizationRepository.findById(organizationId).get())
                .roles(userRoles)
                .build();
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
