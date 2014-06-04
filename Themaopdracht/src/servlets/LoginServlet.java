package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBUser;
import domeinklassen.User;

public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException { 

		RequestDispatcher rd = req.getRequestDispatcher("loginpage.jsp");
		Connection con = (Connection)req.getSession().getAttribute("verbinding");
		
		String username = req.getParameter("username"); 
		String password = req.getParameter("password"); 
		if(username != null && password != null){
			//login check
			ConnectDBUser usercon = new ConnectDBUser(con);
			User deGebruiker = usercon.getUser(username);
			if(deGebruiker != null && deGebruiker.getWachtwoord().equals(password)){
				req.setAttribute("msg", "U bent succesvol ingelogd!");
				resp.addCookie(new Cookie("username", username));
				req.getSession().setAttribute("gebruiker", deGebruiker);
				//logger
				Logger logger = Logger.getLogger("ATDlogger");
				logger.info("Gebruiker ingelogd: " + username);
				rd = req.getRequestDispatcher("index.jsp");
			}
			else{ 
				req.setAttribute("error", "Onjuiste gebruikersnaam of wachtwoord!"); 
			} 
		}
		else{
			req.setAttribute("error","Niet alle velden zijn ingevuld!");	
		}
		rd.forward(req, resp); 
	}
} 

