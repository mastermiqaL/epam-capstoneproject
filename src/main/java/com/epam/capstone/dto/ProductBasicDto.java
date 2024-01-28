package com.epam.capstone.dto;

import java.io.Serializable;
import java.util.Objects;

public class ProductBasicDto implements Serializable {
    private final String name;
    private final Double price;
    private final String imageUrl;

    public ProductBasicDto(String name, Double price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductBasicDto entity = (ProductBasicDto) o;
        return Objects.equals(this.name, entity.name) &&
                Objects.equals(this.price, entity.price) &&
                Objects.equals(this.imageUrl, entity.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, imageUrl);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ", " +
                "price = " + price + ", " +
                "imageUrl = " + imageUrl + ")";
    }
}
