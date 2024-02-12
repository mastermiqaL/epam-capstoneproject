package com.epam.capstone.repositories;

import com.epam.capstone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Override
    Optional<User> findById(Integer integer);

    User save(User user);

    Optional<User> findByUsername(String username);

    User findByEmail(String email);



}