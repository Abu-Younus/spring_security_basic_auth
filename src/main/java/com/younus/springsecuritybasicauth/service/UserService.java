package com.younus.springsecuritybasicauth.service;

import com.younus.springsecuritybasicauth.domain.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;

public interface UserService extends UserDetailsService {
    String saveUser(UserDto userDto, BindingResult bindingResult);
}
