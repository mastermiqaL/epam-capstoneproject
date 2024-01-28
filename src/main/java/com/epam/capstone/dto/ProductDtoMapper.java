package com.epam.capstone.dto;

import com.epam.capstone.entities.Product;

import java.util.function.Function;

public class ProductDtoMapper implements Function<Product,ProductDto> {
    @Override
    public ProductDto apply(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getName(),
                product.getStock(),
                product.getPlaceDate(),
                product.getRating(),
                product.getSecondhand(),
                product.getImageUrl(),
                product.getDescription()
        );
    }
}
