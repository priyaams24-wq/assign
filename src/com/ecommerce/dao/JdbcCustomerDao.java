package com.ecommerce.dao;

import com.ecommerce.model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCustomerDao implements CustomerDao {
    @Override
    public void save(Customer customer, Connection conn) throws SQLException {
        String sql = "INSERT INTO customers (customer_id, password, email, credit) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getCustomerId());
            stmt.setString(2, customer.getPassword());
            stmt.setString(3, customer.getEmail());
            stmt.setDouble(4, customer.getCredit());
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Customer> findById(String customerId, Connection conn) throws SQLException {
        String sql = "SELECT customer_id, password, email, credit FROM customers WHERE customer_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToCustomer(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByEmailAndPassword(String email, String password, Connection conn) throws SQLException {
        String sql = "SELECT customer_id, password, email, credit FROM customers WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRowToCustomer(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void updateCredit(String customerId, double newCredit, Connection conn) throws SQLException {
        String sql = "UPDATE customers SET credit = ? WHERE customer_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newCredit);
            stmt.setString(2, customerId);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Customer> findAll(Connection conn) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT customer_id, password, email, credit FROM customers";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                customers.add(mapRowToCustomer(rs));
            }
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
