package com.epam.capstone.controllers;

import com.epam.capstone.dto.ProductBasicDto;
import com.epam.capstone.dto.ProductDto;
import com.epam.capstone.dto.ProductPlacingDto;
import com.epam.capstone.entities.Product;
import com.epam.capstone.security.CustomUserDetails;
import com.epam.capstone.services.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
public class ProductController {
    private final ProductServiceImpl productService;


    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products")
    public String getProducts(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "12") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        List<ProductBasicDto> products= productService.getAllProducts(pageable);
        model.addAttribute("products",products);
        return "products";
    }

    @GetMapping(value = "/products/id/{product_id}")
    public String getProductById(
            Model model,
            @PathVariable Integer product_id
    ) {
        ProductDto product = productService.getProductDtoById(product_id);
        model.addAttribute("product",product);
        return "productinfo";
    }

    @GetMapping(value = "/home")
    public String getHomePage(Model model,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "12") int size
    ) {

        PageRequest pageable = PageRequest.of(page, size);
        List<String> categories = Arrays.asList("Tech", "Books", "Plants");

        List<ProductBasicDto> productBasicDtos = productService.getStartPageProducts(categories, pageable);

        List<ProductBasicDto> techProducts = productBasicDtos.subList(0, 4);
//        List<ProductBasicDto> booksProducts = productBasicDtos.subList(4, 8);
//        List<ProductBasicDto> plantsProducts = productBasicDtos.subList(8, 12);

        model.addAttribute("techProducts", techProducts);
        model.addAttribute("search", "");
//        model.addAttribute("booksProducts", booksProducts);
//        model.addAttribute("plantsProducts", plantsProducts);

        return "home";
    }


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

    @GetMapping(value = "/products/search")
    public String searchProducts(Model model,
                                 @RequestParam("productName") String productName,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "12") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        List<ProductBasicDto> searchResults = productService.getSearchResult(productName, pageable);

        // Add the search results to the model
        model.addAttribute("products", searchResults);

        // Other existing code

        return "products";
    }

//    @ResponseBody
//    @GetMapping(value = "/products/search/{name}")
//    public List<ProductBasicDto> getSearchResult(
//
//            @PathVariable String name,
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "page", defaultValue = "12") int size
//    ) {
//        PageRequest pageable = PageRequest.of(page, size);
//        return productService.getSearchResult(name, pageable);
//    }

    @GetMapping(value = "/products/filter/")
    public String  getFilteredAndSortedProducts(
            Model model,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) Boolean isSecondHand,
            @RequestParam(required = false) String[] sortFields,
            @RequestParam(required = false) Sort.Direction[] sortDirections,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        List<ProductBasicDto> products= productService.getFilteredAndSortedProducts(categories, minPrice, maxPrice, isSecondHand, sortFields, sortDirections, pageable);
        model.addAttribute("products",products);
        return "products";
    }


    @PreAuthorize("hasRole('USER')")
    @DeleteMapping(value = "/products/delete/{productId}")
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
    @GetMapping(value = "/product/add")
    public String productAddForm(Model model){
        model.addAttribute("product",new ProductPlacingDto());
        return "placeproduct";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/products/add")
    public String addProduct(@Valid @ModelAttribute("product") ProductPlacingDto product,
                                             BindingResult result,
                                             Model model,
                                             RedirectAttributes redirectAttributes
    ) {
        try {
            if (result.hasErrors()) {
                return "placeproduct";
            }
            productService.saveProduct(product);
            redirectAttributes.addFlashAttribute("successMessage","Product added successfully!");
            return "profile";
        }catch (Exception e){
            model.addAttribute("registrationError", "An error occurred during registration. Please try again.");
            return "placeproduct";
        }
    }
}
