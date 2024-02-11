package com.epam.capstone.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class OrderDto implements Serializable {
    private final Integer id;
    private final Integer productId;
    private final Integer amount;
    private final LocalDate orderDate;

    public OrderDto(Integer id, Integer productId, Integer amount, LocalDate orderDate) {
        this.id = id;
        this.productId = productId;
        this.amount = amount;
        this.orderDate = orderDate;
    }

    public Integer getId() {
        return id;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getAmount() {
        return amount;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto entity = (OrderDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.productId, entity.productId) &&
                Objects.equals(this.amount, entity.amount) &&
                Objects.equals(this.orderDate, entity.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, amount, orderDate);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "productId = " + productId + ", " +
                "amount = " + amount + ", " +
                "orderDate = " + orderDate + ")";
    }
}
