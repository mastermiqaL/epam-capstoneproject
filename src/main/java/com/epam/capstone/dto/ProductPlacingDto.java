package com.epam.capstone.dto;

import java.io.Serializable;
import java.util.Objects;

public class ProductPlacingDto implements Serializable {
    private final String name;
    private final String category;
    private final Double price;
    private final Integer stock;
    private final String secondhand;
    private final String imageUrl;
    private final String description;

    public ProductPlacingDto(String name, String category, Double price, Integer stock, String secondhand, String imageUrl, String description) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.secondhand = secondhand;
        this.imageUrl = imageUrl;
        this.description = description;
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

    public Integer getStock() {
        return stock;
    }

    public String getSecondhand() {
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
        ProductPlacingDto entity = (ProductPlacingDto) o;
        return Objects.equals(this.name, entity.name) &&
                Objects.equals(this.category, entity.category) &&
                Objects.equals(this.price, entity.price) &&
                Objects.equals(this.stock, entity.stock) &&
                Objects.equals(this.secondhand, entity.secondhand) &&
                Objects.equals(this.imageUrl, entity.imageUrl) &&
                Objects.equals(this.description, entity.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, price, stock, secondhand, imageUrl, description);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ", " +
                "category = " + category + ", " +
                "price = " + price + ", " +
                "stock = " + stock + ", " +
                "secondhand = " + secondhand + ", " +
                "imageUrl = " + imageUrl + ", " +
                "description = " + description + ")";
    }
}
