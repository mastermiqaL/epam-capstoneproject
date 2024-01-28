package com.epam.capstone.services;

import com.epam.capstone.dto.CartitemDto;
import com.epam.capstone.entities.Cartitem;
import com.epam.capstone.entities.CartitemId;

import java.util.List;

public interface CartitemService {
    List<CartitemDto> getCartItemsByUserID(Integer id);
    Cartitem saveCartitem(Cartitem cartitem);
    void deleteCartitem(CartitemId cartitemId);
}
