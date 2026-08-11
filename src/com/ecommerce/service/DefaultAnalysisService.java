package com.ecommerce.service;

import com.ecommerce.dao.OrderDao;
import com.ecommerce.exception.ECommerceException;
import com.ecommerce.model.OrderDetails;
import com.ecommerce.model.OrderStatistics;
import com.ecommerce.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultAnalysisService implements AnalysisService {
    private final OrderDao orderDao;

    public DefaultAnalysisService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    private List<OrderDetails> getAllDetails() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return orderDao.findAllDetails(conn);
        } catch (SQLException e) {
            throw new ECommerceException("Failed to retrieve analysis data: " + e.getMessage());
        }
    }

    @Override
    public Map<String, Double> getCustomerPurchases() {
        return getAllDetails().stream().collect(Collectors.groupingBy(
                od -> od.getCustomer().getCustomerId(),
                Collectors.summingDouble(OrderDetails::getOrderAmount)
        ));
    }

    @Override
    public List<String> getCustomersExceedingPurchase(double limit) {
        return getCustomerPurchases().entrySet().stream()
                .filter(entry -> entry.getValue() > limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Integer> getProductUnitsSold() {
        return getAllDetails().stream().collect(Collectors.groupingBy(
                od -> od.getProduct().getProductNo(),
                Collectors.summingInt(OrderDetails::getQuantity)
        ));
    }

    @Override
    public Optional<Map.Entry<String, Integer>> getBestSellingProduct() {
        return getProductUnitsSold().entrySet().stream()
                .max(Map.Entry.comparingByValue());
    }

    @Override
    public Map<String, Double> getProductRevenue() {
        return getAllDetails().stream().collect(Collectors.groupingBy(
                od -> od.getProduct().getProductNo(),
                Collectors.summingDouble(OrderDetails::getOrderAmount)
        ));
    }

    @Override
    public Optional<Map.Entry<String, Double>> getHighestRevenueProduct() {
        return getProductRevenue().entrySet().stream()
                .max(Map.Entry.comparingByValue());
    }

    @Override
    public List<OrderDetails> getOrdersInPeriod(LocalDate from, LocalDate to) {
        if (from == null || to == null || to.isBefore(from)) {
            return Collections.emptyList();
        }
        return getAllDetails().stream()
                .filter(od -> !od.getOrderDate().isBefore(from) && !od.getOrderDate().isAfter(to))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Double> getMonthlySales() {
        return getAllDetails().stream().collect(Collectors.groupingBy(
                od -> od.getOrderDate().getMonth().name(),
                Collectors.summingDouble(OrderDetails::getOrderAmount)
        ));
    }

    @Override
    public Map<String, Long> getCustomerOrderCounts() {
        return getAllDetails().stream().collect(Collectors.groupingBy(
                od -> od.getCustomer().getCustomerId(),
                Collectors.counting()
        ));
    }

    @Override
    public Optional<Map.Entry<String, Long>> getCustomerWithMaxOrders() {
        return getCustomerOrderCounts().entrySet().stream()
                .max(Map.Entry.comparingByValue());
    }

    @Override
    public List<Map.Entry<String, Double>> getTopCustomers(int limit) {
        if (limit <= 0) {
            return Collections.emptyList();
        }
        return getCustomerPurchases().entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getProductsPurchasedByCustomer(String customerId) {
        if (customerId == null) {
            return Collections.emptyList();
        }
        return getAllDetails().stream()
                .filter(od -> od.getCustomer().getCustomerId().equals(customerId))
                .map(od -> od.getProduct().getProductNo())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getCustomersWhoPurchasedProduct(String productNo) {
        if (productNo == null) {
            return Collections.emptyList();
        }
        return getAllDetails().stream()
                .filter(od -> od.getProduct().getProductNo().equals(productNo))
                .map(od -> od.getCustomer().getCustomerId())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public OrderStatistics getOrderStatistics() {
        List<OrderDetails> allDetails = getAllDetails();
        long totalOrders = allDetails.size();
        long unitsSold = allDetails.stream().mapToLong(OrderDetails::getQuantity).sum();
        double totalRevenue = allDetails.stream().mapToDouble(OrderDetails::getOrderAmount).sum();
        double highestOrderValue = allDetails.stream().mapToDouble(OrderDetails::getOrderAmount).max().orElse(0.0);
        double lowestOrderValue = allDetails.stream().mapToDouble(OrderDetails::getOrderAmount).min().orElse(0.0);
        double averageOrderValue = totalOrders == 0 ? 0.0 : totalRevenue / totalOrders;
        return new OrderStatistics(totalOrders, unitsSold, totalRevenue, highestOrderValue, lowestOrderValue, averageOrderValue);
    }
}
