package com.epam.capstone.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartitemId implements Serializable {
    private static final long serialVersionUID = 905481177322639259L;
    @Column(name = "user_ID", nullable = false)
    private Integer userId;
    @Column(name = "product_ID", nullable = false)
    private Integer productId;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CartitemId entity = (CartitemId) o;
        return Objects.equals(this.productId, entity.productId) &&
                Objects.equals(this.userId, entity.userId);
    }
}