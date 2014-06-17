package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBFactuur;
import database.ConnectDBKlant;
import domeinklassen.Factuur;
import domeinklassen.Klant;

public class FactuurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String knop = req.getParameter("knop");
		Connection con = (Connection)req.getSession().getAttribute("verbinding");
		RequestDispatcher rd = req.getRequestDispatcher("onbetaaldefacturenoverzicht.jsp");
		
		//overzicht van niet-betaalde facturen
		if(knop.equals("overzicht")){
			ConnectDBFactuur conn = new ConnectDBFactuur(con);	
			ArrayList<Factuur> terug = conn.getFacturenNietBetaald();
			req.setAttribute("OverzichtFacturenNietBetaald", terug);
		}
		//betaal geselecteerde factuur
		else if(knop.equals("betaal")){
			ConnectDBFactuur factuurcon = new ConnectDBFactuur(con);
			Factuur deFactuur = factuurcon.zoekFactuur(Integer.parseInt(req.getParameter("factuurid")));
			Date datum = new Date();
			deFactuur.betaal(req.getParameter("betaalmiddel"), datum);
			factuurcon.updateFactuur(deFactuur);
			ArrayList<Factuur> terug = factuurcon.getFacturenNietBetaald();
			req.setAttribute("OverzichtFacturenNietBetaald", terug);
		}
		//nieuwe factuur aanmaken
		else if(knop.equals("Kies")){
			ConnectDBKlant klantconn = new ConnectDBKlant(con);
			ArrayList<Klant> klanten = klantconn.getKlanten();
			req.setAttribute("klanten", klanten);
			rd = req.getRequestDispatcher("nieuwefactuur.jsp");
		}
		else if(knop.equals("zoek")){
			String gekozenfactuur = req.getParameter("factuurid");
			req.setAttribute("factuurid", gekozenfactuur);
		}
		rd.forward(req, resp);
	}
}
