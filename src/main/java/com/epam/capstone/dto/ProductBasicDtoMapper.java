package com.epam.capstone.dto;

import com.epam.capstone.entities.Product;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class ProductBasicDtoMapper implements Function<Product,ProductBasicDto> {
    @Override
    public ProductBasicDto apply(Product product) {
        return new ProductBasicDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
    }
}
