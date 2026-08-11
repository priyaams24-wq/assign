package com.ecommerce.dao;

import com.ecommerce.model.Product;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProductDao {
    void save(Product product, Connection conn) throws SQLException;
    Optional<Product> findByProductNo(String productNo, Connection conn) throws SQLException;
    List<Product> findAll(Connection conn) throws SQLException;
    List<Product> findAvailable(Connection conn) throws SQLException;
    List<Product> findByPriceRange(double minPrice, double maxPrice, Connection conn) throws SQLException;
    List<Product> findByStockGreaterThanOrEqual(int quantity, Connection conn) throws SQLException;
    void updateStock(String productNo, int newStock, Connection conn) throws SQLException;
}
