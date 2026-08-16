<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Receipt - Console Mode</title>
    <style>
        body { font-family: 'Courier New', Courier, monospace; background-color: #ffffff; color: #000000; padding: 30px; line-height: 1.5; }
        .console-box { border: 2px solid #000; padding: 20px; max-width: 600px; margin: 0 auto; }
        .btn-portal { display: inline-block; margin-top: 20px; color: #0000ee; text-decoration: none; font-weight: bold; }
        .btn-portal:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="console-box">
        <h2>--- ORDER SUCCESSFUL ---</h2>
        
        <pre>
==============================================
                ORDER RECEIPT
==============================================
Order No       : #${order.orderNo()}
Order Date     : ${order.orderDate()}
Customer ID    : ${order.customerId()}
----------------------------------------------
Product Number : ${order.productNo()}
Unit Price     : ₹${String.format("%,.2f", product.price())}
Quantity       : ${quantity} units
Total Amount   : ₹${String.format("%,.2f", amount)}
==============================================
        </pre>

        <a href="${pageContext.request.contextPath}/webmvc/customer/portal?customerId=${customerId}" class="btn-portal">[Return to Portal]</a>
    </div>
</body>
</html>
