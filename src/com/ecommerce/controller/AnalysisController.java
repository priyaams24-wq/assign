package com.ecommerce.controller;

import com.ecommerce.service.AnalysisService;
import com.ecommerce.view.ReportView;

import java.util.Map;
import java.util.Scanner;

public class AnalysisController {
    private final AnalysisService analysisService;
    private final ReportView reportView;

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
                        reportView.printCustomersExceedingPurchase(analysisService.getCustomersExceedingPurchase(limit),
                                limit);
                        break;
                    case "3":
                        Map<String, Integer> sales = analysisService.getProductUnitsSold();
                        Map.Entry<String, Integer> bestSeller = null;
                        boolean bestSellerUnderDev = false;
                        try {
                            bestSeller = analysisService.getBestSellingProduct().orElse(null);
                        } catch (UnsupportedOperationException e) {
                            bestSellerUnderDev = true;
                        }
                        reportView.printProductSales(sales, bestSeller);
                        if (bestSellerUnderDev) {
                            System.out.println("Best Selling Product : [xyz]");
                        }
                        break;
                    case "4":
                    case "5":
                    case "6":
                    case "7":
                    case "8":
                    case "9":
                    case "10":
                    case "11":
                        System.out.println("yet to do.");
                        break;
                    default:
                        System.err.println("Invalid option.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid numerical format.");
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
