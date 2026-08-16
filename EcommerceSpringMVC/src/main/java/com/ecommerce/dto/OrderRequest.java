package com.ecommerce.dto;

public record OrderRequest(
    String productNo,
    int quantity
) {}
