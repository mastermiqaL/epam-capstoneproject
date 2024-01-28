package com.epam.capstone.controllers;

import com.epam.capstone.dto.CartitemDto;
import com.epam.capstone.entities.Cartitem;
import com.epam.capstone.services.CartitemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CartController {
    private final CartitemService cartitemService;

    public CartController(CartitemService cartitemService) {
        this.cartitemService = cartitemService;
    }

    @GetMapping(value = "/carts/{user_id")
    public List<CartitemDto> getCartItemsByUserID(
            @PathVariable Integer user_id
    ){
        return cartitemService.getCartItemsByUserID(user_id);
    }

}
