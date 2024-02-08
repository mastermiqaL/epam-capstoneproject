package com.epam.capstone.controllers;

import com.epam.capstone.dto.UserBasicDto;
import com.epam.capstone.dto.UserBasicDtoMapper;
import com.epam.capstone.dto.UserDto;
import com.epam.capstone.dto.UserDtoMapper;
import com.epam.capstone.entities.User;
import com.epam.capstone.security.CustomUserDetails;
import com.epam.capstone.services.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {
    private final UserServiceImpl userService;
    private final UserDtoMapper userDtoMapper;
    private final UserBasicDtoMapper userBasicDtoMapper;

    public UserController(UserServiceImpl userService, UserDtoMapper userDtoMapper, UserBasicDtoMapper userBasicDtoMapper) {
        this.userService = userService;
        this.userDtoMapper = userDtoMapper;
        this.userBasicDtoMapper = userBasicDtoMapper;
    }


    @GetMapping(value = "/users/profile/{username}")
    public ResponseEntity<?> getUserInfo(@PathVariable String username) {

        User user = userService.getUserByUsername(username);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof CustomUserDetails)) {
            // Respond with a status code that indicates the client should redirect
            // 401 Unauthorized or 403 Forbidden is typically used in REST APIs
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please, login");
        }
        CustomUserDetails userDetails = (CustomUserDetails) principal;
        if(user!=null){
            if(userDetails.getActualUsername().equals(username)){
                UserDto extendedDTO =userDtoMapper.apply(user);
                return ResponseEntity.ok(extendedDTO);
            }else{
                UserBasicDto limitedDTO = userBasicDtoMapper.apply(user);
                return ResponseEntity.ok(limitedDTO);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
