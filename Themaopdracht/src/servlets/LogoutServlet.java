package servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domeinklassen.User;

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException { 
		User u = (User)req.getSession().getAttribute("gebruiker");
		String naam = u.getGebruikersnaam();
		//logger
		Logger logger = Logger.getLogger("ATDlogger");
		logger.info("Gebruiker uitgelogd: " + naam);
		req.getSession().setAttribute("gebruiker", null);
		String terug = "Tot ziens, " + naam + "!";
		req.setAttribute("msg", terug);
		req.getRequestDispatcher("loginpage.jsp").forward(req, resp); 
	}
}
