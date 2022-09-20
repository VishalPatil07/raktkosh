package com.raktkosh.request.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SigninDTO {
  @NotBlank(message = "Username must not be blank")
  private String username;
  
  @NotBlank(message = "Password must not be blank")
  private String password;
}
