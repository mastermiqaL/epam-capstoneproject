package com.epam.capstone.services;

import com.epam.capstone.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts(Pageable pageable);
    Product getProductById(Integer integer);
    List<Product> getStartPageProducts(Pageable pageable);
    List<Product> getProductsBySellerId(Integer integer,Pageable pageable);
    List<Product> getProductsBySellerUsername(String username,Pageable pageable);
    List<Product> filterAndSortProducts(String category, Integer minPrice, Integer maxPrice, Boolean isSecondHand,
            String[] sortFields,Sort.Direction[] sortDirections, Pageable pageable);

}
