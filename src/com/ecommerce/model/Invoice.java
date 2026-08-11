package com.ecommerce.model;

import java.util.Comparator;
import java.util.List;

public class Invoice {
    private Customer customer;
    private List<OrderDetails> orders;
    private double totalAmount;
    private OrderDetails highestValueOrder;
    private OrderDetails lowestValueOrder;
    private int numberOfOrders;

    public Invoice(Customer customer, List<OrderDetails> orders) {
        this.customer = customer;
        this.orders = orders;
        this.numberOfOrders = orders.size();
        this.totalAmount = orders.stream()
                .mapToDouble(OrderDetails::getOrderAmount)
                .sum();
        this.highestValueOrder = orders.stream()
                .max(Comparator.comparingDouble(OrderDetails::getOrderAmount))
                .orElse(null);
        this.lowestValueOrder = orders.stream()
                .min(Comparator.comparingDouble(OrderDetails::getOrderAmount))
                .orElse(null);
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderDetails> getOrders() {
        return orders;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public OrderDetails getHighestValueOrder() {
        return highestValueOrder;
    }

    public void setHighestValueOrder(OrderDetails highestValueOrder) {
        this.highestValueOrder = highestValueOrder;
    }

    public OrderDetails getLowestValueOrder() {
        return lowestValueOrder;
    }

    public void setLowestValueOrder(OrderDetails lowestValueOrder) {
        this.lowestValueOrder = lowestValueOrder;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }
}
