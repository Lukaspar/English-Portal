package com.lukaspar.ep.authentication.service;

import com.lukaspar.ep.authentication.dto.*;
import com.lukaspar.ep.authentication.exception.*;
import com.lukaspar.ep.authentication.repository.RoleRepository;
import com.lukaspar.ep.authentication.mapper.UserMapper;
import com.lukaspar.ep.authentication.model.Role;
import com.lukaspar.ep.authentication.model.User;
import com.lukaspar.ep.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String DEFAULT_ROLE = "USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(userDto.getUsername());
        }
        User user = userMapper.userDtoToUser(userDto);
        user.setRoles(Set.of(prepareDefaultRole()));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setActive(true);
        return userRepository.saveAndFlush(user);
    }

    private Role prepareDefaultRole() {
        return roleRepository.findByName(DEFAULT_ROLE).orElseThrow(() -> new RoleNotFoundException(DEFAULT_ROLE));
    }

    public UserProfileDto getUserProfile(String username) {
        User loggedUser = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return userMapper.userToUserProfileDto(loggedUser);
    }

    @Transactional
    public void updateUserProfile(String username, UserProfileDto userProfileDto) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        userMapper.updateUserFromUserProfileDto(userProfileDto, user);
        userRepository.saveAndFlush(user);
    }
}
