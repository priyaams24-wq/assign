package com.ecommerce.repository;

import com.ecommerce.model.Customer;
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
public class CustomerRepository {

    @Autowired
    private DataSource dataSource;

    public void save(Customer customer) {
        String sql = "INSERT INTO customers (customer_id, password, email, credit) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.customerId());
            stmt.setString(2, customer.password());
            stmt.setString(3, customer.email());
            stmt.setDouble(4, customer.credit());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save customer", e);
        }
    }

    public Optional<Customer> findById(String customerId) {
        String sql = "SELECT customer_id, password, email, credit FROM customers WHERE customer_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToCustomer(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find customer by id", e);
        }
        return Optional.empty();
    }

    public Optional<Customer> findByEmailAndPassword(String email, String password) {
        String sql = "SELECT customer_id, password, email, credit FROM customers WHERE email = ? AND password = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToCustomer(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find customer by credentials", e);
        }
        return Optional.empty();
    }

    public void updateCredit(String customerId, double newCredit) {
        String sql = "UPDATE customers SET credit = ? WHERE customer_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newCredit);
            stmt.setString(2, customerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update customer credit", e);
        }
    }

    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT customer_id, password, email, credit FROM customers ORDER BY customer_id ASC";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                customers.add(mapRowToCustomer(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to list customers", e);
        }
        return customers;
    }

    private Customer mapRowToCustomer(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getString("customer_id"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getDouble("credit")
        );
    }
}
