<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Invoice - Console Mode</title>
    <style>
        body { font-family: 'Courier New', Courier, monospace; background-color: #ffffff; color: #000000; padding: 30px; line-height: 1.5; }
        .console-box { border: 2px solid #000; padding: 20px; max-width: 800px; margin: 0 auto; }
        .btn-back { display: inline-block; margin-bottom: 20px; color: #0000ee; text-decoration: none; }
        .btn-back:hover { text-decoration: underline; }
        .summary-box { border: 1px dashed #000; padding: 15px; margin-bottom: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { border: 1px solid #000; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <div class="console-box">
        <a href="${pageContext.request.contextPath}/webmvc/customer/portal?customerId=${customerId}" class="btn-back">&lt; Back to Portal</a>
        
        <h2>--- CUSTOMER INVOICE ---</h2>
        
        <div class="summary-box">
            <pre>
==============================================
               INVOICE SUMMARY
==============================================
Customer ID       : ${invoice.customer().customerId()}
Email Address     : ${invoice.customer().email()}
Available Credit  : ₹${String.format("%,.2f", invoice.customer().credit())}
----------------------------------------------
Total Orders      : ${invoice.numberOfOrders()}
Total Amount Spent: ₹${String.format("%,.2f", invoice.totalAmount())}
----------------------------------------------
Highest Order     : <c:choose><c:when test="${not empty invoice.highestOrder()}">₹${String.format("%,.2f", invoice.highestOrder().amount())} (Order #${invoice.highestOrder().orderNo()})</c:when><c:otherwise>N/A</c:otherwise></c:choose>
Lowest Order      : <c:choose><c:when test="${not empty invoice.lowestOrder()}">₹${String.format("%,.2f", invoice.lowestOrder().amount())} (Order #${invoice.lowestOrder().orderNo()})</c:when><c:otherwise>N/A</c:otherwise></c:choose>
==============================================
            </pre>
        </div>

        <h3>ITEMIZED ORDERS:</h3>
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
                <c:forEach var="item" items="${invoice.orders()}">
                    <tr>
                        <td>#${item.orderNo()}</td>
                        <td>${item.orderDate()}</td>
                        <td><strong>${item.product().productNo()}</strong></td>
                        <td>₹${String.format("%,.2f", item.price())}</td>
                        <td>${item.quantity()} units</td>
                        <td style="font-weight: bold;">₹${String.format("%,.2f", item.amount())}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty invoice.orders()}">
                    <tr>
                        <td colspan="6" style="text-align: center; color: #888;">No invoice items available.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>
