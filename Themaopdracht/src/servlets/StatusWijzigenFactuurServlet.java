package servlets;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBFactuur;
import domeinklassen.Factuur;

public class StatusWijzigenFactuurServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getAttribute("factuur") != null){
			Factuur deFactuur = (Factuur) req.getAttribute("factuur");
			if(deFactuur.getIsBetaald() == true){
				resp.sendRedirect("homepage.jsp");
			}
			req.setAttribute("factuurid", deFactuur.getID());
		}
		else{
			String knop = req.getParameter("knop");
			if(knop.equals("Done")){
				resp.sendRedirect("homepage.jsp");
			}
			else if(knop.equals("Betaal")){
				ConnectDBFactuur factuurcon = new ConnectDBFactuur();
				Factuur deFactuur = factuurcon.zoekFactuur(Integer.parseInt(req.getParameter("factuurid")));
				Calendar now = Calendar.getInstance();
				deFactuur.betaal(req.getParameter("betaalmiddel"), now);
				req.setAttribute("stap1", "done");
			}
			RequestDispatcher rd = req.getRequestDispatcher("statuswijzigenfactuur.jsp");
			rd.forward(req, resp);	
		}
	}
}

