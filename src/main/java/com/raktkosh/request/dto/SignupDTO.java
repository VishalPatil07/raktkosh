package com.raktkosh.request.dto;

import java.time.LocalDate;

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
public class SignupDTO extends SigninDTO {
  @NotBlank(message = "Fullname is required.")
  private String name;
  
  @NotBlank(message = "Valid email is required.")
  private String email;
  
  //@Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
  private LocalDate dob;
  private String role;
}
