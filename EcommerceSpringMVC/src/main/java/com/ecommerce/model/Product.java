package com.ecommerce.model;

public record Product(
    String productNo,
    double price,
    int stock
) {}
