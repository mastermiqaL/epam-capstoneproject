package com.epam.capstone.services;

import com.epam.capstone.dto.ProductBasicDto;
import com.epam.capstone.dto.ProductBasicDtoMapper;
import com.epam.capstone.dto.ProductDto;
import com.epam.capstone.dto.ProductDtoMapper;
import com.epam.capstone.entities.Product;
import com.epam.capstone.repositories.ProductRepository;
import com.epam.capstone.specifications.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductDtoMapper productDtoMapper;
    private final ProductBasicDtoMapper productBasicDtoMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductDtoMapper productDtoMapper, ProductBasicDtoMapper productBasicDtoMapper) {
        this.productRepository = productRepository;
        this.productDtoMapper = productDtoMapper;
        this.productBasicDtoMapper = productBasicDtoMapper;
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    //TODO: custom exceptions for notfound action
    @Override
    public List<ProductBasicDto> getAllProducts(Pageable pageable) {
        List<Product> products = productRepository.findAll(pageable).getContent();
        return products.stream()
                .map(productBasicDtoMapper)
                .collect(Collectors.toList());
    }



    @Override
    public ProductDto getProductById(Integer id) {
        Product product = productRepository.findById(id).get();
        return productDtoMapper.apply(product);
    }

    @Override
    public List<ProductBasicDto> getSearchResult(String name, Pageable pageable) {
        List<Product> products = productRepository.findByNameContaining(name, pageable).getContent();
        return products.stream()
                .map(productBasicDtoMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductBasicDto> getStartPageProducts(List<String> categories, Pageable pageable) {
        List<Product> products = productRepository.findByCategoryIn(categories, pageable).getContent();
        return products.stream()
                .map(productBasicDtoMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductBasicDto> getProductsBySellerId(Integer id, Pageable pageable) {
        List<Product> products = productRepository.findBySeller_IdOrderByRatingDesc(id, pageable).getContent();
        return products.stream()
                .map(productBasicDtoMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductBasicDto> getProductsBySellerUsername(String username, Pageable pageable) {
        List<Product> products = productRepository.findBySeller_Username(username, pageable).getContent();
        return products.stream()
                .map(productBasicDtoMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductBasicDto> getFilteredAndSortedProducts(List<String> categories, Integer minPrice, Integer maxPrice, Boolean isSecondHand, String[] sortFields, Sort.Direction[] sortDirections, Pageable pageable) {
        Specification<Product> spec = Specification.where(null);

        if (categories != null && !categories.isEmpty()) {
            spec = spec.and(ProductSpecification.hasCategory(categories));
        }

        if (minPrice >= 0 && maxPrice >= 0 && maxPrice >= minPrice) {
            spec = spec.and(ProductSpecification.hasPriceInRange(minPrice, maxPrice));
        }

        if (isSecondHand) {
            spec = spec.and(ProductSpecification.isSecondHand(true));
        }

        List<Product> products = productRepository.findAll(spec, pageable).getContent();
        return products.stream()
                .map(productBasicDtoMapper)
                .collect(Collectors.toList());
    }
}
