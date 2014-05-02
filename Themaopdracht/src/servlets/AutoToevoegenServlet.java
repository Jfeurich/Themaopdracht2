package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBKlant;
import domeinklassen.Klant;

public class AutoToevoegenServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String knop = req.getParameter("knop");
		if(knop.equals("Done")){
			resp.sendRedirect("homepage.jsp");
		}
		else{
			if(knop.equals("KiesKlant")){
				ConnectDBKlant klantcon = new ConnectDBKlant();	
				ArrayList<Klant> klanten = klantcon.getKlanten();
				req.setAttribute("klanten", klanten);
				req.setAttribute("stap1", "done");
			}
			if(knop.equals("VoegAutoToe")){
				ConnectDBKlant klantcon = new ConnectDBKlant();	
				ArrayList<Klant> klanten = klantcon.getKlanten();
				req.setAttribute("klanten", klanten);
				req.setAttribute("stap2", "done");
			}	
			RequestDispatcher rd = req.getRequestDispatcher("autotoevoegen.jsp");
			rd.forward(req, resp);	
		}
	}
}
