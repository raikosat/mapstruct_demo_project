package com.spring.ts.maptruct.mappers;

import com.spring.ts.maptruct.dtos.UserDTO;
import com.spring.ts.maptruct.entities.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-18T15:55:31+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setCreatedAt( dto.getCreatedAt() );
        user.setUpdatedAt( dto.getUpdatedAt() );
        user.setUsername( dto.getUsername() );
        user.setEmail( dto.getEmail() );
        user.setPassword( dto.getPassword() );

        return user;
    }

    @Override
    public UserDTO toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setCreatedAt( entity.getCreatedAt() );
        userDTO.setUpdatedAt( entity.getUpdatedAt() );
        userDTO.setUsername( entity.getUsername() );
        userDTO.setEmail( entity.getEmail() );
        userDTO.setPassword( entity.getPassword() );

        return userDTO;
    }
}
