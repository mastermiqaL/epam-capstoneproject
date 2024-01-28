package com.epam.capstone;

import com.epam.capstone.entities.User;
import com.epam.capstone.repositories.UserRepository;

import com.epam.capstone.security.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {
    @Autowired
    private  UserRepository userRepository;

    @Test
    @Transactional
    public void saveUserTest(){
        User user=new User();
        user.setFirstName("a");
        user.setLastName("b");
        user.setContactNumber("5");
        user.setEmail("abc");
        user.setUsername("bca");
        user.setGender(0);
//        user.setRole("BUYER");
//        user.setPasswordHash("asdsa");
//        user.setSalt("qwe");
        userRepository.save(user);
       // userRepository.flush(); // Flush the EntityManager
        User savedUser = userRepository.findById(23).orElseThrow(); // Assuming findByUsername returns Optional<User>

        Assertions.assertEquals(23,savedUser.getId());
    }

    @Test
    @org.springframework.transaction.annotation.Transactional
    public void userTest(){};

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public DataSource dataSource() {
            // Define your DataSource bean here
            return DataSourceBuilder.create().url("jdbc:mariadb://localhost:3306/imarket").username("root").password("123").build();
        }
    }
}
