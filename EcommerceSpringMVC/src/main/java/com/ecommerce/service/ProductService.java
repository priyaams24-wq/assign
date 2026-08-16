package com.ecommerce.service;

import com.ecommerce.dto.ProductResponse;
import com.ecommerce.exception.ECommerceException;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void addProduct(Product product) {
        if (product.productNo() == null || product.productNo().trim().isEmpty()) {
            throw new ECommerceException("Product number cannot be empty");
        }
        if (product.price() <= 0) {
            throw new ECommerceException("Product price must be greater than zero");
        }
        if (product.stock() < 0) {
            throw new ECommerceException("Product stock cannot be negative");
        }

        if (productRepository.findByProductNo(product.productNo()).isPresent()) {
            throw new ECommerceException("Product number already exists: " + product.productNo());
        }
        productRepository.save(product);
    }

    public Optional<ProductResponse> getProductByNo(String productNo) {
        if (productNo == null || productNo.trim().isEmpty()) {
            return Optional.empty();
        }
        return productRepository.findByProductNo(productNo.trim())
                .map(p -> new ProductResponse(p.productNo(), p.price(), p.stock()));
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(p -> new ProductResponse(p.productNo(), p.price(), p.stock()))
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getAvailableProducts() {
        return productRepository.findAvailable().stream()
                .map(p -> new ProductResponse(p.productNo(), p.price(), p.stock()))
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByPriceRange(double min, double max) {
        if (min < 0 || max < min) {
            return Collections.emptyList();
        }
        return productRepository.findByPriceRange(min, max).stream()
                .map(p -> new ProductResponse(p.productNo(), p.price(), p.stock()))
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsByMinStock(int minStock) {
        if (minStock < 0) {
            return Collections.emptyList();
        }
        return productRepository.findByStockGreaterThanOrEqual(minStock).stream()
                .map(p -> new ProductResponse(p.productNo(), p.price(), p.stock()))
                .collect(Collectors.toList());
    }
}
