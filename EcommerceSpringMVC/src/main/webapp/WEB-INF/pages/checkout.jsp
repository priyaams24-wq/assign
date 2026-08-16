<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Place Order - Console Mode</title>
    <style>
        body { font-family: 'Courier New', Courier, monospace; background-color: #ffffff; color: #000000; padding: 30px; line-height: 1.5; }
        .console-box { border: 2px solid #000; padding: 20px; max-width: 600px; margin: 0 auto; }
        .form-group { margin-bottom: 15px; }
        .alert { color: #ff0000; font-weight: bold; margin-bottom: 15px; }
        .btn-submit { font-family: 'Courier New'; padding: 6px 15px; cursor: pointer; background-color: #000; color: #fff; border: 1px solid #000; font-weight: bold; }
        .btn-submit:hover { background-color: #fff; color: #000; }
        .btn-cancel { margin-left: 15px; color: #555; text-decoration: none; }
        .btn-cancel:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="console-box">
        <h2>--- PLACE NEW ORDER ---</h2>

        <c:if test="${not empty error}">
            <div class="alert">ERROR: ${error}</div>
        </c:if>

        <pre>
Product Details:
----------------------------------
Product Number  : ${product.productNo()}
Unit Price      : ₹${String.format("%,.2f", product.price())}
Available Stock : ${product.stock()} units
----------------------------------
        </pre>

        <form action="${pageContext.request.contextPath}/webmvc/orders/place" method="post">
            <input type="hidden" name="productNo" value="${product.productNo()}" />
            <input type="hidden" name="customerId" value="${customerId}" />
            
            <div class="form-group">
                <label>Enter Order Quantity: </label>
                <input type="number" name="quantity" min="1" max="${product.stock()}" value="1" style="font-family: 'Courier New'; width: 80px;" required />
            </div>
            
            <br/>
            <button type="submit" class="btn-submit">Submit Order</button>
            <a href="${pageContext.request.contextPath}/webmvc/products/list?customerId=${customerId}" class="btn-cancel">[Cancel / Go Back]</a>
        </form>
    </div>
</body>
</html>
