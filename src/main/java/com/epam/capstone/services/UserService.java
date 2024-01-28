package com.epam.capstone.services;

import com.epam.capstone.dto.RegistrationRequestDto;
import com.epam.capstone.dto.UserDto;
import com.epam.capstone.entities.User;

public interface UserService {
    UserDto getUserById(Integer id);
    UserDto getUserByUsername(String username);
   // UserDto getUserByProductId(Integer id);
    void saveUser(RegistrationRequestDto requestDto);
    void deleteUser(Integer id);
    User getUserByEmail(String email);

}
