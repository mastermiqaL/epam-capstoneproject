package com.epam.capstone.security;

import com.epam.capstone.entities.Product;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("productSecurity")
public class ProductSecurity {
    @PreAuthorize("hasRole('USER')")
    public static boolean canDeleteProduct(Product product) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return product.getSeller().getId().equals(userDetails.getUserId());
    }

    @PreAuthorize("hasRole('USER')")
    public boolean canAddProduct() {
        // Additional logic if needed
        return true;
    }
}