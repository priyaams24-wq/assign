<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Browse Products - Console Mode</title>
    <style>
        body { font-family: 'Courier New', Courier, monospace; background-color: #ffffff; color: #000000; padding: 30px; line-height: 1.5; }
        .console-box { border: 2px solid #000; padding: 20px; max-width: 800px; margin: 0 auto; }
        .btn-back { display: inline-block; margin-bottom: 20px; color: #0000ee; text-decoration: none; }
        .btn-back:hover { text-decoration: underline; }
        .filter-section { border: 1px dashed #000; padding: 15px; margin-bottom: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { border: 1px solid #000; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .btn-order { color: #0000ee; text-decoration: none; font-weight: bold; }
        .btn-order:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="console-box">
        <a href="${pageContext.request.contextPath}/webmvc/customer/portal/${customerId}" class="btn-back">&lt; Back to Portal</a>
        
        <h2>--- BROWSE PRODUCTS ---</h2>
        
        <div class="filter-section">
            <h4 style="margin: 0 0 10px 0;">[ FILTER PRODUCTS ]</h4>
            <form action="${pageContext.request.contextPath}/webmvc/products/list/${customerId}" method="get">
                
                Availability:
                <select name="filter" style="font-family: 'Courier New';">
                    <option value="all" ${filter == 'all' ? 'selected' : ''}>All Products</option>
                    <option value="available" ${filter == 'available' ? 'selected' : ''}>In Stock Only</option>
                </select>
                <br/><br/>
                
                Price Range: 
                Min ₹<input type="number" name="minPrice" step="0.01" value="${minPrice}" style="width: 80px; font-family: 'Courier New';" /> 
                Max ₹<input type="number" name="maxPrice" step="0.01" value="${maxPrice}" style="width: 80px; font-family: 'Courier New';" />
                <br/><br/>
                
                Minimum Stock: 
                <input type="number" name="minStock" value="${minStock}" style="width: 80px; font-family: 'Courier New';" />
                <br/><br/>
                
                <input type="submit" value="Apply Filter" style="font-family: 'Courier New'; padding: 4px 10px; cursor: pointer;" />
                <a href="${pageContext.request.contextPath}/webmvc/products/list/${customerId}" style="margin-left: 10px; color: #555;">[Reset]</a>
            </form>
        </div>

        <table>
            <thead>
                <tr>
                    <th>Product No</th>
                    <th>Price</th>
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
                                <c:when test="${product.stock() > 0}">IN STOCK</c:when>
                                <c:otherwise><span style="color:red;">OUT OF STOCK</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${product.stock() > 0}">
                                    <a href="${pageContext.request.contextPath}/webmvc/orders/checkout/${product.productNo()}/${customerId}" class="btn-order">[ORDER]</a>
                                </c:when>
                                <c:otherwise>
                                    <span style="color:#888;">[N/A]</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty products}">
                    <tr>
                        <td colspan="5" style="text-align: center; color: #888;">No products matching filters found.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>
