package com.ecommerce.dto;

public record ProductResponse(
    String productNo,
    double price,
    int stock
) {}
