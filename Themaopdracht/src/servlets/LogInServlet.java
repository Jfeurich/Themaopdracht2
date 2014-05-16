package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domeinklassen.User;


public class LogInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<User> deGebruikers;
	private User deGebruiker;
	
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		String url = config.getInitParameter("connectDB");
		System.out.println(url);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException { 
		boolean loginSuccess = false; 
		String username = req.getParameter("username"); 
		String password = req.getParameter("password"); 
		if(username != null && password != null){
			//login check
			deGebruikers = (ArrayList<User>) getServletContext().getAttribute("users");
			if(deGebruikers == null){
				deGebruikers = new ArrayList<User>();
			}
			boolean b = false;
			for(User u: deGebruikers){
				if(u.getGebruikersnaam().equals(username) && u.getWachtwoord().equals(password)){
					deGebruiker = u;
					b = true;
				}
			}			
			if (!b) { 
				req.setAttribute("msgs", "Username and/or password are incorrect!"); 
			} 
			else{
				ArrayList<String> ingelogdeGebruikers = (ArrayList<String>) getServletContext().getAttribute("loggedusers");
				b = true;
				if(ingelogdeGebruikers == null){
					ingelogdeGebruikers = new ArrayList<String>();
				}
				for(String s: ingelogdeGebruikers){
					if(s.equals(deGebruiker.getGebruikersnaam())){
						b = false;
					}
				}
				if(b){
					ingelogdeGebruikers.remove("username");
					getServletContext().setAttribute("loggedusers", ingelogdeGebruikers);
					req.setAttribute("msgs", "U bent succesvol ingelogd!");
					loginSuccess = true;
				}
				else{
					req.setAttribute("msgs", "U bent reeds ingelogd!");
				}
			}
		}
		else{
			req.setAttribute("msg","Niet alle velden zijn ingevuld!");	
		}
		RequestDispatcher rd = null; 
		if (loginSuccess) {
			rd = req.getRequestDispatcher("homepage.jsp");
			resp.addCookie(new Cookie("username", deGebruiker.getGebruikersnaam()));
			req.getSession().setMaxInactiveInterval(10);
			req.getSession().setAttribute("gebruiker", deGebruiker);
		}
		else{
			rd = req.getRequestDispatcher("login.jsp");
		}
		rd.forward(req, resp); 
	}
} 

