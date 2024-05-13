<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration Page</title>
</head>
<body>
<h1> <% 

String error = (String) session.getAttribute("error");
if(error != null){
	out.print(error);
	session.setAttribute("error",null);
} 
%></h1>
<Form action="registerServlet" method="POST">
FirstName : <input name="firstName"><br>
LastName : <input name="lastName"><br>
UserName : <input name="username"><br>
Password : <input name="password"><br>
<input type="submit">
</Form>
</body>
</html>