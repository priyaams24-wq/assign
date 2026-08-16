<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Invoice</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f7f6; margin: 0; padding: 20px; }
        .container { max-width: 850px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); }
        .header { display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #eee; padding-bottom: 20px; margin-bottom: 20px; }
        h1 { margin: 0; color: #333; }
        .btn-back { padding: 8px 16px; background-color: #6c757d; color: white; text-decoration: none; border-radius: 4px; font-weight: bold; }
        .btn-back:hover { background-color: #5a6268; }
        .info-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-bottom: 30px; }
        .info-card { background: #f8f9fa; padding: 20px; border-radius: 6px; border: 1px solid #dee2e6; }
        .info-card h3 { margin: 0 0 15px 0; color: #007bff; border-bottom: 1px solid #dee2e6; padding-bottom: 8px; }
        .info-card p { margin: 8px 0; font-size: 15px; color: #555; }
        .info-card strong { color: #222; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #eee; }
        th { background-color: #007bff; color: white; }
        tr:hover { background-color: #f8f9fa; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Customer Invoice Summary</h1>
            <a href="${pageContext.request.contextPath}/webmvc/customer/portal?customerId=${customerId}" class="btn-back">Back to Portal</a>
        </div>

        <div class="info-grid">
            <div class="info-card">
                <h3>Customer Information</h3>
                <p><strong>Customer ID:</strong> ${invoice.customer().customerId()}</p>
                <p><strong>Email Address:</strong> ${invoice.customer().email()}</p>
                <p><strong>Available Credit:</strong> ₹${String.format("%,.2f", invoice.customer().credit())}</p>
            </div>
            
            <div class="info-card">
                <h3>Invoice Details</h3>
                <p><strong>Total Orders Placed:</strong> ${invoice.numberOfOrders()}</p>
                <p><strong>Total Amount Spent:</strong> <span style="color: #28a745; font-weight: bold;">₹${String.format("%,.2f", invoice.totalAmount())}</span></p>
                <p><strong>Highest Order:</strong> 
                    <c:choose>
                        <c:when test="${not empty invoice.highestOrder()}">
                            ₹${String.format("%,.2f", invoice.highestOrder().amount())} (Order #${invoice.highestOrder().orderNo()})
                        </c:when>
                        <c:otherwise>N/A</c:otherwise>
                    </c:choose>
                </p>
                <p><strong>Lowest Order:</strong> 
                    <c:choose>
                        <c:when test="${not empty invoice.lowestOrder()}">
                            ₹${String.format("%,.2f", invoice.lowestOrder().amount())} (Order #${invoice.lowestOrder().orderNo()})
                        </c:when>
                        <c:otherwise>N/A</c:otherwise>
                    </c:choose>
                </p>
            </div>
        </div>

        <h2>Itemized Orders</h2>
        <table>
            <thead>
                <tr>
                    <th>Order No</th>
                    <th>Order Date</th>
                    <th>Product No</th>
                    <th>Unit Price (₹)</th>
                    <th>Quantity</th>
                    <th>Total Amount (₹)</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${invoice.orders()}">
                    <tr>
                        <td>#${item.orderNo()}</td>
                        <td>${item.orderDate()}</td>
                        <td><strong>${item.product().productNo()}</strong></td>
                        <td>₹${String.format("%,.2f", item.price())}</td>
                        <td>${item.quantity()}</td>
                        <td style="color: #28a745; font-weight: bold;">₹${String.format("%,.2f", item.amount())}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty invoice.orders}">
                    <tr>
                        <td colspan="6" style="text-align: center; color: #888; padding: 20px;">No invoice items available.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>
