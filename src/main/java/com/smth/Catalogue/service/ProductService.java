package com.smth.Catalogue.service;

import com.smth.Catalogue.entity.Product;
import com.smth.Catalogue.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setDownloadUrl(productDetails.getDownloadUrl());
        product.setWebsiteUrl(productDetails.getWebsiteUrl());
        product.setCategory(productDetails.getCategory());
        product.setDeveloper(productDetails.getDeveloper());
        product.setVersion(productDetails.getVersion());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Set<String> getAllCategories() {
        return productRepository.findAll().stream()
                .map(Product::getCategory)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public Page<Product> getPagedAndSortedProducts(int page, int size, String sortBy, boolean desc) {
        Sort sort = desc ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable);
    }

    public Page<Product> getFilteredProducts(String query, String category, String sortBy, boolean desc, int page, int size) {
        Sort sort = Sort.by(desc ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        if (query != null && !query.isEmpty()) {
            return productRepository.findByNameContainingIgnoreCase(query, pageable);
        } else if (category != null && !category.isEmpty()) {
            return productRepository.findByCategory(category, pageable);
        } else {
            return productRepository.findAll(pageable);
        }
    }

    public List<Product> getSimilarProducts(String category, Long excludeId, int limit) {
        return productRepository.findTopByCategoryAndIdNot(category, excludeId, PageRequest.of(0, limit));
    }
}