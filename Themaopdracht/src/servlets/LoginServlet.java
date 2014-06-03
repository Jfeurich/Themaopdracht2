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

import database.ConnectDB;
import database.ConnectDBUser;
import domeinklassen.User;

public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException { 

		RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
		if(req.getSession().getAttribute("gebruiker") == null){	//check dat er nog niemand in is gelogd
			ConnectDB database = new ConnectDB();
			Connection con = database.maakVerbinding();
			
			boolean loginSuccess = false; 
			String username = req.getParameter("username"); 
			String password = req.getParameter("password"); 
			if(username != null && password != null){
				//login check
				ConnectDBUser usercon = new ConnectDBUser(con);
				User deGebruiker = usercon.getUser(username);
				if(deGebruiker != null && deGebruiker.getWachtwoord().equals(password)){
					req.setAttribute("msg", "U bent succesvol ingelogd!");
					loginSuccess = true;
					resp.addCookie(new Cookie("username", username));
					req.getSession().setMaxInactiveInterval(120);
					req.getSession().setAttribute("gebruiker", deGebruiker);
					//logger
					Logger logger = Logger.getLogger("ATDlogger");
					logger.info("Gebruiker ingelogd: " + username);
				}
				else{ 
					req.setAttribute("error", "Onjuist wachtwoord!"); 
				} 
			}
			else{
				req.setAttribute("error","Niet alle velden zijn ingevuld!");	
			}
			if (!loginSuccess) {
				rd = req.getRequestDispatcher("loginpage.jsp");
			}
			database.sluitVerbinding(con);
		}
		else{
			req.setAttribute("msg","Leuk geprobeerd, maar u bent al ingelogd.");
		}
		rd.forward(req, resp); 
	}
} 

