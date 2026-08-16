package com.ecommerce.model;

public record Customer(
    String customerId,
    String password,
    String email,
    double credit
) {}
