package com.ecommerce.dao;

import com.ecommerce.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcProductDao implements ProductDao {
    @Override
    public void save(Product product, Connection conn) throws SQLException {
        String sql = "INSERT INTO products (product_no, price, stock) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.getProductNo());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getStock());
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Product> findByProductNo(String productNo, Connection conn) throws SQLException {
        String sql = "SELECT product_no, price, stock FROM products WHERE product_no = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, productNo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToProduct(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Product> findAll(Connection conn) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT product_no, price, stock FROM products ORDER BY product_no ASC";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        }
        return products;
    }

    @Override
    public List<Product> findAvailable(Connection conn) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT product_no, price, stock FROM products WHERE stock > 0 ORDER BY product_no ASC";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        }
        return products;
    }

    @Override
    public List<Product> findByPriceRange(double minPrice, double maxPrice, Connection conn) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT product_no, price, stock FROM products WHERE price BETWEEN ? AND ? ORDER BY price ASC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, minPrice);
            stmt.setDouble(2, maxPrice);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRowToProduct(rs));
                }
            }
        }
        return products;
    }

    @Override
    public List<Product> findByStockGreaterThanOrEqual(int quantity, Connection conn) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT product_no, price, stock FROM products WHERE stock >= ? ORDER BY product_no ASC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRowToProduct(rs));
                }
            }
        }
        return products;
    }

    @Override
    public void updateStock(String productNo, int newStock, Connection conn) throws SQLException {
        String sql = "UPDATE products SET stock = ? WHERE product_no = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newStock);
            stmt.setString(2, productNo);
            stmt.executeUpdate();
        }
    }

    private Product mapRowToProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getString("product_no"),
                rs.getDouble("price"),
                rs.getInt("stock")
        );
    }
}
