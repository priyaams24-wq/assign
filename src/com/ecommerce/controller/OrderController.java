package com.ecommerce.controller;

import com.ecommerce.model.Customer;
import com.ecommerce.model.Invoice;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetails;
import com.ecommerce.service.OrderService;
import com.ecommerce.view.OrderView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class OrderController {
    private final OrderService orderService;
    private final OrderView orderView;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public OrderController(OrderService orderService, OrderView orderView) {
        this.orderService = orderService;
        this.orderView = orderView;
    }

    public void handlePlaceOrder(Customer loggedInCustomer, Scanner scanner) {
        try {
            System.out.print("Enter Product Number: ");
            String productNo = scanner.nextLine();
            System.out.print("Enter Quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            Order order = orderService.placeOrder(loggedInCustomer.getCustomerId(), productNo, quantity);
            orderView.printOrderReceipt(order);
        } catch (NumberFormatException e) {
            orderView.printError("Invalid quantity format.");
        } catch (Exception e) {
            orderView.printError(e.getMessage());
        }
    }

    public void handleViewOrderHistory(Customer loggedInCustomer, Scanner scanner) {
        System.out.println("Order History Filter Options:");
        System.out.println("1. All Orders");
        System.out.println("2. Orders After a Particular Date");
        System.out.println("3. Orders Before a Particular Date");
        System.out.println("4. Orders within a Date Range");
        System.out.print("Choose an option: ");

        String choice = scanner.nextLine();
        try {
            List<OrderDetails> history;
            switch (choice) {
                case "1":
                    history = orderService.getCustomerOrderHistory(loggedInCustomer.getCustomerId());
                    break;
                case "2":
                    System.out.print("Enter Date (yyyy-MM-dd): ");
                    LocalDate afterDate = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
                    history = orderService.getCustomerOrderHistoryAfter(loggedInCustomer.getCustomerId(), afterDate);
                    break;
                case "3":
                    System.out.print("Enter Date (yyyy-MM-dd): ");
                    LocalDate beforeDate = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
                    history = orderService.getCustomerOrderHistoryBefore(loggedInCustomer.getCustomerId(), beforeDate);
                    break;
                case "4":
                    System.out.print("Enter From Date (yyyy-MM-dd): ");
                    LocalDate fromDate = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
                    System.out.print("Enter To Date (yyyy-MM-dd): ");
                    LocalDate toDate = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
                    history = orderService.getCustomerOrderHistoryBetween(loggedInCustomer.getCustomerId(), fromDate, toDate);
                    break;
                default:
                    orderView.printError("Invalid option.");
                    return;
            }
            orderView.printOrderHistory(history);
        } catch (DateTimeParseException e) {
            orderView.printError("Invalid date format. Please use yyyy-MM-dd.");
        } catch (Exception e) {
            orderView.printError(e.getMessage());
        }
    }

    public void handleGenerateInvoice(Customer loggedInCustomer) {
        try {
            Invoice invoice = orderService.generateCustomerInvoice(loggedInCustomer.getCustomerId());
            orderView.printInvoice(invoice);
        } catch (Exception e) {
            orderView.printError(e.getMessage());
        }
    }
}
