package com.ecommerce.dto;

public record CustomerRegisterRequest(
    String customerId,
    String password,
    String email,
    double credit
) {}
