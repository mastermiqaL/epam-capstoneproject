package com.epam.capstone.controllers;

import com.epam.capstone.dto.AddressDto;
import com.epam.capstone.dto.OrderDto;
import com.epam.capstone.dto.ProductBasicDto;
import com.epam.capstone.entities.Cartitem;
import com.epam.capstone.entities.CartitemId;
import com.epam.capstone.entities.Order;
import com.epam.capstone.security.CustomUserDetails;
import com.epam.capstone.services.AddressServiceImpl;
import com.epam.capstone.services.CartitemServiceImpl;
import com.epam.capstone.services.OrderServiceImpl;
import com.epam.capstone.services.PaymentServiceImpl;
import org.aspectj.weaver.ast.Or;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@Controller
public class OrderController {
    private final OrderServiceImpl orderService;
    private final CartitemServiceImpl cartitemService;
    private final AddressServiceImpl addressService;
    private final PaymentServiceImpl paymentService;
    private static final Logger logger = Logger.getLogger(OrderController.class.getName());


    public OrderController(OrderServiceImpl orderService, CartitemServiceImpl cartitemService, AddressServiceImpl addressService, PaymentServiceImpl paymentService) {
        this.orderService = orderService;
        this.cartitemService = cartitemService;
        this.addressService = addressService;
        this.paymentService = paymentService;
    }


    @GetMapping(value = "/checkout")
    public String getCheckoutForm(Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {

                return "redirect:/login";
            }
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            logger.info("is authenticated");
            List<ProductBasicDto> cartItems = cartitemService.getCartItemsByUserId(userDetails.getUserId());
            logger.info("returned products:" + cartItems);
            if (cartItems.isEmpty()) {
                return "redirect:/cart";
            }
            AddressDto address = addressService.getAddressByUserId(userDetails.getUserId());
            logger.info("returned address:" + address);
            if (address == null) {
                return "redirect:/address/add";
            }

            Double totalPrice = 0.0;
            for (ProductBasicDto productBasicDto : cartItems) {
                totalPrice += productBasicDto.getPrice();
            }
            logger.info("total price is calculated:" + totalPrice);

            model.addAttribute("products", cartItems);
            model.addAttribute("address", address);
            logger.info("before casting");
            model.addAttribute("totalPrice", (float) totalPrice.doubleValue());
            logger.info("after casting");
            return "checkout";

        } catch (NoSuchElementException e) {
            return "redirect:/address/add";
        }
    }

    @GetMapping(value = "/orders/myorders")
    public String getMyOrders(Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
                return "redirect:/login";
            }
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            List<OrderDto> orders = orderService.getUserOrders(userDetails.getUserId());
            model.addAttribute("orders", orders);
            return "myorders";

        } catch (Exception e) {
            return "redirect:/users/myprofile";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/orders/add/save")
    public String saveNewOrder(RedirectAttributes redirectAttributes) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
                return "redirect:/login";
            }
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            List<ProductBasicDto> products = cartitemService.getCartItemsByUserId(userDetails.getUserId());
            for (ProductBasicDto productBasicDto : products) {
                Order savedOrder = orderService.save(productBasicDto);
                paymentService.save(savedOrder);
                CartitemId cartitemId = new CartitemId();
                cartitemId.setProductId(productBasicDto.getId());
                cartitemId.setUserId(userDetails.getUserId());
                cartitemService.deleteCartitem(cartitemId);
            }
            redirectAttributes.addFlashAttribute("successMessage", "Order made successfully");
            return "redirect:/orders/myorders";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error occurred");
            return "redirect:/cart";
        }
    }
}
