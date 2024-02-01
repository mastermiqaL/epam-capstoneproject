package com.epam.capstone.controllers;

import com.epam.capstone.dto.AuthRequestDto;
import com.epam.capstone.dto.RegistrationRequestDto;
import com.epam.capstone.entities.User;
import com.epam.capstone.services.JwtService;
import com.epam.capstone.services.UserServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.logging.Logger;

@Controller
public class AuthController {
    private final UserServiceImpl userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    @Autowired
    public AuthController(UserServiceImpl userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }



    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequestDto authRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.getEmail(),
                        authRequestDto.getPassword()
                )
        );
        logger.info("past authentication object");
        if (authentication.isAuthenticated()) {
            logger.info("is authenticated");
            String token = jwtService.generateToken(authRequestDto.getEmail());
            logger.info("token generated");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization",token);
            return new ResponseEntity<>("Login successful", headers, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }

    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        RegistrationRequestDto user = new RegistrationRequestDto();
        model.addAttribute("user", user);
        return "register";
    }



    @PostMapping("/register")
    public ResponseEntity<String> registration(@Validated @RequestBody RegistrationRequestDto registrationRequestDto,
                                               BindingResult result) {

        User existingUser = userService.getUserByEmail(registrationRequestDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null, "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            // Extract field errors for detailed information
            StringBuilder errors = new StringBuilder("Registration failed. Check the provided data. Errors: ");
            for (FieldError error : result.getFieldErrors()) {
                errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
        }

        userService.saveUser(registrationRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body("Registration successful. Please login.");
    }


}
