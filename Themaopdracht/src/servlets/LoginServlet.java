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

		RequestDispatcher rd = req.getRequestDispatcher("loginpage.jsp");
		
		String username = req.getParameter("username"); 
		String password = req.getParameter("password"); 
		if(username != null && password != null){
			//maak verbinding met database
			ConnectDB database = new ConnectDB();
			Connection con = database.maakVerbinding();
			req.getSession().setAttribute("verbinding", con);
			//login check
			ConnectDBUser usercon = new ConnectDBUser(con);
			username = username.toLowerCase();
			User u = usercon.getUser(username);
			if(u != null && u.getWachtwoord().equals(password)){
				rd = req.getRequestDispatcher("index.jsp");
				req.setAttribute("msg", "U bent succesvol ingelogd!");
				req.getSession().setAttribute("gebruiker", u);
				//check of gebruiker hier is gekomen via redirect. 
				//zo ja, probeer terug te sturen naar pagina waar redirect plaats vond
				Cookie verwijder = null;
				if(req.getCookies() != null){
					for(Cookie c : req.getCookies()){	
						if(c.getName().equals("vorigepagina")){
							verwijder = c;
							break;
						}
					}
					if(verwijder != null){
						rd = req.getRequestDispatcher(verwijder.getValue());
						verwijder.setMaxAge(-1);
					}
				}
				resp.addCookie(new Cookie("username", username));
				//logger
				Logger logger = Logger.getLogger("ATDlogger");
				logger.info("Gebruiker ingelogd: " + username);
			}
			else{ 
				req.setAttribute("error", "Onjuiste gebruikersnaam of wachtwoord!"); 
				//sluit verbinding met database als de gebruiker niet in mag loggen
				database.sluitVerbinding(con);
			} 
		}
		else{
			req.setAttribute("error","Niet alle velden zijn ingevuld!");	
		}
		rd.forward(req, resp); 
	}
} 

