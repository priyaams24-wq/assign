package com.ecommerce.service;

import com.ecommerce.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    void addProduct(Product product);
    Optional<Product> getProductByNo(String productNo);
    List<Product> getAllProducts();
    List<Product> getAvailableProducts();
    List<Product> getProductsByPriceRange(double min, double max);
    List<Product> getProductsByMinStock(int minStock);
}
