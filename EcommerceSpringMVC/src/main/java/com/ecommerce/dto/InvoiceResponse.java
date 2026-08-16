package com.ecommerce.dto;

import java.util.List;

public record InvoiceResponse(
    CustomerResponse customer,
    List<OrderDetailsResponse> orders,
    double totalAmount,
    OrderDetailsResponse highestOrder,
    OrderDetailsResponse lowestOrder,
    int numberOfOrders
) {}
