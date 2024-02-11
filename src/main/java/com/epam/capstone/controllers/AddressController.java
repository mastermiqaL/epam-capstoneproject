package com.epam.capstone.controllers;

import com.epam.capstone.dto.AddressPlacingDto;
import com.epam.capstone.dto.ProductPlacingDto;
import com.epam.capstone.services.AddressService;
import com.epam.capstone.services.AddressServiceImpl;
import jakarta.validation.Valid;
import org.hibernate.sql.ast.tree.expression.SqlTuple;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AddressController {
    private final AddressServiceImpl addressService;

    public AddressController(AddressServiceImpl addressService) {
        this.addressService = addressService;
    }

    @GetMapping(value = "/address/add")
    public String getAddressPlacingForm(Model model) {
        AddressPlacingDto address = new AddressPlacingDto();
        model.addAttribute("address", address);
        return "addaddress";
    }

    @PostMapping(value = "/address/add/save")
    public String addAddress(@Valid @ModelAttribute("address") AddressPlacingDto addressPlacingDto,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            if (result.hasErrors()) {
                return "redirect:/address/add";
            }
            addressService.saveAddress(addressPlacingDto);
            redirectAttributes.addFlashAttribute("successMessage", "Address added successfully.");
            return "redirect:/users/myprofile";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error occurred.");
            return "redirect:/address/add";
        }

    }
}
