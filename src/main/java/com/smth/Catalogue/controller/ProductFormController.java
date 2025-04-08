package com.smth.Catalogue.controller;

import org.springframework.ui.Model;
import com.smth.Catalogue.entity.Product;
import com.smth.Catalogue.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductFormController {

    private final ProductService productService;

    public ProductFormController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/new")
    public String showForm(Model model) {
        model.addAttribute("product", new Product());
        return "product_form";
    }

    @PostMapping("/product/new")
    public String submitForm(@ModelAttribute Product product) {
        productService.createProduct(product);
        return "redirect:/";
    }
}
