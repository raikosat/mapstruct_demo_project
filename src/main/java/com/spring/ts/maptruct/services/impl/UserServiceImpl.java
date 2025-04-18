package com.spring.ts.maptruct.services.impl;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.spring.ts.maptruct.dtos.UserDTO;
import com.spring.ts.maptruct.entities.User;
import com.spring.ts.maptruct.helper.ServiceHelper;
import com.spring.ts.maptruct.mappers.UserMapper;
import com.spring.ts.maptruct.repositories.UserRepository;
import com.spring.ts.maptruct.services.UserService;

@Service
@ServiceHelper
public class UserServiceImpl extends BaseServiceImpl<UserRepository, UserMapper, UserDTO, User>
    implements UserService {

  @Override
  public UserDTO findUserByUserId(Long userId) {
    Optional<User> user = repository.findByUserId(userId);
    if (user.isPresent()) {
      return getMapper().toDto(user.get());
    }
    return null;
  }

  @Override
  public UserDTO existsByUsername(String username) {
    Optional<User> user = repository.findByUsername(username);
    if (user.isPresent()) {
      return getMapper().toDto(user.get());
    }
    return null;
  }

  @Override
  public UserDTO existsByEmail(String email) {
    Optional<User> user = repository.findByEmail(email);
    if (user.isPresent()) {
      return getMapper().toDto(user.get());
    }
    return null;
  }

  @Override
  public boolean register(UserDTO userDTO) {
    // Check if the username already exists
    boolean userExists =
        ((UserRepository) repository).findByUsername(userDTO.getUsername()).isPresent();
    if (userExists) {
      return false; // Username already taken
    }
    // Save the new user
    User user = mapper.toEntity(userDTO);
    this.repository.save(user);
    return true; // Registration successful
  }
}
