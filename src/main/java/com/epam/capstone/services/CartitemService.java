package com.epam.capstone.services;

import com.epam.capstone.dto.CartitemDto;
import com.epam.capstone.dto.ProductBasicDto;
import com.epam.capstone.entities.Cartitem;
import com.epam.capstone.entities.CartitemId;

import java.util.List;

public interface CartitemService {
    List<ProductBasicDto> getCartItemsByUserID(Integer id);
    Cartitem saveCartitem(CartitemId cartitemId,Integer productId,Integer userId);
    Cartitem getCartitem(CartitemId cartitemId);
    void deleteCartitem(CartitemId cartitemId);
}
