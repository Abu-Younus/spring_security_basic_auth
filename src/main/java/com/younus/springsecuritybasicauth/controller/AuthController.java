package com.younus.springsecuritybasicauth.controller;

import com.younus.springsecuritybasicauth.domain.UserDto;
import com.younus.springsecuritybasicauth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "auth/registration";
    }

    @PostMapping("/register")
    public String saveUser(@Valid  @ModelAttribute("user") UserDto userDto, BindingResult bindingResult) {
        return userService.saveUser(userDto, bindingResult);
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
}
