package com.epam.capstone.services;

import com.epam.capstone.controllers.AuthController;
import com.epam.capstone.dto.RegistrationRequestDto;
import com.epam.capstone.dto.UserDto;
import com.epam.capstone.dto.UserDtoMapper;
import com.epam.capstone.entities.User;
import com.epam.capstone.repositories.UserRepository;
import com.epam.capstone.security.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());


    public UserServiceImpl(UserRepository userRepository, UserDtoMapper userDtoMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
        this.passwordEncoder = passwordEncoder;
    }





    @Override
    public UserDto getUserById(Integer id) {
        User user=userRepository.findById(id).get();
        //todo:custom exception classes for handling exception
        return  userDtoMapper.apply(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }


    @Override
    public void saveUser(RegistrationRequestDto registrationRequestDto) {
        User user = new User();
        logger.info(registrationRequestDto.getFirstName()+"");
        user.setFirstName(registrationRequestDto.getFirstName());
        user.setLastName(registrationRequestDto.getLastName());
        user.setUsername(registrationRequestDto.getUsername());
        logger.info(registrationRequestDto.getEmail()+"");
        user.setEmail(registrationRequestDto.getEmail());
        user.setContactNumber(registrationRequestDto.getContactNumber());
        logger.info(registrationRequestDto.getDateOfBirth()+"");
        user.setDateOfBirth(registrationRequestDto.getDateOfBirth());
        logger.info(registrationRequestDto.getPassword()+"");
        user.setPasswordHash(passwordEncoder.encode(registrationRequestDto.getPassword()));
        user.setRole(UserRole.USER);


        userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
