<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Registration</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f7f6; margin: 0; padding: 0; display: flex; justify-content: center; align-items: center; height: 100vh; }
        .card { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 380px; }
        h2 { margin-top: 0; color: #333; text-align: center; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; color: #666; font-size: 14px; }
        input[type="email"], input[type="password"], input[type="text"], input[type="number"] { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        .btn { width: 100%; padding: 10px; background-color: #28a745; border: none; border-radius: 4px; color: white; font-size: 16px; cursor: pointer; }
        .btn:hover { background-color: #218838; }
        .alert { padding: 10px; border-radius: 4px; margin-bottom: 15px; font-size: 14px; text-align: center; }
        .alert-danger { background-color: #f8d7da; color: #721c24; }
        .link-text { text-align: center; margin-top: 15px; font-size: 14px; }
        .link-text a { color: #007bff; text-decoration: none; }
        .link-text a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="card">
        <h2>Customer Registration</h2>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/webmvc/customer/register" method="post">
            <div class="form-group">
                <label>Customer ID (alphanumeric)</label>
                <input type="text" name="customerId" placeholder="e.g. cust101" required />
            </div>
            <div class="form-group">
                <label>Email Address</label>
                <input type="email" name="email" placeholder="e.g. test@example.com" required />
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" required />
            </div>
            <div class="form-group">
                <label>Initial Credit Balance (₹)</label>
                <input type="number" name="credit" step="0.01" min="0" value="1000.00" required />
            </div>
            <button type="submit" class="btn">Register</button>
        </form>

        <div class="link-text">
            Already have an account? <a href="${pageContext.request.contextPath}/webmvc/customer/login">Login here</a>
        </div>
    </div>
</body>
</html>
