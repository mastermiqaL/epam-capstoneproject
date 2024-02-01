package com.epam.capstone.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "cartitem")
public class Cartitem {
    @EmbeddedId
    private CartitemId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_ID", nullable = false)
    private User user;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_ID", nullable = false)
    private Product product;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CartitemId getId() {
        return id;
    }

    public void setId(CartitemId id) {
        this.id = id;
    }
}