package com.ecommerce.controller;

import com.ecommerce.model.OrderDetails;
import com.ecommerce.model.OrderStatistics;
import com.ecommerce.service.AnalysisService;
import com.ecommerce.view.ReportView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class AnalysisController {
    private final AnalysisService analysisService;
    private final ReportView reportView;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public AnalysisController(AnalysisService analysisService, ReportView reportView) {
        this.analysisService = analysisService;
        this.reportView = reportView;
    }

    public void handleAnalysisMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- ANALYSIS & REPORTS ---");
            System.out.println("1. Customer Purchase Analysis (Total purchase of every customer)");
            System.out.println("2. Identify High-Value Customers (Exceeding limit)");
            System.out.println("3. Product Sales Analysis (Units sold & Best seller)");
            System.out.println("4. Product Revenue Analysis (Revenue & Highest earner)");
            System.out.println("5. Date-Based Order Report");
            System.out.println("6. Monthly Sales Analysis");
            System.out.println("7. Customer Order Analysis (Order count & Max orders customer)");
            System.out.println("8. Top 3 Customers Report");
            System.out.println("9. Customer Product Purchase Analysis (Unique products)");
            System.out.println("10. Product Customer Purchase Analysis (Unique customers)");
            System.out.println("11. Advanced Order Statistics (Summary)");
            System.out.println("12. Return to Main Menu");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();
            if ("12".equals(choice)) {
                break;
            }

            try {
                switch (choice) {
                    case "1":
                        reportView.printCustomerPurchases(analysisService.getCustomerPurchases());
                        break;
                    case "2":
                        System.out.print("Enter Purchase Limit (₹): ");
                        double limit = Double.parseDouble(scanner.nextLine());
                        reportView.printCustomersExceedingPurchase(analysisService.getCustomersExceedingPurchase(limit), limit);
                        break;
                    case "3":
                        Map<String, Integer> sales = analysisService.getProductUnitsSold();
                        Optional<Map.Entry<String, Integer>> bestSeller = analysisService.getBestSellingProduct();
                        reportView.printProductSales(sales, bestSeller.orElse(null));
                        break;
                    case "4":
                        Map<String, Double> revenues = analysisService.getProductRevenue();
                        Optional<Map.Entry<String, Double>> maxRev = analysisService.getHighestRevenueProduct();
                        reportView.printProductRevenue(revenues, maxRev.orElse(null));
                        break;
                    case "5":
                        System.out.print("Enter From Date (yyyy-MM-dd): ");
                        LocalDate from = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
                        System.out.print("Enter To Date (yyyy-MM-dd): ");
                        LocalDate to = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
                        List<OrderDetails> orders = analysisService.getOrdersInPeriod(from, to);
                        reportView.printPeriodOrders(orders);
                        break;
                    case "6":
                        reportView.printMonthlySales(analysisService.getMonthlySales());
                        break;
                    case "7":
                        Map<String, Long> counts = analysisService.getCustomerOrderCounts();
                        Optional<Map.Entry<String, Long>> maxOrd = analysisService.getCustomerWithMaxOrders();
                        reportView.printCustomerOrderCounts(counts, maxOrd.orElse(null));
                        break;
                    case "8":
                        reportView.printTopCustomers(analysisService.getTopCustomers(3));
                        break;
                    case "9":
                        System.out.print("Enter Customer ID: ");
                        String custId = scanner.nextLine();
                        reportView.printCustomerProducts(custId, analysisService.getProductsPurchasedByCustomer(custId));
                        break;
                    case "10":
                        System.out.print("Enter Product Number: ");
                        String prodNo = scanner.nextLine();
                        reportView.printProductCustomers(prodNo, analysisService.getCustomersWhoPurchasedProduct(prodNo));
                        break;
                    case "11":
                        OrderStatistics stats = analysisService.getOrderStatistics();
                        reportView.printOrderStatistics(stats);
                        break;
                    default:
                        System.err.println("Invalid option.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid numerical format.");
            } catch (DateTimeParseException e) {
                System.err.println("Error: Invalid date format. Use yyyy-MM-dd.");
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
