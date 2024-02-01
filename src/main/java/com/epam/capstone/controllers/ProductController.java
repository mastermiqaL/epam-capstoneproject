package com.epam.capstone.controllers;

import com.epam.capstone.dto.ProductBasicDto;
import com.epam.capstone.dto.ProductDto;
import com.epam.capstone.dto.ProductPlacingDto;
import com.epam.capstone.entities.Product;
import com.epam.capstone.security.CustomUserDetails;
import com.epam.capstone.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "12") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return productService.getAllProducts(pageable);
    }

    @GetMapping(value = "/products/id/{product_id}")
    public ProductDto getProductById(
            @PathVariable Integer product_id
    ) {
        return productService.getProductDtoById(product_id);
    }

    @GetMapping(value = "/home")
    public List<ProductBasicDto> getHomePageProducts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "12") int size) {

        PageRequest pagable = PageRequest.of(page, size);
        List<String> categories = Arrays.asList("Tech", "Books", "Plants");
        return productService.getStartPageProducts(categories, pagable);
    }

//    @GetMapping(value = "/products/{seller_id}")
//    public List<ProductBasicDto> getProductsBySellerId(
//            @PathVariable Integer  seller_id,
//            @RequestParam(name = "page",defaultValue = "0") int page,
//            @RequestParam(name = "size",defaultValue = "20") int size){
//        PageRequest pageable =PageRequest.of(page,size);
//        return productService.getProductsBySellerId(seller_id,pageable);
//    }


    @GetMapping(value = "/products/seller/{seller_username}")
    public List<ProductBasicDto> getProductsBySellerUsername(
            @PathVariable String seller_username,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            @RequestParam(name = "sort", defaultValue = "rating") String sortField
    ) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortField).descending());
        return productService.getProductsBySellerUsername(seller_username, pageable);
    }

    @GetMapping(value = "/products/search/{name}")
    public List<ProductBasicDto> getSearchResult(
            @PathVariable String name,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "page", defaultValue = "12") int size
    ) {
        PageRequest pageable = PageRequest.of(page, size);
        return productService.getSearchResult(name, pageable);
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
            @RequestParam(defaultValue = "5") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return productService.getFilteredAndSortedProducts(categories, minPrice, maxPrice, isSecondHand, sortFields, sortDirections, pageable);
    }

    //    @PostMapping
//    public ResponseEntity<Product> saveProduct(@RequestBody )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/products/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        if (product.getSeller().getId().equals(userDetails.getUserId())) {
            productService.deleteProduct(productId);
            return ResponseEntity.ok("Product deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You don't have permission to delete this product.");
        }
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/products/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductPlacingDto product) {
        productService.saveProduct(product);
        return ResponseEntity.ok("Product added successfully.");
    }
}
