package com.epam.capstone.dto;

import com.epam.capstone.entities.Cartitem;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CartitemToProductBasicMapper implements Function<Cartitem,ProductBasicDto> {

    @Override
    public ProductBasicDto apply(Cartitem cartitem) {
        return  new ProductBasicDto(
                cartitem.getProduct().getId(),
                cartitem.getProduct().getName(),
                cartitem.getProduct().getPrice(),
                cartitem.getProduct().getImageUrl()
        );
    }
}
