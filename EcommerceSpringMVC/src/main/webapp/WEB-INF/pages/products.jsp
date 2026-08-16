<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Available Products</title>
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
        .badge { padding: 4px 8px; border-radius: 4px; font-size: 12px; font-weight: bold; }
        .badge-success { background-color: #d4edda; color: #155724; }
        .badge-danger { background-color: #f8d7da; color: #721c24; }
        .btn-order { padding: 6px 12px; background-color: #28a745; color: white; text-decoration: none; border-radius: 4px; font-size: 14px; font-weight: bold; }
        .btn-order:hover { background-color: #218838; }
        .btn-disabled { padding: 6px 12px; background-color: #e2e3e5; color: #868e96; border-radius: 4px; font-size: 14px; cursor: not-allowed; text-decoration: none; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Browse Products</h1>
            <a href="${pageContext.request.contextPath}/webmvc/customer/portal" class="btn-back">Back to Portal</a>
        </div>

        <div class="filters">
            <h4 style="margin: 0 0 10px 0;">Filter Products</h4>
            <form action="${pageContext.request.contextPath}/webmvc/products/list" method="get">
                <div class="filter-group">
                    <label>Availability</label>
                    <select name="filter" style="padding: 6px; border: 1px solid #ccc; border-radius: 4px;">
                        <option value="all" ${filter != 'available' ? 'selected' : ''}>All Products</option>
                        <option value="available" ${filter == 'available' ? 'selected' : ''}>Available (In Stock Only)</option>
                    </select>
                </div>
                <div class="filter-group">
                    <label>Min Price (₹)</label>
                    <input type="number" name="minPrice" step="0.01" value="${minPrice}" />
                </div>
                <div class="filter-group">
                    <label>Max Price (₹)</label>
                    <input type="number" name="maxPrice" step="0.01" value="${maxPrice}" />
                </div>
                <div class="filter-group">
                    <label>Min Stock</label>
                    <input type="number" name="minStock" value="${minStock}" />
                </div>
                <button type="submit" class="btn-filter">Apply Filters</button>
                <a href="${pageContext.request.contextPath}/webmvc/products/list" class="btn-reset">Reset</a>
            </form>
        </div>

        <table>
            <thead>
                <tr>
                    <th>Product No</th>
                    <th>Price (₹)</th>
                    <th>Stock</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td><strong>${product.productNo()}</strong></td>
                        <td>₹${String.format("%,.2f", product.price())}</td>
                        <td>${product.stock()}</td>
                        <td>
                            <c:choose>
                                <c:when test="${product.stock() > 0}">
                                    <span class="badge badge-success">IN STOCK</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-danger">OUT OF STOCK</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${product.stock() > 0}">
                                    <a href="${pageContext.request.contextPath}/webmvc/orders/checkout?productNo=${product.productNo()}" class="btn-order">Order Now</a>
                                </c:when>
                                <c:otherwise>
                                    <span class="btn-disabled">Order Now</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty products}">
                    <tr>
                        <td colspan="5" style="text-align: center; color: #888; padding: 20px;">No products match the criteria.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>
