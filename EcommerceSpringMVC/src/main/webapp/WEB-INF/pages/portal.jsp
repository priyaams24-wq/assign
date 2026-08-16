<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Portal</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f7f6; margin: 0; padding: 20px; }
        .container { max-width: 800px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); }
        .header { display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #eee; padding-bottom: 20px; margin-bottom: 20px; }
        h1 { margin: 0; color: #333; }
        .btn-logout { padding: 8px 16px; background-color: #dc3545; color: white; text-decoration: none; border-radius: 4px; font-weight: bold; }
        .btn-logout:hover { background-color: #bd2130; }
        .profile-info { background: #f8f9fa; padding: 20px; border-radius: 6px; margin-bottom: 30px; border-left: 5px solid #007bff; }
        .profile-info p { margin: 8px 0; font-size: 16px; color: #555; }
        .profile-info strong { color: #222; }
        .menu-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; }
        .menu-card { background: #e9ecef; padding: 20px; border-radius: 6px; text-align: center; text-decoration: none; color: #333; transition: all 0.2s ease-in-out; border: 1px solid #dee2e6; }
        .menu-card:hover { background: #007bff; color: white; transform: translateY(-3px); box-shadow: 0 4px 10px rgba(0,0,0,0.1); border-color: #007bff; }
        .menu-card h3 { margin: 0 0 10px 0; font-size: 18px; }
        .menu-card p { margin: 0; font-size: 14px; opacity: 0.8; }
        .alert { padding: 12px; border-radius: 4px; margin-bottom: 20px; background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Customer Portal</h1>
            <a href="${pageContext.request.contextPath}/webmvc/customer/logout" class="btn-logout">Logout</a>
        </div>

        <c:if test="${not empty error}">
            <div class="alert">${error}</div>
        </c:if>

        <div class="profile-info">
            <p><strong>Customer ID:</strong> ${customer.customerId()}</p>
            <p><strong>Email Address:</strong> ${customer.email()}</p>
            <p><strong>Credit Balance:</strong> <span style="color: #28a745; font-weight: bold; font-size: 18px;">₹${String.format("%,.2f", customer.credit())}</span></p>
        </div>

        <h2>Select an Action</h2>
        <div class="menu-grid">
            <a href="${pageContext.request.contextPath}/webmvc/products/list?customerId=${customerId}" class="menu-card">
                <h3>Browse Products</h3>
                <p>View all items and place new orders</p>
            </a>
            <a href="${pageContext.request.contextPath}/webmvc/orders/history?customerId=${customerId}" class="menu-card">
                <h3>Order History</h3>
                <p>Filter and search past purchase records</p>
            </a>
            <a href="${pageContext.request.contextPath}/webmvc/orders/invoice?customerId=${customerId}" class="menu-card">
                <h3>Generate Invoice</h3>
                <p>Download summary and key metrics of orders</p>
            </a>
        </div>
    </div>
</body>
</html>
