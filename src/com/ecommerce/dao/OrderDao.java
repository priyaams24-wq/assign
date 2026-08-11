package com.ecommerce.dao;

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetails;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface OrderDao {
    void save(Order order, Connection conn) throws SQLException;
    List<Order> findAll(Connection conn) throws SQLException;
    List<OrderDetails> findAllDetails(Connection conn) throws SQLException;
    List<OrderDetails> findDetailsByCustomerId(String customerId, Connection conn) throws SQLException;
    List<OrderDetails> findDetailsByDateRange(LocalDate fromDate, LocalDate toDate, Connection conn) throws SQLException;
    List<OrderDetails> findDetailsByProductNo(String productNo, Connection conn) throws SQLException;
}
