<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order History - Console Mode</title>
    <style>
        body { font-family: 'Courier New', Courier, monospace; background-color: #ffffff; color: #000000; padding: 30px; line-height: 1.5; }
        .console-box { border: 2px solid #000; padding: 20px; max-width: 800px; margin: 0 auto; }
        .btn-back { display: inline-block; margin-bottom: 20px; color: #0000ee; text-decoration: none; }
        .btn-back:hover { text-decoration: underline; }
        .filter-section { border: 1px dashed #000; padding: 15px; margin-bottom: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { border: 1px solid #000; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <div class="console-box">
        <a href="${pageContext.request.contextPath}/webmvc/customer/portal/${customerId}" class="btn-back">&lt; Back to Portal</a>
        
        <h2>--- ORDER HISTORY ---</h2>
        
        <div class="filter-section">
            <h4 style="margin: 0 0 10px 0;">[ FILTER ORDERS ]</h4>
            <form action="${pageContext.request.contextPath}/webmvc/orders/history/${customerId}" method="get">
                
                Date Range (YYYY-MM-DD):<br/>
                From: <input type="text" name="fromDate" value="${fromDate}" style="width: 100px; font-family: 'Courier New';" placeholder="2026-08-01" /> 
                To: <input type="text" name="toDate" value="${toDate}" style="width: 100px; font-family: 'Courier New';" placeholder="2026-08-31" />
                <br/><br/>
                
                Other Date Filters (YYYY-MM-DD):<br/>
                After:  <input type="text" name="afterDate" value="${afterDate}" style="width: 100px; font-family: 'Courier New';" placeholder="2026-08-01" /> 
                Before: <input type="text" name="beforeDate" value="${beforeDate}" style="width: 100px; font-family: 'Courier New';" placeholder="2026-08-31" />
                <br/><br/>
                
                <input type="submit" value="Search Orders" style="font-family: 'Courier New'; padding: 4px 10px; cursor: pointer;" />
                <a href="${pageContext.request.contextPath}/webmvc/orders/history/${customerId}" style="margin-left: 10px; color: #555;">[Reset]</a>
            </form>
        </div>

        <table>
            <thead>
                <tr>
                    <th>Order No</th>
                    <th>Order Date</th>
                    <th>Product No</th>
                    <th>Unit Price</th>
                    <th>Quantity</th>
                    <th>Total Amount</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${history}">
                    <tr>
                        <td>#${item.orderNo()}</td>
                        <td>${item.orderDate()}</td>
                        <td><strong>${item.product().productNo()}</strong></td>
                        <td>₹${String.format("%,.2f", item.price())}</td>
                        <td>${item.quantity()} units</td>
                        <td style="font-weight: bold;">₹${String.format("%,.2f", item.amount())}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty history}">
                    <tr>
                        <td colspan="6" style="text-align: center; color: #888;">No orders matching search criteria found.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>
