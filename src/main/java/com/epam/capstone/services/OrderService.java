package com.epam.capstone.services;

import com.epam.capstone.dto.OrderDto;
import com.epam.capstone.dto.ProductBasicDto;
import com.epam.capstone.entities.Order;
import com.epam.capstone.entities.Product;

import java.util.List;

public interface OrderService {
    List<OrderDto> getUserOrders(Integer id);
    Order save( ProductBasicDto product);

}
