package com.ecommerce.dao;

import com.ecommerce.model.Customer;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetails;
import com.ecommerce.model.Product;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcOrderDao implements OrderDao {
    @Override
    public void save(Order order, Connection conn) throws SQLException {
        String sql = "INSERT INTO orders (order_date, customer_id, product_no, quantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, Date.valueOf(order.getOrderDate()));
            stmt.setString(2, order.getCustomerId());
            stmt.setString(3, order.getProductNo());
            stmt.setInt(4, order.getQuantity());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setOrderNo(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public List<Order> findAll(Connection conn) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT order_no, order_date, customer_id, product_no, quantity FROM orders";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
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
        }
        return orders;
    }

    @Override
    public List<OrderDetails> findAllDetails(Connection conn) throws SQLException {
        List<OrderDetails> details = new ArrayList<>();
        String sql = "SELECT o.order_no, o.order_date, o.customer_id, c.password, c.email, c.credit, " +
                "o.product_no, p.price, p.stock, o.quantity FROM orders o " +
                "JOIN customers c ON o.customer_id = c.customer_id " +
                "JOIN products p ON o.product_no = p.product_no";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                details.add(mapRowToOrderDetails(rs));
            }
        }
        return details;
    }

    @Override
    public List<OrderDetails> findDetailsByCustomerId(String customerId, Connection conn) throws SQLException {
        List<OrderDetails> details = new ArrayList<>();
        String sql = "SELECT o.order_no, o.order_date, o.customer_id, c.password, c.email, c.credit, " +
                "o.product_no, p.price, p.stock, o.quantity FROM orders o " +
                "JOIN customers c ON o.customer_id = c.customer_id " +
                "JOIN products p ON o.product_no = p.product_no " +
                "WHERE o.customer_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    details.add(mapRowToOrderDetails(rs));
                }
            }
        }
        return details;
    }

    @Override
    public List<OrderDetails> findDetailsByDateRange(LocalDate fromDate, LocalDate toDate, Connection conn) throws SQLException {
        List<OrderDetails> details = new ArrayList<>();
        String sql = "SELECT o.order_no, o.order_date, o.customer_id, c.password, c.email, c.credit, " +
                "o.product_no, p.price, p.stock, o.quantity FROM orders o " +
                "JOIN customers c ON o.customer_id = c.customer_id " +
                "JOIN products p ON o.product_no = p.product_no " +
                "WHERE o.order_date BETWEEN ? AND ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(fromDate));
            stmt.setDate(2, Date.valueOf(toDate));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    details.add(mapRowToOrderDetails(rs));
                }
            }
        }
        return details;
    }

    @Override
    public List<OrderDetails> findDetailsByProductNo(String productNo, Connection conn) throws SQLException {
        List<OrderDetails> details = new ArrayList<>();
        String sql = "SELECT o.order_no, o.order_date, o.customer_id, c.password, c.email, c.credit, " +
                "o.product_no, p.price, p.stock, o.quantity FROM orders o " +
                "JOIN customers c ON o.customer_id = c.customer_id " +
                "JOIN products p ON o.product_no = p.product_no " +
                "WHERE o.product_no = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, productNo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    details.add(mapRowToOrderDetails(rs));
                }
            }
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
                product.getPrice(),
                rs.getInt("quantity")
        );
    }
}
