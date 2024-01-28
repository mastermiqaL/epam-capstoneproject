package com.epam.capstone.dto;

import com.epam.capstone.entities.User;

import java.util.function.Function;

public class UserDtoMapper implements Function<User,UserDto> {
    @Override
    public UserDto apply(User user) {
        return new UserDto(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getContactNumber(),
                user.getGender(),
                user.getDateOfBirth(),
                user.getUsername()
        );
    }
}
