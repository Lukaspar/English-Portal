package com.lukaspar.ep.service;

import com.lukaspar.ep.dto.UserDto;
import com.lukaspar.ep.exception.RoleNotFoundException;
import com.lukaspar.ep.exception.UserAlreadyExistsException;
import com.lukaspar.ep.mapper.UserMapper;
import com.lukaspar.ep.model.Role;
import com.lukaspar.ep.model.User;
import com.lukaspar.ep.repository.RoleRepository;
import com.lukaspar.ep.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

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

}
