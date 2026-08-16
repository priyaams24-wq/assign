package com.ecommerce.service;

import com.ecommerce.dto.CustomerRegisterRequest;
import com.ecommerce.dto.CustomerResponse;
import com.ecommerce.exception.ECommerceException;
import com.ecommerce.model.Customer;
import com.ecommerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public void register(CustomerRegisterRequest request) {
        if (request.customerId() == null || request.customerId().trim().isEmpty()) {
            throw new ECommerceException("Customer ID cannot be empty");
        }
        if (request.password() == null || request.password().trim().isEmpty()) {
            throw new ECommerceException("Password cannot be empty");
        }
        if (request.email() == null || request.email().trim().isEmpty()) {
            throw new ECommerceException("Email cannot be empty");
        }
        if (request.credit() < 0) {
            throw new ECommerceException("Credit cannot be negative");
        }

        if (customerRepository.findById(request.customerId()).isPresent()) {
            throw new ECommerceException("Customer ID already exists: " + request.customerId());
        }
        boolean emailExists = customerRepository.findAll().stream()
y so mny error                .anyMatch(c -> c.email().equalsIgnoreCase(request.email()));
        if (emailExists) {
            throw new ECommerceException("Email address already exists: " + request.email());
        }

        Customer customer = new Customer(
                request.customerId().trim(),
                request.password(),
                request.email().trim(),
                request.credit()
        );
        customerRepository.save(customer);
    }

    public Optional<CustomerResponse> login(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return Optional.empty();
        }
        return customerRepository.findByEmailAndPassword(email.trim(), password)
                .map(c -> new CustomerResponse(c.customerId(), c.email(), c.credit()));
    }

    public Optional<CustomerResponse> getCustomerById(String customerId) {
        if (customerId == null || customerId.trim().isEmpty()) {
            return Optional.empty();
        }
        return customerRepository.findById(customerId.trim())
                .map(c -> new CustomerResponse(c.customerId(), c.email(), c.credit()));
    }
}
