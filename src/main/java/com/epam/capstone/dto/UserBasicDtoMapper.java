package com.epam.capstone.dto;

import com.epam.capstone.entities.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class UserBasicDtoMapper implements Function<User,UserBasicDto> {
    @Override
    public UserBasicDto apply(User user) {
        return new UserBasicDto(
                user.getFirstName(),
                user.getLastName(),
                user.getContactNumber(),
                user.getDateOfBirth(),
                user.getUsername()
        );
    }
}
