package com.lukaspar.ep.authentication.mapper;

import com.lukaspar.ep.authentication.dto.UserDto;
import com.lukaspar.ep.authentication.dto.UserProfileDto;
import com.lukaspar.ep.authentication.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User userDtoToUser(UserDto userDto);

    UserProfileDto userToUserProfileDto(User user);

    void updateUserFromUserProfileDto(UserProfileDto userProfileDto, @MappingTarget User user);
}
