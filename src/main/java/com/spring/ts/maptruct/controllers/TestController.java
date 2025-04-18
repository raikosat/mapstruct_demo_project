package com.spring.ts.maptruct.controllers;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spring.ts.maptruct.dtos.SignupRequestDTO;
import com.spring.ts.maptruct.dtos.UserDTO;
import com.spring.ts.maptruct.services.UserService;

@RestController
public class TestController {

  @Autowired
  UserService userService;

  @RequestMapping("/find/{userId}")
  ResponseEntity<?> hello(@PathVariable(name = "userId") Long userId) {
    UserDTO userDto = userService.findUserByUserId(userId);
    return ResponseEntity.ok(userDto);
  }

  @PostMapping("/register")
  ResponseEntity<?> register(@RequestBody SignupRequestDTO request) throws BadRequestException {
    if (Objects.nonNull(userService.existsByUsername(request.getUsername()))) {
      return ResponseEntity.badRequest().body("Username already exists");
    }

    if (Objects.nonNull(userService.existsByEmail(request.getEmail()))) {
      return ResponseEntity.badRequest().body("Email already exists");
    }

    UserDTO user = new UserDTO(request.getUsername(), request.getEmail(), request.getPassword());
    user.setCreatedAt(new Date());
    user.setUpdatedAt(new Date());
    userService.register(user);
    return ResponseEntity.ok(Map.of("message", "User registered successfully"));
  }
}
