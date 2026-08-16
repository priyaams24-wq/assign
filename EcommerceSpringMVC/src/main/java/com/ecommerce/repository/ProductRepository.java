package com.ecommerce.repository;

import com.ecommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    @Autowired
    private DataSource dataSource;

    public void save(Product product) {
        String sql = "INSERT INTO products (product_no, price, stock) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, product.productNo());
            stmt.setDouble(2, product.price());
            stmt.setInt(3, product.stock());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save product", e);
        }
    }

    public Optional<Product> findByProductNo(String productNo) {
        String sql = "SELECT product_no, price, stock FROM products WHERE product_no = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, productNo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToProduct(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find product by productNo", e);
        }
        return Optional.empty();
    }

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT product_no, price, stock FROM products ORDER BY product_no ASC";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to list all products", e);
        }
        return products;
    }

    public List<Product> findAvailable() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT product_no, price, stock FROM products WHERE stock > 0 ORDER BY product_no ASC";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find available products", e);
        }
        return products;
    }

    public List<Product> findByPriceRange(double minPrice, double maxPrice) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT product_no, price, stock FROM products WHERE price BETWEEN ? AND ? ORDER BY price ASC";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, minPrice);
            stmt.setDouble(2, maxPrice);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRowToProduct(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find products in price range", e);
        }
        return products;
    }

    public List<Product> findByStockGreaterThanOrEqual(int quantity) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT product_no, price, stock FROM products WHERE stock >= ? ORDER BY product_no ASC";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapRowToProduct(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find products by stock", e);
        }
        return products;
    }

    public void updateStock(String productNo, int newStock) {
        String sql = "UPDATE products SET stock = ? WHERE product_no = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newStock);
            stmt.setString(2, productNo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update product stock", e);
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
