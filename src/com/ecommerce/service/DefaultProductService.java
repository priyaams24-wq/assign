package com.ecommerce.service;

import com.ecommerce.dao.ProductDao;
import com.ecommerce.exception.ECommerceException;
import com.ecommerce.model.Product;
import com.ecommerce.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DefaultProductService implements ProductService {
    private final ProductDao productDao;

    public DefaultProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void addProduct(Product product) {
        if (product.getProductNo() == null || product.getProductNo().trim().isEmpty()) {
            throw new ECommerceException("Product number cannot be empty");
        }
        if (product.getPrice() <= 0) {
            throw new ECommerceException("Product price must be greater than zero");
        }
        if (product.getStock() < 0) {
            throw new ECommerceException("Product stock cannot be negative");
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (productDao.findByProductNo(product.getProductNo(), conn).isPresent()) {
                throw new ECommerceException("Product number already exists: " + product.getProductNo());
            }
            productDao.save(product, conn);
        } catch (SQLException e) {
            throw new ECommerceException("Failed to add product: " + e.getMessage());
        }
    }

    @Override
    public Optional<Product> getProductByNo(String productNo) {
        if (productNo == null || productNo.trim().isEmpty()) {
            return Optional.empty();
        }
        try (Connection conn = DatabaseConnection.getConnection()) {
            return productDao.findByProductNo(productNo.trim(), conn);
        } catch (SQLException e) {
            throw new ECommerceException("Failed to retrieve product: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getAllProducts() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return productDao.findAll(conn);
        } catch (SQLException e) {
            throw new ECommerceException("Failed to retrieve products: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getAvailableProducts() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return productDao.findAvailable(conn);
        } catch (SQLException e) {
            throw new ECommerceException("Failed to retrieve available products: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getProductsByPriceRange(double min, double max) {
        if (min < 0 || max < min) {
            return Collections.emptyList();
        }
        try (Connection conn = DatabaseConnection.getConnection()) {
            return productDao.findByPriceRange(min, max, conn);
        } catch (SQLException e) {
            throw new ECommerceException("Failed to retrieve products by price range: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getProductsByMinStock(int minStock) {
        if (minStock < 0) {
            return Collections.emptyList();
        }
        try (Connection conn = DatabaseConnection.getConnection()) {
            return productDao.findByStockGreaterThanOrEqual(minStock, conn);
        } catch (SQLException e) {
            throw new ECommerceException("Failed to retrieve products by stock: " + e.getMessage());
        }
    }
}
