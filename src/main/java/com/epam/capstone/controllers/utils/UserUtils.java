package com.epam.capstone.controllers.utils;

import com.epam.capstone.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {
    public static boolean isAuthenticatedUser(String userName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getActualUsername().equals(userName);
        }
        return false;
    }
}
