package com.lukaspar.ep.authentication.controller;

import com.lukaspar.ep.authentication.dto.FriendRequest;
import com.lukaspar.ep.authentication.dto.UserDto;
import com.lukaspar.ep.authentication.model.User;
import com.lukaspar.ep.authentication.service.UserService;
import com.lukaspar.ep.common.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Set;

import static com.lukaspar.ep.authentication.util.FriendshipStatus.ACCEPTED;
import static com.lukaspar.ep.authentication.util.FriendshipStatus.REJECTED;
import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<Long> createUser(@Valid @RequestBody UserDto userDto){
        User user = userService.registerUser(userDto);
        return ResponseEntity.status(CREATED).body(user.getId());
    }

    @GetMapping("current")
    public UserPrincipal currentUser(@AuthenticationPrincipal UserPrincipal principal){
        return principal;
    }

    @PostMapping("sendFriendRequest")
    public ResponseEntity<Long> sendFriendRequest(@Valid @RequestBody FriendRequest friendRequest){
        User user = userService.sendFriendRequest(friendRequest);
        return ResponseEntity.status(CREATED).body(user.getId());
    }

    @GetMapping("{userId}/friendRequests")
    public ResponseEntity<Set<String>> getFriendRequests(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getFriendsRequests(userId));
    }

    @PutMapping("acceptFriendRequest")
    public ResponseEntity<Void> acceptFriendRequest(@Valid @RequestBody FriendRequest friendRequest){
        userService.acceptOrRejectFriendRequest(friendRequest, ACCEPTED);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("rejectFriendRequest")
    public ResponseEntity<Void> rejectFriendRequest(@Valid @RequestBody FriendRequest friendRequest){
        userService.acceptOrRejectFriendRequest(friendRequest, REJECTED);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{userId}/friends")
    public ResponseEntity<Set<String>> getUserFriends(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserFriends(userId));
    }

}
