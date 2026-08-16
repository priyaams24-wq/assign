package com.ecommerce.model;

import java.time.LocalDate;

public record OrderDetails(
    int orderNo,
    LocalDate orderDate,
    Customer customer,
    Product product,
    double productPrice,
    int quantity,
    double orderAmount
) {
    public OrderDetails(int orderNo, LocalDate orderDate, Customer customer, Product product, double productPrice, int quantity) {
        this(orderNo, orderDate, customer, product, productPrice, quantity, productPrice * quantity);
    }
}
