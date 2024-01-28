package com.epam.capstone.controllers;

import com.epam.capstone.dto.ProductBasicDto;
import com.epam.capstone.dto.ProductDto;
import com.epam.capstone.entities.Product;
import com.epam.capstone.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class ProductController {
    private final ProductServiceImpl productService;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products")
    public List<ProductBasicDto> getProducts(
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "12") int size){
        PageRequest pageable =PageRequest.of(page,size);
        return productService.getAllProducts(pageable);
    }

    @GetMapping(value = "/products/{product_id}")
    public ProductDto getProductById(
            @PathVariable Integer product_id
    ){
        return productService.getProductById(product_id);
    }

    @GetMapping(value = "/home")
    public List<ProductBasicDto> getHomePageProducts(
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "12") int size){

        PageRequest pagable = PageRequest.of(page,size);
        List<String> categories= Arrays.asList("Tech","Books","Plants");
        return productService.getStartPageProducts(categories,pagable);
    }

    @GetMapping(value = "/products/{seller_id}")
    public List<ProductBasicDto> getProductsBySellerId(
            @PathVariable Integer  seller_id,
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "20") int size){
        PageRequest pageable =PageRequest.of(page,size);
        return productService.getProductsBySellerId(seller_id,pageable);
    }


    @GetMapping(value = "/products/{seller_username}")
    public List<ProductBasicDto> getProductsBySellerUsername(
            @PathVariable String  seller_username,
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "size",defaultValue = "20") int size,
            @RequestParam(name = "sort",defaultValue ="rating") String sortField
    ){
        PageRequest pageable =PageRequest.of(page,size, Sort.by(sortField).descending());
        return productService.getProductsBySellerUsername(seller_username,pageable);
    }

    @GetMapping(value = "/products/search/{name}")
    public List<ProductBasicDto> getSearchResult(
            @PathVariable String name,
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "page",defaultValue = "12") int size
    ){
        PageRequest pageable =PageRequest.of(page,size);
        return productService.getSearchResult(name,pageable);
    }

    @GetMapping(value = "/products/filter/")
    public List<ProductBasicDto> getFilteredAndSortedProducts(
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) Boolean isSecondHand,
            @RequestParam(required = false) String[] sortFields,
            @RequestParam(required = false) Sort.Direction[] sortDirections,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size)
    {
        PageRequest pageable = PageRequest.of(page,size);
        return productService.getFilteredAndSortedProducts(categories,minPrice,maxPrice,isSecondHand,sortFields,sortDirections,pageable);
    }
    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody )
}
