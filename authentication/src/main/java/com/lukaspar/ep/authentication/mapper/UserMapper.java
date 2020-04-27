package com.lukaspar.ep.mapper;

import com.lukaspar.ep.dto.UserDto;
import com.lukaspar.ep.model.Role;
import com.lukaspar.ep.model.User;
import com.lukaspar.ep.common.security.UserPrincipal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User userDtoToUser(UserDto userDto);

    @Mapping(target = "authorities", expression = "mapUserAuthorities(user.getRoles())")
    UserPrincipal userToUserPrincipal(User user);

    default Set<GrantedAuthority> mapUserAuthorities(Set<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
    }

}
