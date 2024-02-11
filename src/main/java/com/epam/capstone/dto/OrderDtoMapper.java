package com.epam.capstone.dto;

import com.epam.capstone.entities.Order;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class OrderDtoMapper implements Function<Order,OrderDto> {
    @Override
    public OrderDto apply(Order order) {
        return  new OrderDto(
                order.getId(),
                order.getProduct().getId(),
                order.getAmount(),
                order.getOrderDate()
        );
    }
}
