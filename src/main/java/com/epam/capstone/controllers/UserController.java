package com.epam.capstone.controllers;

import com.epam.capstone.dto.UserDto;
import com.epam.capstone.services.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

//    @GetMapping(value = "/users/{user_id}")
//    public UserDto getUserById()
}
