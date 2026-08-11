package com.ecommerce.dao;

import com.ecommerce.model.Customer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    void save(Customer customer, Connection conn) throws SQLException;
    Optional<Customer> findById(String customerId, Connection conn) throws SQLException;
    Optional<Customer> findByEmailAndPassword(String email, String password, Connection conn) throws SQLException;
    void updateCredit(String customerId, double newCredit, Connection conn) throws SQLException;
    List<Customer> findAll(Connection conn) throws SQLException;
}
