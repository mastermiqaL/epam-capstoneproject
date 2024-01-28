package com.epam.capstone.services;

import com.epam.capstone.dto.CartitemDto;
import com.epam.capstone.dto.CartitemDtoMapper;
import com.epam.capstone.entities.Cartitem;
import com.epam.capstone.entities.CartitemId;
import com.epam.capstone.repositories.CartitemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartitemServiceImpl implements CartitemService{
    private final CartitemRepository cartitemRepository;
    private final CartitemDtoMapper cartitemDtoMapper;

    @Autowired
    public CartitemServiceImpl(CartitemRepository cartitemRepository, CartitemDtoMapper cartitemDtoMapper) {
        this.cartitemRepository = cartitemRepository;
        this.cartitemDtoMapper = cartitemDtoMapper;
    }

    @Override
    public List<CartitemDto> getCartItemsByUserID(Integer id) {
        List<Cartitem> cartitems=cartitemRepository.findByCart_User_Id(id);
        return cartitems.stream()
                .map(cartitemDtoMapper)
                .collect(Collectors.toList());
    }

    @Override
    public Cartitem saveCartitem(Cartitem cartitem) {
        return cartitemRepository.save(cartitem);
    }

    @Override
    public void deleteCartitem(CartitemId cartitemId) {
        cartitemRepository.deleteById(cartitemId);
    }
}
