package com.ecommerce.app;

import com.ecommerce.controller.AnalysisController;
import com.ecommerce.controller.CustomerController;
import com.ecommerce.controller.OrderController;
import com.ecommerce.controller.ProductController;
import com.ecommerce.dao.CustomerDao;
import com.ecommerce.dao.JdbcCustomerDao;
import com.ecommerce.dao.JdbcOrderDao;
import com.ecommerce.dao.JdbcProductDao;
import com.ecommerce.dao.OrderDao;
import com.ecommerce.dao.ProductDao;
import com.ecommerce.model.Customer;
import com.ecommerce.service.AnalysisService;
import com.ecommerce.service.CustomerService;
import com.ecommerce.service.DefaultAnalysisService;
import com.ecommerce.service.DefaultCustomerService;
import com.ecommerce.service.DefaultOrderService;
import com.ecommerce.service.DefaultProductService;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.ProductService;
import com.ecommerce.view.CustomerView;
import com.ecommerce.view.OrderView;
import com.ecommerce.view.ProductView;
import com.ecommerce.view.ReportView;

import java.util.Optional;
import java.util.Scanner;

public class ECommerceApplication {
    public static void main(String[] args) {
        CustomerDao customerDao = new JdbcCustomerDao();
        ProductDao productDao = new JdbcProductDao();
        OrderDao orderDao = new JdbcOrderDao();

        CustomerService customerService = new DefaultCustomerService(customerDao);
        ProductService productService = new DefaultProductService(productDao);
        OrderService orderService = new DefaultOrderService(customerDao, productDao, orderDao);
        AnalysisService analysisService = new DefaultAnalysisService(orderDao);

        CustomerView customerView = new CustomerView();
        ProductView productView = new ProductView();
        OrderView orderView = new OrderView();
        ReportView reportView = new ReportView();

        CustomerController customerController = new CustomerController(customerService, customerView);
        ProductController productController = new ProductController(productService, productView);
        OrderController orderController = new OrderController(orderService, orderView);
        AnalysisController analysisController = new AnalysisController(analysisService, reportView);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== CUSTOMER ORDER & INVOICE MANAGEMENT SYSTEM ===");
            System.out.println("1. Customer Login");
            System.out.println("2. Customer Registration");
            System.out.println("3. Reports & Analytics");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();
            if ("4".equals(choice)) {
                break;
            }

            switch (choice) {
                case "1":
                    Optional<Customer> loggedIn = customerController.handleLogin(scanner);
                    if (loggedIn.isPresent()) {
                        handleCustomerMenu(loggedIn.get(), customerService, productController, orderController, scanner);
                    }
                    break;
                case "2":
                    customerController.handleRegistration(scanner);
                    break;
                case "3":
                    analysisController.handleAnalysisMenu(scanner);
                    break;
                default:
                    System.err.println("Invalid option.");
            }
        }
        scanner.close();
        System.out.println("Goodbye!");
    }

    private static void handleCustomerMenu(Customer customer, CustomerService customerService,
                                           ProductController productController, OrderController orderController,
                                           Scanner scanner) {
        Customer currentCustomer = customer;
        while (true) {
            Optional<Customer> updated = customerService.getCustomerById(currentCustomer.getCustomerId());
            if (updated.isPresent()) {
                currentCustomer = updated.get();
            }

            System.out.println("\n--- CUSTOMER PORTAL ---");
            System.out.println("Logged in: " + currentCustomer.getEmail() + " | Credit: ₹" + String.format("%,.2f", currentCustomer.getCredit()));
            System.out.println("1. Display All Products");
            System.out.println("2. Display Products Currently Available");
            System.out.println("3. Search Products by Price Range");
            System.out.println("4. Search Products by Stock");
            System.out.println("5. Place an Order");
            System.out.println("6. View Order History");
            System.out.println("7. Generate Invoice");
            System.out.println("8. Logout");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();
            if ("8".equals(choice)) {
                System.out.println("Logged out.");
                break;
            }

            switch (choice) {
                case "1":
                    productController.handleDisplayAllProducts();
                    break;
                case "2":
                    productController.handleDisplayAvailableProducts();
                    break;
                case "3":
                    productController.handleSearchProductsByPrice(scanner);
                    break;
                case "4":
                    productController.handleSearchProductsByStock(scanner);
                    break;
                case "5":
                    orderController.handlePlaceOrder(currentCustomer, scanner);
                    break;
                case "6":
                    orderController.handleViewOrderHistory(currentCustomer, scanner);
                    break;
                case "7":
                    orderController.handleGenerateInvoice(currentCustomer);
                    break;
                default:
                    System.err.println("Invalid option.");
            }
        }
    }
}
