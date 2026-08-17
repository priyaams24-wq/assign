<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Portal - Console Mode</title>
    <style>
        body { font-family: 'Courier New', Courier, monospace; background-color: #ffffff; color: #000000; padding: 30px; line-height: 1.5; }
        .console-box { border: 2px solid #000; padding: 20px; max-width: 600px; margin: 0 auto; }
        .menu-link { display: block; margin: 10px 0; color: #0000ee; text-decoration: none; }
        .menu-link:hover { text-decoration: underline; }
        .alert { color: #ff0000; font-weight: bold; margin-bottom: 15px; }
    </style>
</head>
<body>
    <div class="console-box">
        <pre>
==================================================
                E-COMMERCE SYSTEM
==================================================
Logged In Customer Details:
--------------------------------------------------
Customer ID    : ${customer.customerId()}
Email          : ${customer.email()}
Credit Balance : ₹${String.format("%,.2f", customer.credit())}
==================================================
        </pre>

        <c:if test="${not empty param.error}">
            <div class="alert">ERROR: ${param.error}</div>
        </c:if>

        <h3>SELECT AN OPTION FROM MENU:</h3>
        <a href="${pageContext.request.contextPath}/webmvc/products/list/${customerId}" class="menu-link">[1] Browse & Order Products</a>
        <a href="${pageContext.request.contextPath}/webmvc/orders/history/${customerId}" class="menu-link">[2] View Order History</a>
        <a href="${pageContext.request.contextPath}/webmvc/orders/invoice/${customerId}" class="menu-link">[3] Generate Customer Invoice</a>
        <a href="${pageContext.request.contextPath}/webmvc/customer/logout" class="menu-link">[4] Logout / Exit</a>
    </div>
</body>
</html>
