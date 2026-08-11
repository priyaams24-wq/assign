package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;
import com.ecommerce.view.ProductView;

import java.util.List;
import java.util.Scanner;

public class ProductController {
    private final ProductService productService;
    private final ProductView productView;

    public ProductController(ProductService productService, ProductView productView) {
        this.productService = productService;
        this.productView = productView;
    }

    public void handleDisplayAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            productView.printProductList(products);
        } catch (Exception e) {
            productView.printError(e.getMessage());
        }
    }

    public void handleDisplayAvailableProducts() {
        try {
            List<Product> products = productService.getAvailableProducts();
            productView.printProductList(products);
        } catch (Exception e) {
            productView.printError(e.getMessage());
        }
    }

    public void handleSearchProductsByPrice(Scanner scanner) {
        try {
            System.out.print("Enter Minimum Price: ");
            double min = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter Maximum Price: ");
            double max = Double.parseDouble(scanner.nextLine());

            List<Product> products = productService.getProductsByPriceRange(min, max);
            productView.printProductList(products);
        } catch (NumberFormatException e) {
            productView.printError("Invalid price format.");
        } catch (Exception e) {
            productView.printError(e.getMessage());
        }
    }

    public void handleSearchProductsByStock(Scanner scanner) {
        try {
            System.out.print("Enter Minimum Stock Quantity: ");
            int minStock = Integer.parseInt(scanner.nextLine());

            List<Product> products = productService.getProductsByMinStock(minStock);
            productView.printProductList(products);
        } catch (NumberFormatException e) {
            productView.printError("Invalid stock format.");
        } catch (Exception e) {
            productView.printError(e.getMessage());
        }
    }
}
