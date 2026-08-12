package com.ecommerce.service;

import com.ecommerce.dao.OrderDao;
import com.ecommerce.exception.ECommerceException;
import com.ecommerce.model.OrderDetails;
import com.ecommerce.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
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
        throw new UnsupportedOperationException("Best-selling product analysis is currently under development.");
    }
}
