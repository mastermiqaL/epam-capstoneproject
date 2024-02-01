package com.epam.capstone.services;

import com.epam.capstone.dto.ProductBasicDto;
import com.epam.capstone.dto.ProductDto;
import com.epam.capstone.dto.ProductPlacingDto;
import com.epam.capstone.entities.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProductService {
    Product saveProduct(ProductPlacingDto product);
    void deleteProduct(Integer id);
    Product getProductById(Integer id);
    List<ProductBasicDto> getAllProducts(Pageable pageable);
    ProductDto getProductDtoById(Integer id);
    List<ProductBasicDto> getSearchResult(String name,Pageable pageable);
    List<ProductBasicDto> getStartPageProducts(List<String> categories, Pageable pageable);
    List<ProductBasicDto> getProductsBySellerId(Integer id,Pageable pageable);
    List<ProductBasicDto> getProductsBySellerUsername(String username,Pageable pageable);
    List<ProductBasicDto> getFilteredAndSortedProducts(List<String> category, Integer minPrice, Integer maxPrice, Boolean isSecondHand,
            String[] sortFields,Sort.Direction[] sortDirections, Pageable pageable);

}
