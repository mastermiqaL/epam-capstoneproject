package com.epam.capstone.services;

import com.epam.capstone.dto.*;
import com.epam.capstone.entities.Cartitem;
import com.epam.capstone.entities.CartitemId;
import com.epam.capstone.entities.Product;
import com.epam.capstone.entities.User;
import com.epam.capstone.repositories.CartitemRepository;
import com.epam.capstone.repositories.ProductRepository;
import com.epam.capstone.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CartitemServiceImpl implements CartitemService{
    private  final UserRepository userRepository;
    private final CartitemRepository cartitemRepository;
    private final CartitemToProductBasicMapper cartitemToProductBasicMapper;
    private final ProductRepository productRepository;

    public CartitemServiceImpl(UserRepository userRepository, CartitemRepository cartitemRepository, CartitemToProductBasicMapper cartitemToProductBasicMapper, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.cartitemRepository = cartitemRepository;
        this.cartitemToProductBasicMapper = cartitemToProductBasicMapper;
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductBasicDto> getCartItemsByUserId(Integer id) {
        List<Cartitem> cartitems=cartitemRepository.findByUserId(id);
        return cartitems.stream()
                .map(cartitemToProductBasicMapper)
                .collect(Collectors.toList());
    }

    @Override
    public Cartitem saveCartitem(CartitemId cartitemId,Integer productId,Integer userId) {
        Product product =productRepository.findById(productId).get();
        User user = userRepository.findById(userId).get();
        Cartitem cartitem=new Cartitem();
        cartitem.setId(cartitemId);
        cartitem.setAmount(1);
        cartitem.setProduct(product);
        cartitem.setUser(user);
       return cartitemRepository.save(cartitem);
    }

    @Override
    public Cartitem getCartitem(CartitemId cartitemId) {
        return cartitemRepository.findById(cartitemId)
                .orElseThrow(() -> new NoSuchElementException("Cartitem not found with id: " + cartitemId));
    }


    @Override
    public void deleteCartitem(CartitemId cartitemId) {
        cartitemRepository.deleteById(cartitemId);
    }
}
