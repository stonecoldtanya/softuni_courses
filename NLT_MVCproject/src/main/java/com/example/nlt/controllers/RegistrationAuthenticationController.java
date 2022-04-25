package com.example.nlt.controllers;

import com.example.nlt.entities.dto.RegistrationDTO;
import com.example.nlt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationAuthenticationController {
    private final UserService userService;

    @Autowired
    public RegistrationAuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/register")
    public String registerView(RegistrationDTO registrationDto) {
        return "user/register";
    }

    @PostMapping(value = "/users/register")
    public String doRegister(@Valid RegistrationDTO registrationDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "user/register";
        }

        this.userService.register(registrationDto);
        return "user/login";
    }
}
