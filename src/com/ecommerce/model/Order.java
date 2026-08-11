package com.ecommerce.model;

import java.time.LocalDate;

public class Order {
    private int orderNo;
    private LocalDate orderDate;
    private String customerId;
    private String productNo;
    private int quantity;

    public Order() {}

    public Order(int orderNo, LocalDate orderDate, String customerId, String productNo, int quantity) {
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.productNo = productNo;
        this.quantity = quantity;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNo=" + orderNo +
                ", orderDate=" + orderDate +
                ", customerId='" + customerId + '\'' +
                ", productNo='" + productNo + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
