package com.ecommerce.service;

import com.ecommerce.model.Invoice;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetails;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    Order placeOrder(String customerId, String productNo, int quantity);
    List<OrderDetails> getCustomerOrderHistory(String customerId);
    List<OrderDetails> getCustomerOrderHistoryAfter(String customerId, LocalDate afterDate);
    List<OrderDetails> getCustomerOrderHistoryBefore(String customerId, LocalDate beforeDate);
    List<OrderDetails> getCustomerOrderHistoryBetween(String customerId, LocalDate fromDate, LocalDate toDate);
    Invoice generateCustomerInvoice(String customerId);
}
