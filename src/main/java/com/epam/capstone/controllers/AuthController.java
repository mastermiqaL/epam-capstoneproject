package com.epam.capstone.controllers;

import com.epam.capstone.dto.AuthRequestDto;
import com.epam.capstone.dto.RegistrationRequestDto;
import com.epam.capstone.entities.User;
import com.epam.capstone.services.JwtService;
import com.epam.capstone.services.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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


    public AuthController(UserServiceImpl userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @GetMapping(value = "/account")
    public String redirectToProfileOrLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            return "redirect:/users/myprofile";
        } else {
            return "redirect:/login";
        }
    }


    @GetMapping("/index")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }


    @GetMapping(value = "/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegistrationRequestDto());
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") RegistrationRequestDto registrationRequestDto,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        try {
            if (result.hasErrors()) {
                return "register";
            }
            User existingUser = userService.getUserByEmail(registrationRequestDto.getEmail());
            if (existingUser != null) {
                result.rejectValue("email", null, "There is already an account registered with the same email");
                return "register";
            }
            userService.saveUser(registrationRequestDto);
        } catch (Exception e) {
            model.addAttribute("registrationError", "An error occurred during registration. Please try again.");
            return "register";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Registration successful. Please login.");
        return "redirect:/login";
    }


}
