package com.ecommerce.model;

import java.time.LocalDate;

public record Order(
    int orderNo,
    LocalDate orderDate,
    String customerId,
    String productNo,
    int quantity
) {}
