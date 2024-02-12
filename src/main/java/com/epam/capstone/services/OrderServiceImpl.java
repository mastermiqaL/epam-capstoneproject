package com.epam.capstone.services;

import com.epam.capstone.dto.OrderDto;
import com.epam.capstone.dto.OrderDtoMapper;
import com.epam.capstone.dto.ProductBasicDto;
import com.epam.capstone.entities.Order;
import com.epam.capstone.repositories.OrderRepository;
import com.epam.capstone.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    private final ProductServiceImpl productService;
    private final OrderRepository orderRepository;
    private final OrderDtoMapper orderDtoMapper;

    public OrderServiceImpl(ProductServiceImpl productService, OrderRepository orderRepository, OrderDtoMapper orderDtoMapper) {
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.orderDtoMapper = orderDtoMapper;
    }

    @Override
    public List<OrderDto> getUserOrders(Integer id) {
        List<Order> orders =orderRepository.findByUserId(id);
        return orders.stream()
                .map(orderDtoMapper)
                .collect(Collectors.toList());
    }

    @Override
    public Order save(ProductBasicDto product) {
        Order order =new Order();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            order.setUser(userDetails.getUser());
            order.setProduct(productService.getProductById(product.getId()));
            order.setAmount(product.getPrice().intValue());
            order.setOrderDate(LocalDate.now());
            return orderRepository.save(order);
        } else {
            throw new IllegalStateException("Unable to retrieve authenticated user details.");
        }
    }
}
