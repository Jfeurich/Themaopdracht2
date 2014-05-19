package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException { 
		req.getSession().setAttribute("gebruiker", null);
		req.setAttribute("msgs", "U bent succesvol uitgelogd!");
		RequestDispatcher rd = null; 
		rd = req.getRequestDispatcher("loginpage.jsp"); 
		rd.forward(req, resp); 
	}
}
