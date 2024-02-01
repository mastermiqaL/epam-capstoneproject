package com.epam.capstone.controllers;

import com.epam.capstone.dto.CartitemDto;
import com.epam.capstone.entities.Cartitem;
import com.epam.capstone.entities.CartitemId;
import com.epam.capstone.security.CustomUserDetails;
import com.epam.capstone.services.CartitemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class CartController {
    private final CartitemService cartitemService;

    public CartController(CartitemService cartitemService) {
        this.cartitemService = cartitemService;
    }

    @GetMapping(value = "/cart/{user_id}")
    public ResponseEntity<List<CartitemDto>> getCartItemsByUserID(
            @PathVariable Integer user_id
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        if(userDetails.getUserId().equals(user_id)){
            return ResponseEntity.ok(cartitemService.getCartItemsByUserID(user_id));
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyList());
        }
    }

    @PostMapping(value = "/cart/add/{productId}")
    public ResponseEntity<String> addCartitem(
            @PathVariable Integer productId
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        CartitemId cartitemId=new CartitemId();
        return null;
    }

}
