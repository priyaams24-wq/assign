package com.ecommerce.service;

import com.ecommerce.dao.CustomerDao;
import com.ecommerce.dao.OrderDao;
import com.ecommerce.dao.ProductDao;
import com.ecommerce.exception.ECommerceException;
import com.ecommerce.exception.EntityNotFoundException;
import com.ecommerce.exception.InsufficientCreditException;
import com.ecommerce.exception.OutOfStockException;
import com.ecommerce.model.Customer;
import com.ecommerce.model.Invoice;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetails;
import com.ecommerce.model.Product;
import com.ecommerce.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DefaultOrderService implements OrderService {
    private final CustomerDao customerDao;
    private final ProductDao productDao;
    private final OrderDao orderDao;

    public DefaultOrderService(CustomerDao customerDao, ProductDao productDao, OrderDao orderDao) {
        this.customerDao = customerDao;
        this.productDao = productDao;
        this.orderDao = orderDao;
    }

    @Override
    public Order placeOrder(String customerId, String productNo, int quantity) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            Customer customer = customerDao.findById(customerId, conn)
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + customerId));

            Product product = productDao.findByProductNo(productNo, conn)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found: " + productNo));

            if (quantity <= 0) {
                throw new ECommerceException("Quantity must be greater than zero");
            }

            if (product.getStock() < quantity) {
                throw new OutOfStockException("Insufficient stock. Available: " + product.getStock() + ", Requested: " + quantity);
            }

            double orderAmount = product.getPrice() * quantity;
            if (customer.getCredit() < orderAmount) {
                throw new InsufficientCreditException("Insufficient credit. Available: " + customer.getCredit() + ", Required: " + orderAmount);
            }

            productDao.updateStock(productNo, product.getStock() - quantity, conn);
            customerDao.updateCredit(customerId, customer.getCredit() - orderAmount, conn);

            Order order = new Order(0, LocalDate.now(), customerId, productNo, quantity);
            orderDao.save(order, conn);

            conn.commit();
            return order;
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                }
            }
            if (e instanceof ECommerceException) {
                throw (ECommerceException) e;
            }
            throw new ECommerceException("Failed to place order: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    @Override
    public List<OrderDetails> getCustomerOrderHistory(String customerId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            customerDao.findById(customerId, conn)
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + customerId));
            return orderDao.findDetailsByCustomerId(customerId, conn);
        } catch (SQLException e) {
            throw new ECommerceException("Failed to retrieve order history: " + e.getMessage());
        }
    }

    @Override
    public List<OrderDetails> getCustomerOrderHistoryAfter(String customerId, LocalDate afterDate) {
        List<OrderDetails> result = new ArrayList<>();
        getCustomerOrderHistory(customerId).stream()
                .filter(o -> o.getOrderDate().isAfter(afterDate))
                .forEach(result::add);
        return result;
    }

    @Override
    public List<OrderDetails> getCustomerOrderHistoryBefore(String customerId, LocalDate beforeDate) {
        List<OrderDetails> result = new ArrayList<>();
        getCustomerOrderHistory(customerId).stream()
                .filter(o -> o.getOrderDate().isBefore(beforeDate))
                .forEach(result::add);
        return result;
    }

    @Override
    public List<OrderDetails> getCustomerOrderHistoryBetween(String customerId, LocalDate fromDate, LocalDate toDate) {
        List<OrderDetails> result = new ArrayList<>();
        getCustomerOrderHistory(customerId).stream()
                .filter(o -> !o.getOrderDate().isBefore(fromDate) && !o.getOrderDate().isAfter(toDate))
                .forEach(result::add);
        return result;
    }

    @Override
    public Invoice generateCustomerInvoice(String customerId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Customer customer = customerDao.findById(customerId, conn)
                    .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + customerId));
            List<OrderDetails> orders = orderDao.findDetailsByCustomerId(customerId, conn);
            return new Invoice(customer, orders);
        } catch (SQLException e) {
            throw new ECommerceException("Failed to generate invoice: " + e.getMessage());
        }
    }
}
