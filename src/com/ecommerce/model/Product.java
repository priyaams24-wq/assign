package com.ecommerce.model;

public class Product {
    private String productNo;
    private double price;
    private int stock;

    public Product() {}

    public Product(String productNo, double price, int stock) {
        this.productNo = productNo;
        this.price = price;
        this.stock = stock;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productNo='" + productNo + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
