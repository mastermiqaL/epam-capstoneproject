package com.epam.capstone.controllers;

import com.epam.capstone.entities.CartitemId;
import com.epam.capstone.security.CustomUserDetails;
import com.epam.capstone.services.CartitemService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;
import java.util.logging.Logger;

@Controller
public class CartController {
    private final CartitemService cartitemService;
    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    public CartController(CartitemService cartitemService) {
        this.cartitemService = cartitemService;
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/cart")
    public String getCartItemsByUserID( Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {

            return "error";
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();


        logger.info("user is the same user who requests");
        model.addAttribute("cartItems", cartitemService.getCartItemsByUserID(userDetails.getUserId()));
        return "cart";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/cart/add/{productId}")
    public String addCartitem(
            @PathVariable Integer productId,
            RedirectAttributes redirectAttributes
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return "error";
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        CartitemId cartitemId = new CartitemId();
        cartitemId.setUserId(userDetails.getUserId());
        cartitemId.setProductId(productId);
        cartitemService.saveCartitem(cartitemId, productId, userDetails.getUserId());
        redirectAttributes.addFlashAttribute("successMessage", "Product added to cart successfully");

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/cart/delete/{productId}")
    public String deleteCartitem(
            @PathVariable Integer productId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Error occurred.");
                return "redirect:/cart";
            }
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            CartitemId cartitemId = new CartitemId();
            cartitemId.setUserId(userDetails.getUserId());
            cartitemId.setProductId(productId);

            if (cartitemService.getCartitem(cartitemId) != null) {
                cartitemService.deleteCartitem(cartitemId);
                redirectAttributes.addFlashAttribute("successMessage", "Product removed successfully from cart.");
            } else {
                redirectAttributes.addFlashAttribute("failureMessage", "You don't have product in cart.");
            }
            return "redirect:/cart"; // Redirect to the cart page after deleting the item

        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
         //   return "redirect:/cart"; // Redirect to the cart page if an error occurs
        }
        return "redirect:/cart";
    }
//@DeleteMapping(value = "/cart/delete/{productId}")
//@ResponseBody // Indicates that the return type should be written directly in the response body
//public ResponseEntity<?> deleteCartItem(
//        @PathVariable Integer productId
//) {
//    try {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
//        }
//        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//
//        CartitemId cartitemId = new CartitemId();
//        cartitemId.setUserId(userDetails.getUserId());
//        cartitemId.setProductId(productId);
//
//        if (cartitemService.getCartitem(cartitemId) != null) {
//            cartitemService.deleteCartitem(cartitemId);
//            return ResponseEntity.ok("Product removed successfully from cart.");
//        } else {
//            return ResponseEntity.badRequest().body("You don't have this product in your cart.");
//        }
//    } catch (Exception e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while deleting the product.");
//    }
//}


}
