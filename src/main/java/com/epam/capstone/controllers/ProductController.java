package com.epam.capstone.controllers;

import com.epam.capstone.dto.ProductBasicDto;
import com.epam.capstone.dto.ProductDto;
import com.epam.capstone.dto.ProductPlacingDto;
import com.epam.capstone.entities.Product;
import com.epam.capstone.security.CustomUserDetails;
import com.epam.capstone.services.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class ProductController {
    private final ProductServiceImpl productService;

    private static final Logger logger = Logger.getLogger(ProductController.class.getName());

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


    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/products/myproducts")
    public String getMyProducts(Model model){
        logger.info("in method");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return "redirect:/login";
        }
        logger.info("authenticatio is not null");
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        logger.info("casted successfully");
        List<ProductDto> productList = productService.getProductsDtoBySellerId(userDetails.getUserId());
        logger.info("product list:" + productList.toString());
        model.addAttribute("productList",productList);
        return "myproducts";
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
    public String getProductsBySellerUsername(
            Model model,
            @PathVariable String seller_username,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            @RequestParam(name = "sort", defaultValue = "rating") String sortField
    ) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortField).descending());
        List<ProductBasicDto> products= productService.getProductsBySellerUsername(seller_username, pageable);
        model.addAttribute("products",products);
        return "sellerproducts";
    }

    @GetMapping(value = "/products/search")
    public String searchProducts(Model model,
                                 @RequestParam("productName") String productName,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "12") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        List<ProductBasicDto> searchResults = productService.getSearchResult(productName, pageable);
        model.addAttribute("products", searchResults);
        return "products";
    }


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
    @GetMapping(value = "/products/delete/{productId}")
    public String deleteProduct(@PathVariable Integer productId,RedirectAttributes redirectAttributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return "redirect:/login";
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Product product = productService.getProductById(productId);
        if (product.getSeller().getId().equals(userDetails.getUserId())) {
            productService.deleteProduct(productId);
            redirectAttributes.addFlashAttribute("successMessage","Product deleted successfully!");
            return "redirect:/products/myproducts";
        } else {
            redirectAttributes.addFlashAttribute("failureMessage","You don't have permission to delete this product");
            return "redirect:/forbidden";
        }

    }



    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/products/add")
    public String productAddForm(Model model){
        model.addAttribute("product",new ProductPlacingDto());
        return "addproduct";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/products/add/save")
    public String addProduct(@Valid @ModelAttribute("product") ProductPlacingDto product,
                                             BindingResult result,
                                             RedirectAttributes redirectAttributes
    ) {
        try {
            if (result.hasErrors()) {
                return "redirect:/products/add";
            }
            productService.saveProduct(product);
            redirectAttributes.addFlashAttribute("successMessage","Product added successfully!");
            return "redirect:/products/myproducts";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred during registration. Please try again.");
            return "redirect:/products/add";
        }
    }
}
