package com.epam.capstone.services;

import com.epam.capstone.entities.Cartitem;

import java.util.List;

public interface CartitemService {
    List<Cartitem> getCartItemsByUserID(Integer id);

}
