package com.ecommerce.repository;

import com.ecommerce.model.Customer;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetails;
import com.ecommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {

    @Autowired
    private DataSource dataSource;

    public Order save(Order order) {
        String sql = "INSERT INTO orders (order_date, customer_id, product_no, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, Date.valueOf(order.orderDate()));
            stmt.setString(2, order.customerId());
            stmt.setString(3, order.productNo());
            stmt.setInt(4, order.quantity());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return new Order(generatedKeys.getInt(1), order.orderDate(), order.customerId(), order.productNo(), order.quantity());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save order", e);
        }
        return order;
    }

    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT order_no, order_date, customer_id, product_no, quantity FROM orders ORDER BY order_no ASC";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("order_no"),
                        rs.getDate("order_date").toLocalDate(),
                        rs.getString("customer_id"),
                        rs.getString("product_no"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find all orders", e);
        }
        return orders;
    }

    public List<OrderDetails> findAllDetails() {
        List<OrderDetails> details = new ArrayList<>();
        String sql = "SELECT o.order_no, o.order_date, o.customer_id, c.password, c.email, c.credit, " +
                "o.product_no, p.price, p.stock, o.quantity FROM orders o " +
                "JOIN customers c ON o.customer_id = c.customer_id " +
                "JOIN products p ON o.product_no = p.product_no " +
                "ORDER BY o.order_no ASC";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                details.add(mapRowToOrderDetails(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find all order details", e);
        }
        return details;
    }

    public List<OrderDetails> findDetailsByCustomerId(String customerId) {
        List<OrderDetails> details = new ArrayList<>();
        String sql = "SELECT o.order_no, o.order_date, o.customer_id, c.password, c.email, c.credit, " +
                "o.product_no, p.price, p.stock, o.quantity FROM orders o " +
                "JOIN customers c ON o.customer_id = c.customer_id " +
                "JOIN products p ON o.product_no = p.product_no " +
                "WHERE o.customer_id = ? " +
                "ORDER BY o.order_no ASC";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    details.add(mapRowToOrderDetails(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find order details by customer", e);
        }
        return details;
    }

    public List<OrderDetails> findDetailsByDateRange(LocalDate fromDate, LocalDate toDate) {
        List<OrderDetails> details = new ArrayList<>();
        String sql = "SELECT o.order_no, o.order_date, o.customer_id, c.password, c.email, c.credit, " +
                "o.product_no, p.price, p.stock, o.quantity FROM orders o " +
                "JOIN customers c ON o.customer_id = c.customer_id " +
                "JOIN products p ON o.product_no = p.product_no " +
                "WHERE o.order_date BETWEEN ? AND ? " +
                "ORDER BY o.order_no ASC";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(fromDate));
            stmt.setDate(2, Date.valueOf(toDate));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    details.add(mapRowToOrderDetails(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find order details by date range", e);
        }
        return details;
    }

    public List<OrderDetails> findDetailsByProductNo(String productNo) {
        List<OrderDetails> details = new ArrayList<>();
        String sql = "SELECT o.order_no, o.order_date, o.customer_id, c.password, c.email, c.credit, " +
                "o.product_no, p.price, p.stock, o.quantity FROM orders o " +
                "JOIN customers c ON o.customer_id = c.customer_id " +
                "JOIN products p ON o.product_no = p.product_no " +
                "WHERE o.product_no = ? " +
                "ORDER BY o.order_no ASC";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, productNo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    details.add(mapRowToOrderDetails(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find order details by productNo", e);
        }
        return details;
    }

    private OrderDetails mapRowToOrderDetails(ResultSet rs) throws SQLException {
        Customer customer = new Customer(
                rs.getString("customer_id"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getDouble("credit")
        );
        Product product = new Product(
                rs.getString("product_no"),
                rs.getDouble("price"),
                rs.getInt("stock")
        );
        return new OrderDetails(
                rs.getInt("order_no"),
                rs.getDate("order_date").toLocalDate(),
                customer,
                product,
                product.price(),
                rs.getInt("quantity")
        );
    }
}
