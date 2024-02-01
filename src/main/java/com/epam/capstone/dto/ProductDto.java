package com.epam.capstone.dto;

import com.epam.capstone.entities.enums.IsSecondHandEnum;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class ProductDto implements Serializable {
    private final Integer id;
    private final String name;
    private final String category;
    private final Double price;
    private final String sellerUsername;
    private final Integer stock;
    private final LocalDate placeDate;
    private final Integer rating;
    private final IsSecondHandEnum secondhand;
    private final String imageUrl;
    private final String description;

    public ProductDto(Integer id, String name, String category, Double price, String sellerUsername, Integer stock, LocalDate placeDate, Integer rating, IsSecondHandEnum secondhand, String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.sellerUsername = sellerUsername;
        this.stock = stock;
        this.placeDate = placeDate;
        this.rating = rating;
        this.secondhand = secondhand;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public Integer getStock() {
        return stock;
    }

    public LocalDate getPlaceDate() {
        return placeDate;
    }

    public Integer getRating() {
        return rating;
    }

    public IsSecondHandEnum getSecondhand() {
        return secondhand;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto entity = (ProductDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.category, entity.category) &&
                Objects.equals(this.price, entity.price) &&
                Objects.equals(this.sellerUsername, entity.sellerUsername) &&
                Objects.equals(this.stock, entity.stock) &&
                Objects.equals(this.placeDate, entity.placeDate) &&
                Objects.equals(this.rating, entity.rating) &&
                Objects.equals(this.secondhand, entity.secondhand) &&
                Objects.equals(this.imageUrl, entity.imageUrl) &&
                Objects.equals(this.description, entity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, price, sellerUsername, stock, placeDate, rating, secondhand, imageUrl, description);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "category = " + category + ", " +
                "price = " + price + ", " +
                "sellerUsername = " + sellerUsername + ", " +
                "stock = " + stock + ", " +
                "placeDate = " + placeDate + ", " +
                "rating = " + rating + ", " +
                "secondhand = " + secondhand + ", " +
                "imageUrl = " + imageUrl + ", " +
                "description = " + description + ")";
    }
}
