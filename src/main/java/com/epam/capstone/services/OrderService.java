package com.epam.capstone.services;

import com.epam.capstone.entities.Order;

import java.util.List;

public interface OrderService {
    List<Order> getUserOrders(Integer id);

}
