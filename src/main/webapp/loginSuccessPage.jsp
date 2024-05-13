<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Success Page</title>
</head>
<body>
<%
if(session.getAttribute("firstName") == null){
	RequestDispatcher requestDispatcher = request.getRequestDispatcher("loginPage.jsp");
	requestDispatcher.forward(request, response);
} 
%>
<h1> Welcome User <%= session.getAttribute("firstName") %>! </h1>
</body>
</html>