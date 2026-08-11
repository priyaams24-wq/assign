package com.ecommerce.model;

public class OrderStatistics {
    private final long totalOrders;
    private final long unitsSold;
    private final double totalRevenue;
    private final double highestOrderValue;
    private final double lowestOrderValue;
    private final double averageOrderValue;

    public OrderStatistics(long totalOrders, long unitsSold, double totalRevenue,
                           double highestOrderValue, double lowestOrderValue, double averageOrderValue) {
        this.totalOrders = totalOrders;
        this.unitsSold = unitsSold;
        this.totalRevenue = totalRevenue;
        this.highestOrderValue = highestOrderValue;
        this.lowestOrderValue = lowestOrderValue;
        this.averageOrderValue = averageOrderValue;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public long getUnitsSold() {
        return unitsSold;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public double getHighestOrderValue() {
        return highestOrderValue;
    }

    public double getLowestOrderValue() {
        return lowestOrderValue;
    }

    public double getAverageOrderValue() {
        return averageOrderValue;
    }
}
