package com.lukaspar.ep.controller;

import com.lukaspar.ep.dto.UserDto;
import com.lukaspar.ep.model.User;
import com.lukaspar.ep.common.security.UserPrincipal;
import com.lukaspar.ep.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
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
    public UserPrincipal getCurrentUser(){
        return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
