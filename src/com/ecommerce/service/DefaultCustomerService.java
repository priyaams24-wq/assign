package com.ecommerce.service;

import com.ecommerce.dao.CustomerDao;
import com.ecommerce.exception.ECommerceException;
import com.ecommerce.model.Customer;
import com.ecommerce.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class DefaultCustomerService implements CustomerService {
    private final CustomerDao customerDao;

    public DefaultCustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public void register(Customer customer) {
        if (customer.getCustomerId() == null || customer.getCustomerId().trim().isEmpty()) {
            throw new ECommerceException("Customer ID cannot be empty");
        }
        if (customer.getPassword() == null || customer.getPassword().trim().isEmpty()) {
            throw new ECommerceException("Password cannot be empty");
        }
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new ECommerceException("Email cannot be empty");
        }
        if (customer.getCredit() < 0) {
            throw new ECommerceException("Credit cannot be negative");
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (customerDao.findById(customer.getCustomerId(), conn).isPresent()) {
                throw new ECommerceException("Customer ID already exists: " + customer.getCustomerId());
            }
            boolean emailExists = customerDao.findAll(conn).stream()
                    .anyMatch(c -> c.getEmail().equalsIgnoreCase(customer.getEmail()));
            if (emailExists) {
                throw new ECommerceException("Email address already exists: " + customer.getEmail());
            }
            customerDao.save(customer, conn);
        } catch (SQLException e) {
            throw new ECommerceException("Failed to register customer: " + e.getMessage());
        }
    }

    @Override
    public Optional<Customer> login(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return Optional.empty();
        }
        try (Connection conn = DatabaseConnection.getConnection()) {
            return customerDao.findByEmailAndPassword(email.trim(), password, conn);
        } catch (SQLException e) {
            throw new ECommerceException("Login failed: " + e.getMessage());
        }
    }

    @Override
    public Optional<Customer> getCustomerById(String customerId) {
        if (customerId == null || customerId.trim().isEmpty()) {
            return Optional.empty();
        }
        try (Connection conn = DatabaseConnection.getConnection()) {
            return customerDao.findById(customerId.trim(), conn);
        } catch (SQLException e) {
            throw new ECommerceException("Failed to retrieve customer: " + e.getMessage());
        }
    }
}
