package com.epam.capstone.services;

import com.epam.capstone.dto.AddressDto;
import com.epam.capstone.dto.RegistrationRequestDto;
import com.epam.capstone.dto.UserDto;
import com.epam.capstone.dto.UserDtoMapper;
import com.epam.capstone.entities.Address;
import com.epam.capstone.entities.User;
import com.epam.capstone.repositories.UserRepository;
import com.epam.capstone.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, UserDtoMapper userDtoMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired



    @Override
    public UserDto getUserById(Integer id) {
        User user=userRepository.findById(id).get();
        //todo:custom exception classes for handling exception
        return  userDtoMapper.apply(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user =userRepository.findByUsername(username).get();
        return userDtoMapper.apply(user);
    }


    @Override
    public void saveUser(RegistrationRequestDto registrationRequestDto) {
        User user = new User();
        user.setFirstName(registrationRequestDto.getFirstName());
        user.setLastName(registrationRequestDto.getLastName());
        user.setUsername(registrationRequestDto.getUsername());
        user.setEmail(registrationRequestDto.getEmail());
        user.setContactNumber(registrationRequestDto.getContactNumber());
        user.setDateOfBirth(registrationRequestDto.getDateOfBirth());
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
