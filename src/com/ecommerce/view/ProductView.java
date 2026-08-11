package com.ecommerce.view;

import com.ecommerce.model.Product;
import java.util.List;

public class ProductView {
    public void printProductList(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        System.out.println(String.format("%-15s %-15s %-10s", "Product No", "Price", "Stock"));
        System.out.println("---------------------------------------------");
        for (Product product : products) {
            System.out.println(String.format("%-15s %-15.2f %-10d",
                    product.getProductNo(), product.getPrice(), product.getStock()));
        }
    }

    public void printProductDetails(Product product) {
        System.out.println("---------------------------------------------");
        System.out.println("Product No : " + product.getProductNo());
        System.out.println("Price      : " + String.format("%.2f", product.getPrice()));
        System.out.println("Stock      : " + product.getStock());
        System.out.println("---------------------------------------------");
    }

    public void printError(String error) {
        System.err.println("Error: " + error);
    }
}
