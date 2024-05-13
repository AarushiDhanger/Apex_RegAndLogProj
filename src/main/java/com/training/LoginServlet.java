package com.training;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		HttpSession session = request.getSession();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myWebsiteInfo", "root", "p!Ckn!ckc@s3");
			String firstName = "";
			String userName = request.getParameter("username");
			String password = request.getParameter("password");
			if (null != userName && null != password) {
				if (connection != null) {
					PreparedStatement statement = connection
							.prepareStatement("select * from users where username=? and password=?");
					statement.setString(1, userName);
					statement.setString(2, password);
					ResultSet resultSet = statement.executeQuery();
					if (!resultSet.next()) {
						session.setAttribute("error", "Invalid Login");
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("LoginPage.jsp");
						requestDispatcher.forward(request, response);
					}
					while (resultSet.next()) {
						firstName = resultSet.getString(1);
					}
					resultSet.close();
					statement.close();
				}
				
				System.out.println("FIRSTNAME: '" + firstName + "'");
				if (firstName == null) {
					session.setAttribute("error", "Invalid Login");
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("LoginPage.jsp");
					requestDispatcher.forward(request, response);
				} else {
					session.setAttribute("firstName", firstName);
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("loginSuccessPage.jsp");
					requestDispatcher.forward(request, response);
				}
			}
//			System.out.println("FIRSTNAME: '" + firstName + "'");
//			if (firstName == null) {
//				session.setAttribute("error", "Invalid Login");
//				RequestDispatcher requestDispatcher = request.getRequestDispatcher("LoginPage.jsp");
//				requestDispatcher.forward(request, response);
//			} else {
//				session.setAttribute("firstName", firstName);
//				RequestDispatcher requestDispatcher = request.getRequestDispatcher("loginSuccessPage.jsp");
//				requestDispatcher.forward(request, response);
//			}
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
