package com.ecommerce.view;

import com.ecommerce.model.Customer;

public class CustomerView {
    public void printCustomerDetails(Customer customer) {
        System.out.println("---------------------------------------------");
        System.out.println("Customer ID : " + customer.getCustomerId());
        System.out.println("Email       : " + customer.getEmail());
        System.out.println("Credit      : " + String.format("%.2f", customer.getCredit()));
        System.out.println("---------------------------------------------");
    }

    public void printRegistrationSuccess(String customerId) {
        System.out.println("Customer registered successfully with ID: " + customerId);
    }

    public void printLoginSuccess(String email) {
        System.out.println("Welcome, " + email + "! Logged in successfully.");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printError(String error) {
        System.err.println("Error: " + error);
    }
}
