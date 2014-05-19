package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

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
	private User deGebruiker;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException { 
		
		ConnectDB database = new ConnectDB();
		Connection con = database.maakVerbinding();
		
		boolean loginSuccess = false; 
		String username = req.getParameter("username"); 
		String password = req.getParameter("password"); 
		if(username != null && password != null){
			//login check
			ConnectDBUser usercon = new ConnectDBUser(con);
			boolean b = false;
			for(User u: usercon.getUsers()){
				if(u.getGebruikersnaam().equals(username) && u.getWachtwoord().equals(password)){
					deGebruiker = u;
					b = true;
				}
			}			
			if (!b) { 
				req.setAttribute("msgs", "Onjuist wachtwoord!"); 
			} 
			else{
				ArrayList<String> ingelogdeGebruikers = (ArrayList<String>) getServletContext().getAttribute("loggedusers");
				getServletContext().setAttribute("loggedusers", ingelogdeGebruikers);
				req.setAttribute("msgs", "U bent succesvol ingelogd!");
				loginSuccess = true;
			}
		}
		else{
			req.setAttribute("msg","Niet alle velden zijn ingevuld!");	
		}
		RequestDispatcher rd = null; 
		if (loginSuccess) {
			rd = req.getRequestDispatcher("index.jsp");
			resp.addCookie(new Cookie("username", deGebruiker.getGebruikersnaam()));
			req.getSession().setMaxInactiveInterval(10);
			req.getSession().setAttribute("gebruiker", deGebruiker);
		}
		else{
			rd = req.getRequestDispatcher("loginpage.jsp");
		}
		rd.forward(req, resp); 
		database.sluitVerbinding(con);
	}
} 

