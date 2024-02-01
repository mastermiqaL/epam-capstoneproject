package com.epam.capstone.controllers;

import com.epam.capstone.controllers.utils.UserUtils;
import com.epam.capstone.dto.UserBasicDto;
import com.epam.capstone.dto.UserBasicDtoMapper;
import com.epam.capstone.dto.UserDto;
import com.epam.capstone.dto.UserDtoMapper;
import com.epam.capstone.entities.User;
import com.epam.capstone.security.CustomUserDetails;
import com.epam.capstone.services.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();



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
