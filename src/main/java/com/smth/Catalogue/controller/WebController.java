package com.smth.Catalogue.controller;

import com.smth.Catalogue.entity.Product;
import com.smth.Catalogue.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebController {

    private final ProductService productService;

    public WebController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "false") boolean desc
    ) {
        Page<Product> productPage = productService.getPagedAndSortedProducts(page, size, sortBy, desc);
        model.addAttribute("productPage", productPage);
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("currentPage", page);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("desc", desc);
        return "index";
    }

    @GetMapping("/search")
    public String searchProducts(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "false") boolean desc,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        Page<Product> productPage = productService.getFilteredProducts(query, category, sortBy, desc, page, 6); // 6 на страницу

        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("query", query);
        model.addAttribute("category", category);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("desc", desc);
        model.addAttribute("categories", productService.getAllCategories());

        return "index";
    }

    @GetMapping("/product/{id}")
    public String getProductPage(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);

        List<Product> similar = productService.getSimilarProducts(product.getCategory(), id, 3);
        model.addAttribute("similarProducts", similar);

        return "product";
    }


    @GetMapping("/category/{category}")
    public String filterByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "false") boolean desc,
            Model model
    ) {
        Page<Product> productPage = productService.getFilteredProducts(null, category, sortBy, desc, page, 6);

        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("category", category);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("desc", desc);
        model.addAttribute("categories", productService.getAllCategories());

        return "index";
    }
}

