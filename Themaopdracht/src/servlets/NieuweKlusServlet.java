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
import domeinklassen.Auto;
import domeinklassen.Klant;
import domeinklassen.Klus;
import domeinklassen.Onderhoudsbeurt;
import domeinklassen.Reparatie;

public class NieuweKlusServlet extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String knop = req.getParameter("knop");
		
		//roep alle klanten van het bedrijf op
		if(knop.equals("klanten")){
			ConnectDBKlant klantconn = new ConnectDBKlant();	
			ArrayList<Klant> klanten = klantconn.getKlanten();
			req.setAttribute("klanten", klanten);
		}
		//roep alle autos van de geselecteerde klant op
		else if(knop.equals("autos")){
			ConnectDBAuto autoconn = new ConnectDBAuto();
			String knr = req.getParameter("gekozenklant");
			int klantnummer = Integer.parseInt(knr);
			ArrayList<Auto> autos = autoconn.getAutosVan(klantnummer);
			req.setAttribute("autos", autos);
		}
		//roep al het werk aan wat aan deze auto is gedaan en laat nieuweklus.jsp het forumulier tonen om een klus toe te voegen
		else if(knop.equals("kiesauto")){
			ConnectDBAuto autoconn = new ConnectDBAuto();
			String autoid = req.getParameter("gekozenauto");
			Auto deAuto = autoconn.zoekAuto(Integer.parseInt(autoid));
			req.setAttribute("gekozen", deAuto);
		}
		else if(knop.equals("voeg toe")){
			ConnectDBKlus klusconn = new ConnectDBKlus();
			String type = req.getParameter("type");
			String dat = req.getParameter("datum");
			String beschrijving = req.getParameter("beschrijving");
			String auto = req.getParameter("autovanklus");
			
			boolean allesIngevuld = !type.equals("") && !dat.equals("") && !beschrijving.equals("");
			if(allesIngevuld){
				//check voor geldige datum
				try{
					SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					Date datum = df.parse(dat);
					int autoid = Integer.parseInt(auto);
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
		}
		RequestDispatcher rd = req.getRequestDispatcher("nieuweklus.jsp");
		rd.forward(req, resp);	
	}
}
