package com.lukaspar.ep.authentication.service;

import com.lukaspar.ep.authentication.dto.FriendRequest;
import com.lukaspar.ep.authentication.dto.UserDto;
import com.lukaspar.ep.authentication.dto.UserProfileDto;
import com.lukaspar.ep.authentication.exception.RoleNotFoundException;
import com.lukaspar.ep.authentication.exception.UserNotFoundException;
import com.lukaspar.ep.authentication.repository.RoleRepository;
import com.lukaspar.ep.authentication.exception.UserAlreadyExistsException;
import com.lukaspar.ep.authentication.mapper.UserMapper;
import com.lukaspar.ep.authentication.model.Role;
import com.lukaspar.ep.authentication.model.User;
import com.lukaspar.ep.authentication.repository.UserRepository;
import com.lukaspar.ep.authentication.util.FriendshipStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
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

    public UserProfileDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return userMapper.userToUserProfileDto(user);
    }

    @Transactional
    public void updateUserProfile(Long userId, UserProfileDto userProfileDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        userMapper.updateUserFromUserProfileDto(userProfileDto, user);
        userRepository.saveAndFlush(user);
    }

    @Transactional
    public User sendFriendRequest(FriendRequest friendRequest) {
        User owner = userRepository.findById(friendRequest.getOwnerId()).orElseThrow(() -> new UserNotFoundException(friendRequest.getOwnerId()));
        User friend = userRepository.findById(friendRequest.getFriendId()).orElseThrow(() -> new UserNotFoundException(friendRequest.getFriendId()));
        owner.addFriendRequest(friend);
        return userRepository.saveAndFlush(owner);
    }

    public Set<String> getFriendsRequests(Long userId) {
        User owner = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return owner.getFriendsRequests();
    }

    @Transactional
    public void acceptOrRejectFriendRequest(FriendRequest friendRequest, FriendshipStatus status) {
        User owner = userRepository.findById(friendRequest.getOwnerId()).orElseThrow(() -> new UserNotFoundException(friendRequest.getOwnerId()));
        User friend = userRepository.findById(friendRequest.getFriendId()).orElseThrow(() -> new UserNotFoundException(friendRequest.getFriendId()));
        owner.acceptOrRejectFriendRequest(friend, status);
        userRepository.saveAndFlush(owner);
    }

    public Set<String> getUserFriends(Long userId) {
        User owner = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return owner.getAllFriends();
    }
}
