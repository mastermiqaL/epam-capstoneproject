package com.epam.capstone.dto;

import com.epam.capstone.entities.User;

import java.util.function.Function;

public class UserBasicDtoMapper implements Function<User,UserBasicDto> {
    @Override
    public UserBasicDto apply(User user) {
        return new UserBasicDto(
                user.getFirstName(),
                user.getLastName(),
                user.getContactNumber(),
                user.getGender(),
                user.getDateOfBirth(),
                user.getUsername()
        );
    }
}
