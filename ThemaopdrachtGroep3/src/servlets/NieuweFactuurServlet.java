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
import database.ConnectDBAuto;
import database.ConnectDBFactuur;
import database.ConnectDBKlant;
import database.ConnectDBKlus;
import domeinklassen.Auto;
import domeinklassen.Factuur;
import domeinklassen.Klant;
import domeinklassen.Klus;

public class NieuweFactuurServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ConnectDB database = new ConnectDB();
		Connection con = database.maakVerbinding();
		String knop = req.getParameter("knop");
		
		if(knop.equals("Done")){
			resp.sendRedirect("index.html");
		}
		//haal klanten uit de database
		else if(knop.equals("klanten")){
			ConnectDBKlant klantconn = new ConnectDBKlant(con);
			ArrayList<Klant> klanten = klantconn.getKlanten();
			req.setAttribute("klanten", klanten);
		}
		// haal auto's uit de database
		else if(knop.equals("autos")){
			ConnectDBAuto autoconn = new ConnectDBAuto(con);
			String knr = req.getParameter("gekozenklant");
			int klantnummer = Integer.parseInt(knr);
			ArrayList<Auto> autos = autoconn.getAutosVan(klantnummer);
			req.setAttribute("autos", autos);
		}
		//haal klussen uit de database
		else if(knop.equals("klus")){
			ConnectDBKlus klusconn = new ConnectDBKlus(con);
			int autoid = Integer.parseInt(req.getParameter("gekozenauto"));
			ArrayList<Klus> klussen = klusconn.getKlussenVoorAuto(autoid);
			req.setAttribute("klussen", klussen);
		}
		//gebruiker heeft een klus geselecteerd en wil een factuur maken
		else if(knop.equals("nieuw")){
			String status = req.getParameter("status");
			String klus = req.getParameter("gekozenklus");
			boolean gemaakt = false;
			if(req.getParameter("klus") != null){
				int klusid = Integer.parseInt(klus);
				if(status.equals("voltooid")){	//is de status van de klus wel "voltooid?"
					//check voor geldige datum
					try{
						ConnectDBFactuur fconn = new ConnectDBFactuur(con);
						if(fconn.getFactuurVanKlus(klusid) == null){	//check of deze klus al een factuur heeft
							ConnectDBKlus klusconn = new ConnectDBKlus(con);
							Klus deKlus = klusconn.zoekKlus(klusid);
							Factuur nieuw = fconn.nieuweFactuur(deKlus);
							if(nieuw != null){		//kijk of de factuur met succes in de database is gezet
								req.setAttribute("msg", "Factuur aangemaakt");
								req.setAttribute("deFactuur", nieuw);
								gemaakt = true;
							}
							else{
								req.setAttribute("msg", "Factuur kan niet aangemaakt worden");
							}
						}
						else{
							req.setAttribute("error", "De factuur van deze klus bestaat al!");
						}
					}
					catch(Exception ex){
						System.out.println(ex);
						req.setAttribute("error", "Geen geldige datum! Gebruik format dd-mm-jjjj");
					}
				}
				else{
					req.setAttribute("error", "Deze klus is nog niet voltooid!");
				}
				if(!gemaakt){	//als de factuur niet aan is gemaakt, geef de klus terug voor een tweede poging
					ConnectDBKlus klusconn = new ConnectDBKlus(con);
					Klus deKlus = klusconn.zoekKlus(klusid);
					req.setAttribute("deKlus",deKlus);
				}
			}
			else{
				req.setAttribute("error", "Er zijn geen klussen beschikbaar!");
			}
		}
		//stel het kortingspercentage in
		else if(knop.equals("bevestig")){
			String factuurid = req.getParameter("factuurvoorkorting");
			String korting = req.getParameter("korting");
			try{	//check of het een geldig nummer is
				int dekorting = Integer.parseInt(korting);
				ConnectDBFactuur factuurcon = new ConnectDBFactuur(con);
				Factuur deFactuur = factuurcon.zoekFactuur(Integer.parseInt(factuurid));
				deFactuur.setKortingsPercentage(dekorting);
				factuurcon.updateFactuur(deFactuur);
				req.setAttribute("msg", "De korting is ingesteld");
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
