package com.lukaspar.ep.authentication.service;

import com.lukaspar.ep.authentication.exception.UserNotFoundException;
import com.lukaspar.ep.authentication.model.User;
import com.lukaspar.ep.authentication.repository.UserRepository;
import com.lukaspar.ep.authentication.util.FriendshipStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final UserRepository userRepository;

    @Transactional
    public User sendFriendRequest(String friendUsername, String loggedUserUsername) {
        User owner = userRepository.findByUsername(loggedUserUsername).orElseThrow(() -> new UserNotFoundException(loggedUserUsername));
        User friend = userRepository.findByUsername(friendUsername).orElseThrow(() -> new UserNotFoundException(friendUsername));
        owner.addFriendRequest(friend);
        return userRepository.saveAndFlush(owner);
    }

    public Set<String> getFriendsRequests(String username) {
        User loggedUser = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return loggedUser.getFriendsRequests();
    }

    @Transactional
    public void acceptOrRejectFriendRequest(String friendUsername, String loggedUserUsername, FriendshipStatus status) {
        User owner = userRepository.findByUsername(loggedUserUsername).orElseThrow(() -> new UserNotFoundException(loggedUserUsername));
        User friend = userRepository.findByUsername(friendUsername).orElseThrow(() -> new UserNotFoundException(friendUsername));
        owner.acceptOrRejectFriendRequest(friend, status);
        userRepository.saveAndFlush(owner);
    }

    public Set<String> getUserFriends(String loggedUserUsername) {
        User owner = userRepository.findByUsername(loggedUserUsername).orElseThrow(() -> new UserNotFoundException(loggedUserUsername));
        return owner.getAllFriends();
    }
}
