package com.ecommerce.controller;

import com.ecommerce.model.Customer;
import com.ecommerce.service.CustomerService;
import com.ecommerce.view.CustomerView;

import java.util.Optional;
import java.util.Scanner;

public class CustomerController {
    private final CustomerService customerService;
    private final CustomerView customerView;

    public CustomerController(CustomerService customerService, CustomerView customerView) {
        this.customerService = customerService;
        this.customerView = customerView;
    }

    public Optional<Customer> handleLogin(Scanner scanner) {
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        Optional<Customer> customerOpt = customerService.login(email, password);
        if (customerOpt.isPresent()) {
            customerView.printLoginSuccess(customerOpt.get().getEmail());
        } else {
            customerView.printError("Invalid credentials or customer does not exist.");
        }
        return customerOpt;
    }

    public void handleRegistration(Scanner scanner) {
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Initial Credit: ");
        double credit;
        try {
            credit = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            customerView.printError("Invalid credit format.");
            return;
        }

        try {
            Customer customer = new Customer(customerId, password, email, credit);
            customerService.register(customer);
            customerView.printRegistrationSuccess(customerId);
        } catch (Exception e) {
            customerView.printError(e.getMessage());
        }
    }
}
