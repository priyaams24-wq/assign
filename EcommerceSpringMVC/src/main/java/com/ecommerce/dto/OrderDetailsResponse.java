package com.ecommerce.dto;

import java.time.LocalDate;

public record OrderDetailsResponse(
    int orderNo,
    LocalDate orderDate,
    CustomerResponse customer,
    ProductResponse product,
    double price,
    int quantity,
    double amount
) {}
