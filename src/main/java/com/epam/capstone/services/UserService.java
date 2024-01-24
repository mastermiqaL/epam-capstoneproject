package com.epam.capstone.services;

import com.epam.capstone.entities.User;

public interface UserService {
    User getUserById(Integer id);
    User getUserByUsername(String username);
    User getUserByProductId(Integer id);

}
