package com.epam.capstone.controllers;

import com.epam.capstone.entities.Order;
import com.epam.capstone.services.OrderServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class OrderController {
    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }
//
//    @GetMapping(value = "/orders/{userID}")
//    public ResponseEntity<List<Order>>

}
