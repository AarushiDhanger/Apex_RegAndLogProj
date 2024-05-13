<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
</head>
<body>
<h1> <% 
String error = (String) session.getAttribute("error");
if(error != null){
	out.print(error);
	session.setAttribute("error",null);
} 
%>
</h1>
<form action = "loginServlet" method="post">
UserName: <input type="text" name="username"><br>
Password: <input type="text" name="password"><br>
<input type="submit">
</form>
</body>
</html>