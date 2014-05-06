package servlets;

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
			ConnectDBKlant klantconn = new ConnectDBKlant();
			ArrayList<Klant> klanten = klantconn.getKlanten();
			req.setAttribute("klanten", klanten);
		}
		else if(knop.equals("autos")){
			ConnectDBAuto autoconn = new ConnectDBAuto();
			String knr = req.getParameter("gekozenklant");
			int klantnummer = Integer.parseInt(knr);
			ArrayList<Auto> autos = autoconn.getAutosVan(klantnummer);
			req.setAttribute("autos", autos);
		}
		else if(knop.equals("klus")){
			ConnectDBKlus klusconn = new ConnectDBKlus();
			int autoid = Integer.parseInt(req.getParameter("gekozenauto"));
			ArrayList<Klus> klussen = klusconn.getKlussenVoorAuto(autoid);
			req.setAttribute("klussen", klussen);
		}
		/*else if(knop.equals("maakfactuur")){
			// hier wordt de klus geselecteerd om een factuur aan te maken.
			ConnectDBKlus klusconn = new ConnectDBKlus();
			req.setAttribute("factuur",factuur );
			Factuur nieuweFactuur = 
		}*/
		else if(knop.equals("nieuw")){
			ConnectDBFactuur factuurconn = new ConnectDBFactuur();
			int kortingspercentage = Integer.parseInt(req.getParameter("kortingspercentage"));
			int autoid = Integer.parseInt(auto);
			
			boolean allesIngevuld = (type!=null) && (dat!=null) && (beschrijving!=null);
			boolean gemaakt = false;
			
			if(allesIngevuld){
				//check voor geldige datum
				try{
					SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					Date datum = df.parse(dat);
					Klus nieuw = klusconn.nieuweKlus(datum, beschrijving, type, autoid);
					if(nieuw != null){
						String terug = "Nieuwe klus toevoegd voor: " + nieuw.getAuto().getKenteken();
						if(nieuw instanceof Onderhoudsbeurt){
							terug = "Nieuwe onderhoudsbeurt toevoegd voor: " + nieuw.getAuto().getKenteken();
						}
						else if(nieuw instanceof Reparatie){
							terug = "Nieuwe reparatie toevoegd voor: " + nieuw.getAuto().getKenteken();
						}
						req.setAttribute("msg", terug);
						gemaakt = true;
					}
					else{
						req.setAttribute("msg", "Kan deze klus niet toevoegen!");	
					}
				}
				catch(Exception ex){
					System.out.println(ex);
					req.setAttribute("error", "Geen geldige datum! Gebruik format dd-mm-jjjj");
				}
			}
			else{
				req.setAttribute("error", "Vul alle velden in en kies een klustype!");
			}
			if(!gemaakt){
				ConnectDBAuto autoconn = new ConnectDBAuto();
				Auto deAuto = autoconn.zoekAuto(autoid);
				req.setAttribute("deAuto", deAuto);
			}
		}
		
	}
}
