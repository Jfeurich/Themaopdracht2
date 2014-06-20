package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.ConnectDB;
import domeinklassen.User;

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException { 
		User u = (User)req.getSession().getAttribute("gebruiker");
		String naam = u.getGebruikersnaam();
		//logger
		HttpSession session = req.getSession();
		Logger logger = Logger.getLogger("ATDlogger");
		logger.info("Gebruiker uitgelogd: " + naam);
		//Sluit session af
		session.setAttribute("gebruiker", null);
		ConnectDB database = new ConnectDB();
		Connection con = (Connection)session.getAttribute("verbinding");
		database.sluitVerbinding(con);
		session.invalidate();
		//Stuur tot ziens bericht terug en stuur gebruiker naar login pagina
		String terug = "Tot ziens, " + naam + "!";
		req.setAttribute("msg", terug);
		req.getRequestDispatcher("loginpage.jsp").forward(req, resp); 
	}
}
