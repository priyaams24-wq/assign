package com.ecommerce.service;

import com.ecommerce.model.OrderDetails;
import com.ecommerce.model.OrderStatistics;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AnalysisService {
    Map<String, Double> getCustomerPurchases();
    List<String> getCustomersExceedingPurchase(double limit);
    Map<String, Integer> getProductUnitsSold();
    Optional<Map.Entry<String, Integer>> getBestSellingProduct();
    Map<String, Double> getProductRevenue();
    Optional<Map.Entry<String, Double>> getHighestRevenueProduct();
    List<OrderDetails> getOrdersInPeriod(LocalDate from, LocalDate to);
    Map<String, Double> getMonthlySales();
    Map<String, Long> getCustomerOrderCounts();
    Optional<Map.Entry<String, Long>> getCustomerWithMaxOrders();
    List<Map.Entry<String, Double>> getTopCustomers(int limit);
    List<String> getProductsPurchasedByCustomer(String customerId);
    List<String> getCustomersWhoPurchasedProduct(String productNo);
    OrderStatistics getOrderStatistics();
}
