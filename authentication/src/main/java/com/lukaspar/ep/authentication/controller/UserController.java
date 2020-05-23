package com.lukaspar.ep.authentication.controller;

import com.lukaspar.ep.authentication.dto.*;
import com.lukaspar.ep.authentication.model.User;
import com.lukaspar.ep.authentication.service.UserService;
import com.lukaspar.ep.common.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping("profile")
    public ResponseEntity<UserProfileDto> getUserProfile(@AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(userService.getUserProfile(principal.getUsername()));
    }

    @PutMapping("profile")
    public ResponseEntity<Void> updateUserProfile(@RequestBody UserProfileDto userProfileDto, @AuthenticationPrincipal UserPrincipal principal){
        userService.updateUserProfile(principal.getUsername(), userProfileDto);
        return ResponseEntity.noContent().build();
    }

}
