<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Login</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f7f6; margin: 0; padding: 0; display: flex; justify-content: center; align-items: center; height: 100vh; }
        .card { background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 350px; }
        h2 { margin-top: 0; color: #333; text-align: center; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; color: #666; font-size: 14px; }
        input[type="email"], input[type="password"], input[type="text"] { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        .btn { width: 100%; padding: 10px; background-color: #007bff; border: none; border-radius: 4px; color: white; font-size: 16px; cursor: pointer; }
        .btn:hover { background-color: #0056b3; }
        .alert { padding: 10px; border-radius: 4px; margin-bottom: 15px; font-size: 14px; text-align: center; }
        .alert-danger { background-color: #f8d7da; color: #721c24; }
        .alert-success { background-color: #d4edda; color: #155724; }
        .link-text { text-align: center; margin-top: 15px; font-size: 14px; }
        .link-text a { color: #007bff; text-decoration: none; }
        .link-text a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="card">
        <h2>Customer Login</h2>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/webmvc/customer/login" method="post">
            <div class="form-group">
                <label>Email Address</label>
                <input type="email" name="email" required />
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" required />
            </div>
            <button type="submit" class="btn">Login</button>
        </form>

        <div class="link-text">
            Don't have an account? <a href="${pageContext.request.contextPath}/webmvc/customer/register">Register here</a>
        </div>
    </div>
</body>
</html>
