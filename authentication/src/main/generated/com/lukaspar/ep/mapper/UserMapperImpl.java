package com.lukaspar.ep.mapper;

import com.lukaspar.ep.dto.UserDto;
import com.lukaspar.ep.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-04-26T22:43:15+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 13.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User userDtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( userDto.getUsername() );

        return user;
    }
}
