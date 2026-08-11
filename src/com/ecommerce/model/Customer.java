package com.ecommerce.model;

public class Customer {
    private String customerId;
    private String password;
    private String email;
    private double credit;

    public Customer() {}

    public Customer(String customerId, String password, String email, double credit) {
        this.customerId = customerId;
        this.password = password;
        this.email = email;
        this.credit = credit;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", email='" + email + '\'' +
                ", credit=" + credit +
                '}';
    }
}
