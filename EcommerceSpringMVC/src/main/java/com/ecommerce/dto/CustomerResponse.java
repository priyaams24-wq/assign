package com.ecommerce.dto;

public record CustomerResponse(
    String customerId,
    String email,
    double credit
) {}
