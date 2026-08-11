package com.ecommerce.view;

import com.ecommerce.model.Invoice;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetails;

import java.util.List;

public class OrderView {
    public void printOrderReceipt(Order order) {
        System.out.println("Order placed successfully!");
        System.out.println("Order No   : " + order.getOrderNo());
        System.out.println("Date       : " + order.getOrderDate());
        System.out.println("Product    : " + order.getProductNo());
        System.out.println("Quantity   : " + order.getQuantity());
    }

    public void printOrderHistory(List<OrderDetails> orders) {
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }
        System.out.println(String.format("%-11s %-13s %-10s %-8s", "Order No", "Date", "Product", "Quantity"));
        System.out.println("------------------------------------------------");
        for (OrderDetails od : orders) {
            System.out.println(String.format("%-11d %-13s %-10s %-8d",
                    od.getOrderNo(), od.getOrderDate(), od.getProduct().getProductNo(), od.getQuantity()));
        }
    }

    public void printInvoice(Invoice invoice) {
        System.out.println("=============================================");
        System.out.println("                 CUSTOMER INVOICE");
        System.out.println("=============================================");
        System.out.println();
        System.out.println("Customer ID : " + invoice.getCustomer().getCustomerId());
        System.out.println("Email       : " + invoice.getCustomer().getEmail());
        System.out.println();
        System.out.println("---------------------------------------------");
        System.out.println(String.format("%-11s %-10s %-6s %-7s %-8s", "Order No", "Product", "Qty", "Price", "Amount"));
        System.out.println("---------------------------------------------");
        for (OrderDetails od : invoice.getOrders()) {
            System.out.println(String.format("%-11d %-10s %-6d %-7.0f %-8.0f",
                    od.getOrderNo(),
                    od.getProduct().getProductNo(),
                    od.getQuantity(),
                    od.getProductPrice(),
                    od.getOrderAmount()));
        }
        System.out.println("---------------------------------------------");
        System.out.println();
        System.out.println(String.format("%-32s ₹%.0f", "TOTAL", invoice.getTotalAmount()));
        System.out.println("=============================================");
        System.out.println();
        System.out.println("Invoice Statistics:");
        System.out.println("Number of Orders: " + invoice.getNumberOfOrders());
        if (invoice.getHighestValueOrder() != null) {
            System.out.println("Highest Order:");
            System.out.println("Order No : " + invoice.getHighestValueOrder().getOrderNo());
            System.out.println("Amount   : ₹" + String.format("%,.0f", invoice.getHighestValueOrder().getOrderAmount()));
        }
        if (invoice.getLowestValueOrder() != null) {
            System.out.println("Lowest Order:");
            System.out.println("Order No : " + invoice.getLowestValueOrder().getOrderNo());
            System.out.println("Amount   : ₹" + String.format("%,.0f", invoice.getLowestValueOrder().getOrderAmount()));
        }
    }

    public void printError(String error) {
        System.err.println("Error: " + error);
    }
}
