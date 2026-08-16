<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order History</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f7f6; margin: 0; padding: 20px; }
        .container { max-width: 900px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); }
        .header { display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #eee; padding-bottom: 20px; margin-bottom: 20px; }
        h1 { margin: 0; color: #333; }
        .btn-back { padding: 8px 16px; background-color: #6c757d; color: white; text-decoration: none; border-radius: 4px; font-weight: bold; }
        .btn-back:hover { background-color: #5a6268; }
        .filters { background: #f8f9fa; padding: 15px; border-radius: 6px; margin-bottom: 20px; border: 1px solid #dee2e6; }
        .filters form { display: flex; flex-wrap: wrap; gap: 15px; align-items: flex-end; }
        .filter-group { display: flex; flex-direction: column; }
        .filter-group label { font-size: 13px; color: #666; margin-bottom: 5px; }
        .filter-group input { padding: 6px 10px; border: 1px solid #ccc; border-radius: 4px; }
        .btn-filter { padding: 6px 12px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; font-weight: bold; }
        .btn-filter:hover { background-color: #0056b3; }
        .btn-reset { padding: 6px 12px; background-color: #e2e3e5; color: #383d41; text-decoration: none; border-radius: 4px; text-align: center; line-height: 20px; }
        .btn-reset:hover { background-color: #d6d8db; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #eee; }
        th { background-color: #007bff; color: white; }
        tr:hover { background-color: #f8f9fa; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Your Order History</h1>
            <a href="${pageContext.request.contextPath}/webmvc/customer/portal?customerId=${customerId}" class="btn-back">Back to Portal</a>
        </div>

        <div class="filters">
            <h4 style="margin: 0 0 10px 0;">Filter Order History</h4>
            <form action="${pageContext.request.contextPath}/webmvc/orders/history" method="get">
                <input type="hidden" name="customerId" value="${customerId}" />
                <div class="filter-group">
                    <label>From Date</label>
                    <input type="date" name="fromDate" value="${fromDate}" />
                </div>
                <div class="filter-group">
                    <label>To Date</label>
                    <input type="date" name="toDate" value="${toDate}" />
                </div>
                <div style="border-left: 1px solid #ccc; padding-left: 15px;" class="filter-group">
                    <label>Placed After</label>
                    <input type="date" name="afterDate" value="${afterDate}" />
                </div>
                <div class="filter-group">
                    <label>Placed Before</label>
                    <input type="date" name="beforeDate" value="${beforeDate}" />
                </div>
                <button type="submit" class="btn-filter">Apply Filters</button>
                <a href="${pageContext.request.contextPath}/webmvc/orders/history" class="btn-reset">Reset</a>
            </form>
        </div>

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
                <c:forEach var="item" items="${history}">
                    <tr>
                        <td>#${item.orderNo()}</td>
                        <td>${item.orderDate()}</td>
                        <td><strong>${item.product().productNo()}</strong></td>
                        <td>₹${String.format("%,.2f", item.price())}</td>
                        <td>${item.quantity()}</td>
                        <td style="color: #28a745; font-weight: bold;">₹${String.format("%,.2f", item.amount())}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty history}">
                    <tr>
                        <td colspan="6" style="text-align: center; color: #888; padding: 20px;">No order records found.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>
