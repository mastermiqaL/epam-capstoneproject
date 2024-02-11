package com.epam.capstone.controllers;

import com.epam.capstone.dto.ProductDto;
import com.epam.capstone.services.ProductServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminController {
    private final ProductServiceImpl productService;


    public AdminController(ProductServiceImpl productService) {
        this.productService = productService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/admin/dashboard")
    public String getDashboard(Model model){
        List<ProductDto> products = productService.getAllProducts();
        model.addAttribute("products",products);
        return "dashboard";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/admin/products/delete/{productId}")
    public String deleteProductByAdmin(@PathVariable Integer productId, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(productId);
        redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully!");
        return "redirect:/admin/dashboard";
    }

}
