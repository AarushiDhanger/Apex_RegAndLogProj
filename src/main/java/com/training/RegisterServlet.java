package com.training;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private Connection connection = null;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
//			ServletContext servletContext = config.getServletContext();
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/myWebsiteInfo", "root", "p!Ckn!ckc@s3");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("REGISTER SERVLET INIT METHOD COMPLETED");
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("REGISTER SERVLET DESTORY METHOD COMPLETED");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("REGISTER SERVLET DOPOST METHOD WORKING...");

		//get data from registerPage.jsp
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		//create session
		HttpSession session = request.getSession();
		PrintWriter writer = response.getWriter();


		try {
			//check if data is valid, if not, send back to register page with error message
			if (connection != null) {
				if (null != username ) {
					PreparedStatement statement = connection
							.prepareStatement("select * from users where username=?");
					statement.setString(1, username);
					ResultSet resultSet = statement.executeQuery();
					while (resultSet.next()) {
						session.setAttribute("error", "Username taken, please try again.");
						RequestDispatcher requestDispatcher = request.getRequestDispatcher("registerPage.jsp");
						requestDispatcher.forward(request, response);
					}
				}
			}
			//add user to database
			PreparedStatement statement = connection.prepareStatement("insert into users values(?,?,?,?)");
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, username);
			statement.setString(4, password);
			System.out.println("Statement: " + statement);
			int executeUpdate = statement.executeUpdate();
			response.setContentType("text/html");
			if (executeUpdate == 1) {
				writer.append("User Added Successfully!<br>");
				writer.append("Headed to Login Page. Please Wait...");
				System.out.println("CONSOLE LOG: User Added Successfully!");
				// Introduce a delay before redirecting to the login page
			    response.setHeader("Refresh", "3;URL=loginPage.jsp"); // Redirect after 3 seconds
//				RequestDispatcher requestDispatcher = request.getRequestDispatcher("loginPage.jsp");
//				requestDispatcher.forward(request, response);
			} else {
				writer.append("Internal Error Adding User.");
				System.out.println("CONSOLE LOG: Error Adding User.");

			}
			writer.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
