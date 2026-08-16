<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirm Order Checkout</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f7f6; margin: 0; padding: 20px; display: flex; justify-content: center; align-items: center; height: 100vh; }
        .card { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 400px; }
        h2 { margin-top: 0; color: #333; text-align: center; }
        .product-summary { background: #f8f9fa; padding: 15px; border-radius: 6px; margin-bottom: 20px; border-left: 5px solid #28a745; }
        .product-summary p { margin: 5px 0; font-size: 15px; color: #555; }
        .product-summary strong { color: #222; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; color: #666; font-size: 14px; }
        input[type="number"] { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; font-size: 15px; }
        .btn-submit { width: 100%; padding: 12px; background-color: #28a745; border: none; border-radius: 4px; color: white; font-size: 16px; cursor: pointer; font-weight: bold; }
        .btn-submit:hover { background-color: #218838; }
        .btn-cancel { width: 100%; padding: 12px; background-color: #e2e3e5; border: 1px solid #ccc; border-radius: 4px; color: #383d41; font-size: 16px; cursor: pointer; font-weight: bold; margin-top: 10px; display: block; text-decoration: none; text-align: center; box-sizing: border-box; }
        .btn-cancel:hover { background-color: #d6d8db; }
        .alert { padding: 10px; border-radius: 4px; margin-bottom: 15px; font-size: 14px; text-align: center; background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
    </style>
</head>
<body>
    <div class="card">
        <h2>Checkout Confirmation</h2>

        <c:if test="${not empty error}">
            <div class="alert">${error}</div>
        </c:if>

        <div class="product-summary">
            <p><strong>Product Number:</strong> ${product.productNo()}</p>
            <p><strong>Unit Price:</strong> ₹${String.format("%,.2f", product.price())}</p>
            <p><strong>Available Stock:</strong> ${product.stock()} units</p>
        </div>

        <form action="${pageContext.request.contextPath}/webmvc/orders/place" method="post">
            <input type="hidden" name="productNo" value="${product.productNo()}" />
            <input type="hidden" name="customerId" value="${customerId}" />
            <div class="form-group">
                <label>Order Quantity</label>
                <input type="number" name="quantity" min="1" max="${product.stock()}" value="1" required />
            </div>
            <button type="submit" class="btn-submit">Place Order</button>
            <a href="${pageContext.request.contextPath}/webmvc/products/list?customerId=${customerId}" class="btn-cancel">Cancel</a>
        </form>
    </div>
</body>
</html>
