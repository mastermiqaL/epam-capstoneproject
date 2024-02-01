package com.epam.capstone.dto;

import java.io.Serializable;
import java.util.Objects;

public class CartitemDto implements Serializable {
    private final Integer productId;
    private final Integer amount;

    public CartitemDto(Integer productId, Integer amount) {
        this.productId = productId;
        this.amount = amount;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartitemDto entity = (CartitemDto) o;
        return Objects.equals(this.productId, entity.productId) &&
                Objects.equals(this.amount, entity.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, amount);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "productId = " + productId + ", " +
                "amount = " + amount + ")";
    }
}
