package com.ecommerce.view;

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
}
