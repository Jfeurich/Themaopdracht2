package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBKlant;
import database.ConnectDBKlus;
import domeinklassen.Klant;
import domeinklassen.Klus;

public class KlusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection con = (Connection)req.getSession().getAttribute("verbinding");
		String knop = req.getParameter("knop");
		//stuur antwoord bij default naar nieuweklus.jsp
		RequestDispatcher rd = req.getRequestDispatcher("klus.jsp");
		
		//geselecteerde klus annuleren
		if(knop.equals("nieuw")){
			ConnectDBKlant klantconn = new ConnectDBKlant(con);	
			ArrayList<Klant> klanten = klantconn.getKlanten();
			if(klanten.size() > 0){
				req.setAttribute("klanten", klanten);
				req.setAttribute("stap1", "done");
				rd = req.getRequestDispatcher("nieuweklus.jsp");
			}
			else{
				req.setAttribute("error", "Er staan nog geen klanten in het systeem!");
			} 
		}
		//klanten ophalen en doorsturen bij nieuwe klus of klus wijzigen
		else{
			String klus = req.getParameter("gekozenklus");
			if(klus != null){
				int klusid = Integer.parseInt(klus);
				ConnectDBKlus klusconn = new ConnectDBKlus(con);
				Klus deKlus = klusconn.zoekKlus(klusid);
				if(knop.equals("annuleren")){
					deKlus.setStatus("geannuleerd");
					klusconn.updateKlus(deKlus);
					req.setAttribute("msg", "Klus is succesvol geannuleerd");
				}
				else if(knop.equals("wijzig")){
					req.setAttribute("deKlus", deKlus);
					rd = req.getRequestDispatcher("kluswijzigen.jsp");
				}
			}
			else{
				req.setAttribute("error", "Er is geen klus geselecteerd!");
			}
		}
		rd.forward(req, resp);
	}
}
		