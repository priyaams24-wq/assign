package com.ecommerce.dto;

import java.time.LocalDate;

public record OrderResponse(
    int orderNo,
    LocalDate orderDate,
    String customerId,
    String productNo,
    int quantity,
    double amount
) {}
