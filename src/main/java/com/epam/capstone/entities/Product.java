package com.epam.capstone.entities;

import com.epam.capstone.entities.enums.IsSecondHandEnum;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "category", nullable = false, length = 20)
    private String category;

    @Column(name = "price", nullable = false)
    private Double price;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller")
    private User seller;
//    @Column(name = "seller")
//    private Integer sellerId;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "seller", insertable = false, updatable = false)
//    private User seller;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "place_date")
    private LocalDate placeDate;

    @Column(name = "rating")
    private Integer rating;

    @Enumerated(EnumType.STRING)
    @Column(name = "secondhand", nullable = false)
    private IsSecondHandEnum secondhand;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Lob
    @Column(name = "description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public IsSecondHandEnum getSecondhand() {
        return secondhand;
    }

    public void setSecondhand(IsSecondHandEnum secondhand) {
        this.secondhand = secondhand;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public LocalDate getPlaceDate() {
        return placeDate;
    }

    public void setPlaceDate(LocalDate placeDate) {
        this.placeDate = placeDate;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}