package com.epam.capstone.dto;

import java.io.Serializable;
import java.util.Objects;

public class ProductBasicDto implements Serializable {
    private final Integer id;
    private final String name;
    private final Double price;
    private final String imageUrl;

    public ProductBasicDto(Integer id,String name, Double price, String imageUrl) {
        this.id=id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public  Integer getId(){return id;}
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
        ProductBasicDto that = (ProductBasicDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(price, that.price) && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, imageUrl);
    }

    @Override
    public String toString() {
        return "ProductBasicDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ProductBasicDto entity = (ProductBasicDto) o;
//        return Objects.equals(this.name, entity.name) &&
//                Objects.equals(this.price, entity.price) &&
//                Objects.equals(this.imageUrl, entity.imageUrl);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name, price, imageUrl);
//    }
//
//    @Override
//    public String toString() {
//        return getClass().getSimpleName() + "(" +
//                "name = " + name + ", " +
//                "price = " + price + ", " +
//                "imageUrl = " + imageUrl + ")";
//    }
}
