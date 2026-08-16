package com.ecommerce.service;

import com.ecommerce.dto.CustomerResponse;
import com.ecommerce.dto.InvoiceResponse;
import com.ecommerce.dto.OrderDetailsResponse;
import com.ecommerce.dto.OrderResponse;
import com.ecommerce.dto.ProductResponse;
import com.ecommerce.exception.ECommerceException;
import com.ecommerce.exception.EntityNotFoundException;
import com.ecommerce.exception.InsufficientCreditException;
import com.ecommerce.exception.OutOfStockException;
import com.ecommerce.model.Customer;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetails;
import com.ecommerce.model.Product;
import com.ecommerce.repository.CustomerRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DataSource dataSource;

    public OrderResponse placeOrder(String customerId, String productNo, int quantity) {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // 1. Get customer
                Customer customer = null;
                String selectCust = "SELECT customer_id, password, email, credit FROM customers WHERE customer_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(selectCust)) {
                    stmt.setString(1, customerId);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            customer = new Customer(
                                    rs.getString("customer_id"),
                                    rs.getString("password"),
                                    rs.getString("email"),
                                    rs.getDouble("credit")
                            );
                        }
                    }
                }
                if (customer == null) {
                    throw new EntityNotFoundException("Customer not found: " + customerId);
                }

                // 2. Get product
                Product product = null;
                String selectProd = "SELECT product_no, price, stock FROM products WHERE product_no = ?";
                try (PreparedStatement stmt = conn.prepareStatement(selectProd)) {
                    stmt.setString(1, productNo);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            product = new Product(
                                    rs.getString("product_no"),
                                    rs.getDouble("price"),
                                    rs.getInt("stock")
                            );
                        }
                    }
                }
                if (product == null) {
                    throw new EntityNotFoundException("Product not found: " + productNo);
                }

                if (quantity <= 0) {
                    throw new ECommerceException("Quantity must be greater than zero");
                }

                if (product.stock() < quantity) {
                    throw new OutOfStockException("Insufficient stock. Available: " + product.stock() + ", Requested: " + quantity);
                }

                double orderAmount = product.price() * quantity;
                if (customer.credit() < orderAmount) {
                    throw new InsufficientCreditException("Insufficient credit. Available: " + customer.credit() + ", Required: " + orderAmount);
                }

                // 3. Update stock
                String updateProd = "UPDATE products SET stock = ? WHERE product_no = ?";
                try (PreparedStatement stmt = conn.prepareStatement(updateProd)) {
                    stmt.setInt(1, product.stock() - quantity);
                    stmt.setString(2, productNo);
                    stmt.executeUpdate();
                }

                // 4. Update credit
                String updateCust = "UPDATE customers SET credit = ? WHERE customer_id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(updateCust)) {
                    stmt.setDouble(1, customer.credit() - orderAmount);
                    stmt.setString(2, customerId);
                    stmt.executeUpdate();
                }

                // 5. Save order
                Order order = new Order(0, LocalDate.now(), customerId, productNo, quantity);
                String insertOrder = "INSERT INTO orders (order_date, customer_id, product_no, quantity) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertOrder, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    stmt.setDate(1, java.sql.Date.valueOf(order.orderDate()));
                    stmt.setString(2, order.customerId());
                    stmt.setString(3, order.productNo());
                    stmt.setInt(4, order.quantity());
                    stmt.executeUpdate();
                    int generatedId = 0;
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            generatedId = generatedKeys.getInt(1);
                        }
                    }
                    order = new Order(generatedId, order.orderDate(), order.customerId(), order.productNo(), order.quantity());
                }

                conn.commit();
                return new OrderResponse(order.orderNo(), order.orderDate(), order.customerId(), order.productNo(), order.quantity(), orderAmount);
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            if (e instanceof ECommerceException) {
                throw (ECommerceException) e;
            }
            throw new ECommerceException("Failed to place order: " + e.getMessage());
        }
    }

    public List<OrderDetailsResponse> getCustomerOrderHistory(String customerId) {
        if (!customerRepository.findById(customerId).isPresent()) {
            throw new EntityNotFoundException("Customer not found: " + customerId);
        }
        return orderRepository.findDetailsByCustomerId(customerId).stream()
                .map(this::mapToOrderDetailsResponse)
                .collect(Collectors.toList());
    }

    public List<OrderDetailsResponse> getCustomerOrderHistoryAfter(String customerId, LocalDate afterDate) {
        List<OrderDetailsResponse> result = new ArrayList<>();
        getCustomerOrderHistory(customerId).stream()
                .filter(o -> o.orderDate().isAfter(afterDate))
                .forEach(result::add);
        return result;
    }

    public List<OrderDetailsResponse> getCustomerOrderHistoryBefore(String customerId, LocalDate beforeDate) {
        List<OrderDetailsResponse> result = new ArrayList<>();
        getCustomerOrderHistory(customerId).stream()
                .filter(o -> o.orderDate().isBefore(beforeDate))
                .forEach(result::add);
        return result;
    }

    public List<OrderDetailsResponse> getCustomerOrderHistoryBetween(String customerId, LocalDate fromDate, LocalDate toDate) {
        List<OrderDetailsResponse> result = new ArrayList<>();
        getCustomerOrderHistory(customerId).stream()
                .filter(o -> !o.orderDate().isBefore(fromDate) && !o.orderDate().isAfter(toDate))
                .forEach(result::add);
        return result;
    }

    public InvoiceResponse generateCustomerInvoice(String customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + customerId));
        List<OrderDetails> orders = orderRepository.findDetailsByCustomerId(customerId);

        List<OrderDetailsResponse> orderResponses = orders.stream()
                .map(this::mapToOrderDetailsResponse)
                .collect(Collectors.toList());

        CustomerResponse customerResponse = new CustomerResponse(customer.customerId(), customer.email(), customer.credit());
        double totalAmount = orderResponses.stream().mapToDouble(OrderDetailsResponse::amount).sum();

        OrderDetailsResponse highestOrder = orderResponses.stream()
                .max((o1, o2) -> Double.compare(o1.amount(), o2.amount()))
                .orElse(null);

        OrderDetailsResponse lowestOrder = orderResponses.stream()
                .min((o1, o2) -> Double.compare(o1.amount(), o2.amount()))
                .orElse(null);

        int numberOfOrders = orderResponses.size();

        return new InvoiceResponse(customerResponse, orderResponses, totalAmount, highestOrder, lowestOrder, numberOfOrders);
    }

    private OrderDetailsResponse mapToOrderDetailsResponse(OrderDetails d) {
        CustomerResponse cr = new CustomerResponse(d.customer().customerId(), d.customer().email(), d.customer().credit());
        ProductResponse pr = new ProductResponse(d.product().productNo(), d.product().price(), d.product().stock());
        return new OrderDetailsResponse(d.orderNo(), d.orderDate(), cr, pr, d.productPrice(), d.quantity(), d.orderAmount());
    }
}
