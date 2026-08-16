<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Receipt</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f7f6; margin: 0; padding: 20px; display: flex; justify-content: center; align-items: center; height: 100vh; }
        .card { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 450px; }
        h2 { margin-top: 0; color: #28a745; text-align: center; }
        .receipt-header { border-bottom: 2px dashed #ddd; padding-bottom: 15px; margin-bottom: 20px; text-align: center; }
        .receipt-header p { margin: 5px 0; color: #666; font-size: 14px; }
        .receipt-row { display: flex; justify-content: space-between; margin-bottom: 12px; font-size: 15px; color: #555; }
        .receipt-row strong { color: #222; }
        .receipt-total { border-top: 2px dashed #ddd; padding-top: 15px; margin-top: 20px; font-size: 18px; color: #333; }
        .btn-portal { width: 100%; padding: 12px; background-color: #007bff; border: none; border-radius: 4px; color: white; font-size: 16px; cursor: pointer; font-weight: bold; margin-top: 25px; display: block; text-decoration: none; text-align: center; box-sizing: border-box; }
        .btn-portal:hover { background-color: #0056b3; }
    </style>
</head>
<body>
    <div class="card">
        <h2>Order Placed Successfully!</h2>
        
        <div class="receipt-header">
            <p>Thank you for your purchase.</p>
            <p>Order No: <strong>#${order.orderNo()}</strong></p>
            <p>Date: ${order.orderDate()}</p>
        </div>

        <div class="receipt-row">
            <span>Product Number:</span>
            <strong>${order.productNo()}</strong>
        </div>
        <div class="receipt-row">
            <span>Unit Price:</span>
            <strong>₹${String.format("%,.2f", product.price())}</strong>
        </div>
        <div class="receipt-row">
            <span>Quantity:</span>
            <strong>${quantity} units</strong>
        </div>
        <div class="receipt-total receipt-row">
            <span>Total Amount:</span>
            <strong style="color: #28a745;">₹${String.format("%,.2f", amount)}</strong>
        </div>

        <a href="${pageContext.request.contextPath}/webmvc/customer/portal" class="btn-portal">Return to Portal</a>
    </div>
</body>
</html>
