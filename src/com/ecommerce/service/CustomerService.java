package com.ecommerce.service;

import com.ecommerce.model.Customer;
import java.util.Optional;

public interface CustomerService {
    void register(Customer customer);
    Optional<Customer> login(String email, String password);
    Optional<Customer> getCustomerById(String customerId);
}
