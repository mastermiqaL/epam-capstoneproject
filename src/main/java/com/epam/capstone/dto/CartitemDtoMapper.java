package com.epam.capstone.dto;

import com.epam.capstone.entities.Cartitem;

import java.util.function.Function;

public class CartitemDtoMapper implements Function<Cartitem,CartitemDto> {
    @Override
    public CartitemDto apply(Cartitem cartitem) {
        return new CartitemDto(
                cartitem.getProduct().getId(),
                cartitem.getAmount()
        );
        //todo:es uech gasatestia
    }
}
