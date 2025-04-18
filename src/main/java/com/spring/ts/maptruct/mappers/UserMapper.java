package com.spring.ts.maptruct.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.spring.ts.maptruct.dtos.UserDTO;
import com.spring.ts.maptruct.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDTO, User> {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
}
