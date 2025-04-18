package com.spring.ts.maptruct.dtos;

import lombok.Data;

@Data
public class SignupRequestDTO {

  public String username;
  public String email;
  public String password;
}
