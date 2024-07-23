package com.example.hotel.controller;

import com.example.hotel.dto.UserDto;
import com.example.hotel.service.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public String registerUser(@RequestBody UserDto userDto) {
        if (authenticationService.findByUsername(userDto.getUsername()).isPresent()) {
            return "Username already exists!";
        }
        authenticationService.save(userDto);
        return "User registered successfully!";
    }
}
