package servlets;
//GITHUB Y U NOT WORK

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
		String knop = req.getParameter("knop");
		if(knop.equals("Done")){
			resp.sendRedirect("homepage.jsp");
		}
		else if(knop.equals("klanten")){
			//haal klanten uit de database
			ConnectDBKlant klantconn = new ConnectDBKlant();
			ArrayList<Klant> klanten = klantconn.getKlanten();
			req.setAttribute("klanten", klanten);
		}
		else if(knop.equals("autos")){
			// haal auto's uit de database
			ConnectDBAuto autoconn = new ConnectDBAuto();
			String knr = req.getParameter("gekozenklant");
			int klantnummer = Integer.parseInt(knr);
			ArrayList<Auto> autos = autoconn.getAutosVan(klantnummer);
			req.setAttribute("autos", autos);
		}
		else if(knop.equals("klus")){
			//haal klussen uit de database
			ConnectDBKlus klusconn = new ConnectDBKlus();
			int autoid = Integer.parseInt(req.getParameter("gekozenauto"));
			ArrayList<Klus> klussen = klusconn.getKlussenVoorAuto(autoid);
			req.setAttribute("klussen", klussen);
		}
		else if(knop.equals("nieuw")){
			// hier wordt de klus geselecteerd om een factuur aan te maken.
			ConnectDBKlus klusconn = new ConnectDBKlus();
			int klusid = Integer.parseInt(req.getParameter("gekozenklus"));
			Klus deKlus = klusconn.zoekKlus(klusid);
			req.setAttribute("deKlus", deKlus);
		}
		else if(knop.equals("bevestig")){
			//kies een klus uit waarvan de status voltooid is.
			ConnectDBFactuur factuurconn = new ConnectDBFactuur();
			String dat = req.getParameter("datum");
			String beschrijving = req.getParameter("beschrijving");
			String status = req.getParameter("status");
			int klusid = Integer.parseInt(req.getParameter("gekozenklus"));

			boolean allesIngevuld = (dat!=null) && (beschrijving!=null)&& (status.equals("voltooid"));
			
			boolean gemaakt = false;

			if(allesIngevuld){
				//check voor geldige datum
				try{
					ConnectDBKlus klusconn = new ConnectDBKlus();
					
					SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					Date datum = df.parse(dat);
					Klus deKlus = klusconn.zoekKlus(klusid);
					Factuur nieuw = factuurconn.nieuweFactuur(deKlus);
					if(nieuw != null){
						String terug = "Factuur aangemaakt";
						req.setAttribute("msg", terug);
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
				ConnectDBKlus klusconn = new ConnectDBKlus();
				Klus deKlus = klusconn.zoekKlus(klusid);
				req.setAttribute("deKlus",deKlus);
			}

		}
		RequestDispatcher rd = req.getRequestDispatcher("nieuwefactuur.jsp");
		rd.forward(req, resp);
	}
}
