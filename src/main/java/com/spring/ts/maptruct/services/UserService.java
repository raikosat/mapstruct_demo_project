package com.spring.ts.maptruct.services;

import com.spring.ts.maptruct.dtos.UserDTO;
import com.spring.ts.maptruct.entities.User;

public interface UserService extends BaseService<UserDTO, User> {

  public UserDTO findUserByUserId(Long userId);
  public UserDTO existsByUsername(String username);
  public UserDTO existsByEmail(String email);
  public boolean register(UserDTO userDTO);
}
