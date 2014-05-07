package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Connection;
import database.ConnectDB;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBAuto;
import database.ConnectDBKlant;
import database.ConnectDBKlus;
import database.ConnectDBFactuur;
import domeinklassen.Auto;
import domeinklassen.Klant;
import domeinklassen.Klus;
import domeinklassen.Onderhoudsbeurt;
import domeinklassen.Reparatie;
import domeinklassen.Factuur;

public class NieuweFactuurServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ConnectDB database = new ConnectDB();
		Connection con = database.maakVerbinding();
		
		String knop = req.getParameter("knop");
		if(knop.equals("Done")){
			resp.sendRedirect("homepage.jsp");
		}
		else if(knop.equals("klanten")){
			//haal klanten uit de database
			ConnectDBKlant klantconn = new ConnectDBKlant(con);
			ArrayList<Klant> klanten = klantconn.getKlanten();
			req.setAttribute("klanten", klanten);
		}
		else if(knop.equals("autos")){
			// haal auto's uit de database
			ConnectDBAuto autoconn = new ConnectDBAuto(con);
			String knr = req.getParameter("gekozenklant");
			int klantnummer = Integer.parseInt(knr);
			ArrayList<Auto> autos = autoconn.getAutosVan(klantnummer);
			req.setAttribute("autos", autos);
		}
		else if(knop.equals("klus")){
			//haal klussen uit de database
			ConnectDBKlus klusconn = new ConnectDBKlus(con);
			int autoid = Integer.parseInt(req.getParameter("gekozenauto"));
			ArrayList<Klus> klussen = klusconn.getKlussenVoorAuto(autoid);
			req.setAttribute("klussen", klussen);
		}
		else if(knop.equals("nieuw")){
			//kies een klus uit waarvan de status voltooid is.
			ConnectDBFactuur factuurconn = new ConnectDBFactuur(con);
			String dat = req.getParameter("datum");
			String beschrijving = req.getParameter("beschrijving");
			String status = req.getParameter("status");
			int klusid = Integer.parseInt(req.getParameter("gekozenklus"));

			boolean allesIngevuld = (dat!=null) && (beschrijving!=null)&& (status.equals("voltooid"));
			
			boolean gemaakt = false;

			if(allesIngevuld){
				//check voor geldige datum
				try{
					ConnectDBKlus klusconn = new ConnectDBKlus(con);
					
					SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					Date datum = df.parse(dat);
					Klus deKlus = klusconn.zoekKlus(klusid);
					Factuur nieuw = factuurconn.nieuweFactuur(deKlus);
					if(nieuw != null){
						String terug = "Factuur aangemaakt";
						req.setAttribute("deFactuur", nieuw);
						gemaakt = true;
					}
					else{
						req.setAttribute("msg", "Factuur kan niet aangemaakt worden");
					}
				}
				catch(Exception ex){
					System.out.println(ex);
					req.setAttribute("error", "Geen geldige datum! Gebruik format dd-mm-jjjj");
				}
			}
			else{
				req.setAttribute("error", "/*foutmelding*/");
			}
			if(!gemaakt){
				ConnectDBKlus klusconn = new ConnectDBKlus(con);
				Klus deKlus = klusconn.zoekKlus(klusid);
				req.setAttribute("deKlus",deKlus);
			}
		}
		else if(knop.equals("bevestig")){
			// hier wordt de klus geselecteerd om een factuur aan te maken.
			ConnectDBKlus klusconn = new ConnectDBKlus(con);
			int klusid = Integer.parseInt(req.getParameter("gekozenklus"));
			Klus deKlus = klusconn.zoekKlus(klusid);
			req.setAttribute("deKlus", deKlus);
		}
		else if(knop.equals("setkorting")){
			String factuurid = req.getParameter("factuurvoorkorting");
			String korting = req.getParameter("korting");
			try{
				int dekorting = Integer.parseInt(korting);
				ConnectDBFactuur factuurcon = new ConnectDBFactuur(con);
				Factuur deFactuur = factuurcon.zoekFactuur(Integer.parseInt(factuurid));
				deFactuur.setKortingsPercentage(dekorting);
				factuurcon.updateFactuur(deFactuur);
				req.setAttribute("msg", "Database is geupdate");
			}
			catch(Exception e){
				req.setAttribute("error", "Geen geldige waarde");
			}
		}
		RequestDispatcher rd = req.getRequestDispatcher("nieuwefactuur.jsp");
		rd.forward(req, resp);
		database.sluitVerbinding(con);
	}
}
