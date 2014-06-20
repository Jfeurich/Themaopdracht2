package servlets;

import java.io.IOException;
import java.sql.Connection;
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
import domeinklassen.User;

public class NieuweKlusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection con = (Connection)req.getSession().getAttribute("verbinding");
		String knop = req.getParameter("knop");
		req.setAttribute("stap1", "done");
		RequestDispatcher rd = req.getRequestDispatcher("nieuweklus.jsp");
		
		//roep alle autos van de geselecteerde klant op
		if(knop.equals("autos")){
			ConnectDBAuto autoconn = new ConnectDBAuto(con);
			String knr = req.getParameter("gekozenklant");
			int klantnummer = Integer.parseInt(knr);
			ArrayList<Auto> autos = autoconn.getAutosVan(klantnummer);
			req.setAttribute("autos", autos);
		}
		//roep al het werk aan wat aan deze auto is gedaan en laat nieuweklus.jsp het forumulier tonen om een klus toe te voegen
		else if(knop.equals("kiesauto") || knop.equals("Nieuwe klus")){
			ConnectDBAuto autoconn = new ConnectDBAuto(con);
			String autoid = req.getParameter("gekozenauto");
			Auto deAuto = autoconn.zoekAuto(Integer.parseInt(autoid));
			req.setAttribute("deAuto", deAuto);
		}
		//klant wil een klus toevoegen
		else if(knop.equals("nieuw")){
			ConnectDBKlus klusconn = new ConnectDBKlus(con);
			String type = req.getParameter("type");
			String dat = req.getParameter("datum");
			String beschrijving = req.getParameter("beschrijving");
			String auto = req.getParameter("autovanklus");
			int autoid = Integer.parseInt(auto);
			boolean allesIngevuld = (type != null) && (!dat.equals("")) && (!beschrijving.equals(""));
			boolean gemaakt = false;
			User u = (User)req.getSession().getAttribute("gebruiker");
			int utp = u.getType();
			if(allesIngevuld){	//check of alle velden in zijn gevuld
				try{	//check voor geldige datum
					SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					Date datum = df.parse(dat);
					if(utp == 3 && datum.before(new Date())){
						req.setAttribute("error", "U mag geen klussen in het verleden aanmaken!");
					}
					else{
						Klus nieuw = klusconn.nieuweKlus(datum, beschrijving, type, autoid);
						if(nieuw != null){
							String terug = "Nieuwe reparatie toevoegd voor: " + nieuw.getAuto().getKenteken();
							if(type.equals("onderhoudsbeurt")){
								terug = "Nieuwe onderhoudsbeurt toevoegd voor: " + nieuw.getAuto().getKenteken();
							}
							req.setAttribute("msg", terug);
							if(utp == 3){
								//Voeg de klus toe aan de klant in sessie zodat deze op de homepage wordt getoond
								Klant k = u.getDeKlant();
								Auto deAuto = nieuw.getAuto();
								for(Auto a : k.getDeAutos()){
									if(a.getID() == deAuto.getID()){
										deAuto = a;
									}
								}
								deAuto.voegKlusToe(nieuw);
								req.getSession().setAttribute("gebruiker", u);
							}
							else{
								ConnectDBKlant kcon = new ConnectDBKlant(con);
								ArrayList<Klant> klanten = kcon.getKlanten();
								req.setAttribute("klanten", klanten);
							}
							gemaakt = true;
						}
						else{
							req.setAttribute("msg", "Kan deze klus niet toevoegen!");	
						}
					}
				}
				catch(Exception ex){
					req.setAttribute("error", "Geen geldige datum! Gebruik format dd-mm-jjjj");
				}
			}
			else{
				req.setAttribute("error", "Vul alle velden in en kies een klustype!");
			}
			if(!gemaakt){
				ConnectDBAuto autoconn = new ConnectDBAuto(con);
				Auto deAuto = autoconn.zoekAuto(autoid);
				req.setAttribute("deAuto", deAuto);
			}
			if(gemaakt && utp == 3){
				rd = req.getRequestDispatcher("index.jsp");
			}
		}
		rd.forward(req, resp);	
	}
}
