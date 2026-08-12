package com.ecommerce.view;

import com.ecommerce.model.OrderDetails;
import com.ecommerce.model.OrderStatistics;

import java.util.List;
import java.util.Map;

public class ReportView {
    public void printCustomerPurchases(Map<String, Double> purchases) {
        System.out.println("Customer ID     Total Purchase");
        System.out.println("--------------------------------");
        purchases.forEach((id, amount) ->
                System.out.println(String.format("%-15s ₹%,.2f", id, amount)));
    }

    public void printCustomersExceedingPurchase(List<String> customerIds, double limit) {
        System.out.println("Customers whose total purchase is greater than ₹" + String.format("%,.2f", limit) + ":");
        if (customerIds.isEmpty()) {
            System.out.println("None.");
        } else {
            customerIds.forEach(System.out::println);
        }
    }

    public void printProductSales(Map<String, Integer> sales, Map.Entry<String, Integer> bestSelling) {
        System.out.println("Product No      Units Sold");
        System.out.println("---------------------------");
        sales.forEach((no, qty) ->
                System.out.println(String.format("%-15s %-10d", no, qty)));
        System.out.println();
        if (bestSelling != null) {
            System.out.println("Best Selling Product : " + bestSelling.getKey());
            System.out.println("Units Sold           : " + bestSelling.getValue());
        }
    }

    public void printProductRevenue(Map<String, Double> revenues, Map.Entry<String, Double> highestRevenue) {
        System.out.println("Product No      Revenue");
        System.out.println("---------------------------");
        revenues.forEach((no, rev) ->
                System.out.println(String.format("%-15s ₹%,.2f", no, rev)));
        System.out.println();
        if (highestRevenue != null) {
            System.out.println("Highest Revenue Product: " + highestRevenue.getKey());
            System.out.println("Revenue                : ₹" + String.format("%,.2f", highestRevenue.getValue()));
        }
    }

    public void printMonthlySales(Map<String, Double> monthlySales) {
        System.out.println("Month       Total Sales");
        System.out.println("------------------------");
        monthlySales.forEach((month, amount) ->
                System.out.println(String.format("%-11s ₹%,.2f", month, amount)));
    }

    public void printCustomerOrderCounts(Map<String, Long> counts, Map.Entry<String, Long> maxOrders) {
        System.out.println("Customer ID     Number of Orders");
        System.out.println("--------------------------------");
        counts.forEach((id, qty) ->
                System.out.println(String.format("%-15s %-10d", id, qty)));
        System.out.println();
        if (maxOrders != null) {
            System.out.println("Customer Max Orders : " + maxOrders.getKey());
            System.out.println("Number of Orders    : " + maxOrders.getValue());
        }
    }

    public void printTopCustomers(List<Map.Entry<String, Double>> topCustomers) {
        System.out.println("Rank    Customer     Purchase Amount");
        System.out.println("-------------------------------------");
        int rank = 1;
        for (Map.Entry<String, Double> entry : topCustomers) {
            System.out.println(String.format("%-7d %-12s ₹%,.2f", rank++, entry.getKey(), entry.getValue()));
        }
    }

    public void printCustomerProducts(String customerId, List<String> products) {
        System.out.println("Customer ID : " + customerId);
        System.out.println();
        System.out.println("Products Purchased:");
        if (products.isEmpty()) {
            System.out.println("No purchases found.");
        } else {
            products.forEach(System.out::println);
        }
    }

    public void printProductCustomers(String productNo, List<String> customers) {
        System.out.println("Product : " + productNo);
        System.out.println();
        System.out.println("Customers:");
        if (customers.isEmpty()) {
            System.out.println("No purchases found.");
        } else {
            customers.forEach(System.out::println);
        }
    }

    public void printOrderStatistics(OrderStatistics stats) {
        System.out.println("====================================");
        System.out.println("        ORDER STATISTICS");
        System.out.println("====================================");
        System.out.println();
        System.out.println(String.format("Total Orders       : %d", stats.getTotalOrders()));
        System.out.println(String.format("Units Sold         : %d", stats.getUnitsSold()));
        System.out.println(String.format("Total Revenue      : ₹%,.2f", stats.getTotalRevenue()));
        System.out.println(String.format("Highest Order      : ₹%,.2f", stats.getHighestOrderValue()));
        System.out.println(String.format("Lowest Order       : ₹%,.2f", stats.getLowestOrderValue()));
        System.out.println(String.format("Average Order Value: ₹%,.2f", stats.getAverageOrderValue()));
    }

    public void printPeriodOrders(List<OrderDetails> orders) {
        if (orders.isEmpty()) {
            System.out.println("No orders found in specified period.");
            return;
        }
        System.out.println(String.format("%-11s %-13s %-12s %-10s %-8s %-12s",
                "Order No", "Date", "Customer", "Product", "Qty", "Amount"));
        System.out.println("----------------------------------------------------------------------");
        for (OrderDetails od : orders) {
            System.out.println(String.format("%-11d %-13s %-12s %-10s %-8d ₹%,.2f",
                    od.getOrderNo(), od.getOrderDate(), od.getCustomer().getCustomerId(),
                    od.getProduct().getProductNo(), od.getQuantity(), od.getOrderAmount()));
        }
    }
}
