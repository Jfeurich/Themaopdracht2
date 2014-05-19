package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDB;
import database.ConnectDBKlant;
import database.ConnectDBKlus;
import domeinklassen.Klant;
import domeinklassen.Klus;

public class KlusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ConnectDB database = new ConnectDB();
		Connection con = database.maakVerbinding();
		String knop = req.getParameter("knop");
		//stuur antwoord bij default terug naar klus.jsp
		RequestDispatcher rd = req.getRequestDispatcher("klus.jsp");
		
		if(knop.equals("nieuw")){
			ConnectDBKlant klantconn = new ConnectDBKlant(con);	
			ArrayList<Klant> klanten = klantconn.getKlanten();
			if(klanten.size() > 0){
				req.setAttribute("klanten", klanten);
			}
			else{
				req.setAttribute("error", "Er staan nog geen klanten in het systeem!");
			}
			rd = req.getRequestDispatcher("nieuweklus.jsp");
		}
		else if(knop.equals("wijzig")){
			String klus = req.getParameter("gekozenklus");
			if(klus != null){
				int klusid = Integer.parseInt(klus);
				ConnectDBKlus klusconn = new ConnectDBKlus(con);
				Klus deKlus = klusconn.zoekKlus(klusid);
				req.setAttribute("deKlus", deKlus);
			}
			else{
				req.setAttribute("error", "Er is geen klus geselecteerd!");
			}
			rd = req.getRequestDispatcher("kluswijzigen.jsp");
		}
		else if(knop.equals("overzicht")){
			ConnectDBKlus kcon = new ConnectDBKlus(con);
			ArrayList<Klus> klussen = kcon.getKlussen();
			req.setAttribute("klussen", klussen);
		}
		database.sluitVerbinding(con);
		rd.forward(req, resp);
	}
}
		