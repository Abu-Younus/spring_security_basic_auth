package com.younus.springsecuritybasicauth.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotEmpty(message = "The full name is required!")
    private String fullName;

    @NotEmpty(message = "The email is required!")
    @Email
    private String email;

    @NotEmpty(message = "The password is required!")
    @Size(min = 6, max = 60, message = "The password must be between 6 and 100!")
    private String password;
}
