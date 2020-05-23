package com.lukaspar.ep.authentication.controller;

import com.lukaspar.ep.authentication.model.User;
import com.lukaspar.ep.authentication.service.FriendService;
import com.lukaspar.ep.common.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.lukaspar.ep.authentication.util.FriendshipStatus.ACCEPTED;
import static com.lukaspar.ep.authentication.util.FriendshipStatus.REJECTED;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication-module/friends")
public class FriendController {

    private final FriendService friendService;

    @PostMapping("sendFriendRequest")
    public ResponseEntity<Long> sendFriendRequest(@RequestParam String friend, @AuthenticationPrincipal UserPrincipal principal) {
        User user = friendService.sendFriendRequest(friend, principal.getUsername());
        return ResponseEntity.status(CREATED).body(user.getId());
    }

    @GetMapping("friendRequests")
    public ResponseEntity<Set<String>> getFriendRequests(@AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(friendService.getFriendsRequests(principal.getUsername()));
    }

    @PutMapping("acceptFriendRequest")
    public ResponseEntity<Void> acceptFriendRequest(@RequestParam String friend, @AuthenticationPrincipal UserPrincipal principal){
        friendService.acceptOrRejectFriendRequest(friend, principal.getUsername(), ACCEPTED);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("rejectFriendRequest")
    public ResponseEntity<Void> rejectFriendRequest(@RequestParam String friend, @AuthenticationPrincipal UserPrincipal principal){
        friendService.acceptOrRejectFriendRequest(friend, principal.getUsername(), REJECTED);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/acceptedFriends")
    public ResponseEntity<Set<String>> getUserFriends(@AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(friendService.getUserFriends(principal.getUsername()));
    }
}
