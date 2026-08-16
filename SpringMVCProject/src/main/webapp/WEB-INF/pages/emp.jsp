<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>Register - Payroll Application</title>
	</head>
	<body>
		<h1>Register Employee</h1>
		<sf:form modelAttribute="employee">
			Id : <sf:input path="id"/> <br>
			Name : <sf:input path="name"/> <br>
			Designation : <sf:input path="designation"/> <br>
			Email Id : <sf:input path="emailId"/> <br>
			<input type="submit" value="Save">
		</sf:form>
		
		<form method="post">
			Id : <input type="text" name="id" /> <br>
			Name : <input type="text" name="name" /> <br>
			Designation : <input type="text" name="designation" /> <br>
			Email Id : <input type="text" name="emailId" /> <br>
			<input type="submit" name="submit" value="Save">
		</form>
		<c:if test="">
		</c:if>
	</body>
</html>