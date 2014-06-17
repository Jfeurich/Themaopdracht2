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

import database.ConnectDBAuto;
import database.ConnectDBKlant;
import database.ConnectDBReservering;
import domeinklassen.Auto;
import domeinklassen.Klant;
import domeinklassen.User;

public class NieuweReserveringServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher("parkeerplaatsoverzicht.jsp");
		Connection con = (Connection)req.getSession().getAttribute("verbinding");
		String knop = req.getParameter("knop");
		
		if(knop.equals("klanten")){
			Object g = req.getSession().getAttribute("gebruiker");
			if(g != null){
				User deGebruiker = (User)g;
				if(deGebruiker.getType() == 3){
					req.setAttribute("msg", "Leuk geprobeerd. U kunt alleen reserveren voor uw eigen auto's.");
				}
				else{			
					ConnectDBKlant klantconn = new ConnectDBKlant(con);
					ArrayList<Klant> klanten = klantconn.getKlanten();
					req.setAttribute("klanten", klanten);
				}
			}
			
		}
		// haal auto's uit de database
		else if(knop.equals("autos")){
			Object g = req.getSession().getAttribute("gebruiker");
			if(g != null){
				User deGebruiker = (User)g;
				if(deGebruiker.getType() == 3){
					req.setAttribute("msg", "Leuk geprobeerd. U kunt alleen reserveren voor uw eigen auto's.");
				}
				else{	
					ConnectDBAuto autoconn = new ConnectDBAuto(con);
					String knr = req.getParameter("gekozenklant");
					int klantnummer = Integer.parseInt(knr);
					ArrayList<Auto> autos = autoconn.getAutosVan(klantnummer);
					req.setAttribute("autos", autos);
				}
			}
		}
		else if(knop.equals("kiesAuto")){
			ConnectDBAuto autoconn = new ConnectDBAuto(con);
			String autoid = req.getParameter("gekozenauto");
			Auto deAuto = autoconn.zoekAuto(Integer.parseInt(autoid));
			req.setAttribute("deAuto", deAuto);
		}
		else if(knop.equals("maakReservering")){
			String auto = req.getParameter("deAuto");
			Date bD = (Date) req.getSession().getAttribute("beginDat");
			Date eD = (Date) req.getSession().getAttribute("eindDat");
			int autoid = Integer.parseInt(auto);
			ConnectDBAuto autoconn = new ConnectDBAuto(con);
			Auto deAuto = autoconn.zoekAuto(autoid);
			boolean magMaken = false;
			//check voor geldige datum
			try{
				int parkeerplek = (int) req.getSession().getAttribute("parkeerplek");
				ConnectDBReservering rconn = new ConnectDBReservering(con);
				rconn.nieuweReservering(deAuto, parkeerplek, bD, eD);
				String terug = "Reservering met succes aangemaakt voor parkeerplaats: " + parkeerplek;
				req.getSession().setAttribute("parkeerplek", null);
				req.getSession().setAttribute("beginDat", null);
				req.getSession().setAttribute("eindDat", null);
				req.setAttribute("msg", terug);
				rd = req.getRequestDispatcher("index.jsp");
			}
			catch(Exception ex){
				System.out.println(ex);
				req.setAttribute("error", "Kon de reservering niet toevoegen!");
			}
			if(!magMaken){	//als de reservering niet aan is gemaakt, geef de auto terug voor een tweede poging
				req.setAttribute("deAuto", deAuto);
			}
		}
		rd.forward(req, resp);
	}
}
