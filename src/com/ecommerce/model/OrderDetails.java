package com.ecommerce.model;

import java.time.LocalDate;

public class OrderDetails {
    private int orderNo;
    private LocalDate orderDate;
    private Customer customer;
    private Product product;
    private double productPrice;
    private int quantity;
    private double orderAmount;

    public OrderDetails() {}

    public OrderDetails(int orderNo, LocalDate orderDate, Customer customer, Product product, double productPrice, int quantity) {
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.customer = customer;
        this.product = product;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.orderAmount = productPrice * quantity;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
        this.orderAmount = productPrice * this.quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.orderAmount = this.productPrice * quantity;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderNo=" + orderNo +
                ", orderDate=" + orderDate +
                ", customer=" + customer +
                ", product=" + product +
                ", productPrice=" + productPrice +
                ", quantity=" + quantity +
                ", orderAmount=" + orderAmount +
                '}';
    }
}
