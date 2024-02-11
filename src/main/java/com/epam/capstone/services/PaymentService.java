package com.epam.capstone.services;

import com.epam.capstone.entities.Order;

public interface PaymentService {
    void save(Order order);
}
