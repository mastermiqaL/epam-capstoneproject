package com.epam.capstone.dto;

import com.epam.capstone.entities.Product;

import java.util.function.Function;

public class ProductBasicDtoMapper implements Function<Product,ProductBasicDto> {
    @Override
    public ProductBasicDto apply(Product product) {
        return new ProductBasicDto(
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
    }
}
