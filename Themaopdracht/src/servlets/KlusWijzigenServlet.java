package servlets;
//Resultaten uit het verleden bieden geen garanties voor de toekomst blijkt maar weer
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

import database.ConnectDB;
import database.ConnectDBAuto;
import database.ConnectDBKlant;
import database.ConnectDBKlus;
import domeinklassen.Auto;
import domeinklassen.Klant;
import domeinklassen.Klus;

public class KlusWijzigenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ConnectDB database = new ConnectDB();
		Connection con = database.maakVerbinding();
		
		String knop = req.getParameter("knop");
		
		//roep alle klanten van het bedrijf op
		if(knop.equals("klanten")){
			ConnectDBKlant klantconn = new ConnectDBKlant(con);	
			ArrayList<Klant> klanten = klantconn.getKlanten();
			req.setAttribute("klanten", klanten);
		}
		//roep alle autos van de geselecteerde klant op
		else if(knop.equals("autos")){
			ConnectDBAuto autoconn = new ConnectDBAuto(con);
			String knr = req.getParameter("gekozenklant");
			int klantnummer = Integer.parseInt(knr);
			ArrayList<Auto> autos = autoconn.getAutosVan(klantnummer);
			req.setAttribute("autos", autos);
		}

		else if(knop.equals("klus")){
			//haal de klussen uit de database
			ConnectDBKlus klusconn = new ConnectDBKlus(con);
			int autoid = Integer.parseInt(req.getParameter("gekozenauto"));
			ArrayList<Klus> klussen = klusconn.getKlussenVoorAuto(autoid);
			req.setAttribute("klussen", klussen);
		}
		
		else if(knop.equals("status")){
			//roep de klus op uit de database
			ConnectDBKlus klusconn = new ConnectDBKlus(con);
			int klusid = Integer.parseInt(req.getParameter("gekozenklus"));
			Klus deKlus = klusconn.zoekKlus(klusid);
			String klusstatus = deKlus.getStatus();	
			req.setAttribute("deKlus", deKlus);
			req.setAttribute("gekozenklus", deKlus.getID());
			req.setAttribute("klusstatus", klusstatus);
		}
		
		else if(knop.equals("bevestig")){
			// sla de nieuwe status op in de klus
			//String dat = req.getParameter("datum");
			//String beschrijving = req.getParameter("beschrijving");
			String status = req.getParameter("status");
			int klusid = Integer.parseInt(req.getParameter("gekozenklus"));
			ConnectDBKlus klusconn = new ConnectDBKlus(con);
			Klus deKlus = klusconn.zoekKlus(klusid);
			
			//boolean allesIngevuld = (dat!=null) && (beschrijving!=null)&& (status!=null);
			boolean gemaakt = false;
			if(!status.equals("")){	
				try{				
					//SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					//Date datum = df.parse(dat);
					deKlus.setStatus(status);
					if(klusconn.updateKlus(deKlus)){
						req.setAttribute("msg", "Status gewijzigd");
						gemaakt = true;
					}
					else{
						req.setAttribute("error", "Status kon niet worden gewijzigd");
					}
				}
				catch(Exception ex){
					System.out.println(ex);
					req.setAttribute("error", "Geen geldige datum! Gebruik format dd-mm-jjjj");
				}
			}
			else{
				req.setAttribute("error", "Vul alle velden in!");
			}
			if(!gemaakt){
				req.setAttribute("deKlus",deKlus);
			}

		}
		database.sluitVerbinding(con);
		RequestDispatcher rd = req.getRequestDispatcher("kluswijzigen.jsp");
		rd.forward(req, resp);
	}
}
