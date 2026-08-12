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
}
