package com.epam.capstone.controllers;

import com.epam.capstone.dto.CartitemDto;
import com.epam.capstone.dto.ProductBasicDto;
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
import java.util.logging.Logger;

@RestController
public class CartController {
    private final CartitemService cartitemService;
    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    public CartController(CartitemService cartitemService) {
        this.cartitemService = cartitemService;
    }

    @GetMapping(value = "/cart/{user_id}")
    public ResponseEntity<List<ProductBasicDto>> getCartItemsByUserID(
            @PathVariable Integer user_id
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        logger.info("in  getCartitemsByUserId");
        if(userDetails.getUserId().equals(user_id)){
            logger.info("user is same user who requests");
            return ResponseEntity.ok(cartitemService.getCartItemsByUserID(user_id));
        }else{
            logger.info("not same user");
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
        cartitemId.setUserId(userDetails.getUserId());
        cartitemId.setProductId(productId);
        cartitemService.saveCartitem(cartitemId,productId,userDetails.getUserId());
        return ResponseEntity.ok("Product added to cart successfully");
    }

    @DeleteMapping(value = "/cart/delete/{productId}")
    public ResponseEntity<String> deleteCartitem(
            @PathVariable Integer productId
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        CartitemId cartitemId =new CartitemId();
        cartitemId.setUserId(userDetails.getUserId());
        cartitemId.setProductId(productId);

        if(cartitemService.getCartitem(cartitemId)!=null){
            cartitemService.deleteCartitem(cartitemId);
            return ResponseEntity.ok("Product removed successfully from cart.");
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You don't have product in cart.");
        }
    }

}
