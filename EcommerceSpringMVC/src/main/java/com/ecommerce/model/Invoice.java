package com.ecommerce.model;

import java.util.Comparator;
import java.util.List;

public record Invoice(
    Customer customer,
    List<OrderDetails> orders,
    double totalAmount,
    OrderDetails highestValueOrder,
    OrderDetails lowestValueOrder,
    int numberOfOrders
) {
    public Invoice(Customer customer, List<OrderDetails> orders) {
        this(
            customer,
            orders,
            orders.stream().mapToDouble(OrderDetails::orderAmount).sum(),
            orders.stream().max(Comparator.comparingDouble(OrderDetails::orderAmount)).orElse(null),
            orders.stream().min(Comparator.comparingDouble(OrderDetails::orderAmount)).orElse(null),
            orders.size()
        );
    }
}
