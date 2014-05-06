package servlets;
//Resultaten uit het verleden bieden geen garanties voor de toekomst
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
import domeinklassen.Factuur;
import domeinklassen.Klant;
import domeinklassen.Klus;

public class KlusWijzigenServlet extends HttpServlet {
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

		else if(knop.equals("klus")){
			//haal de klussen uit de database
			ConnectDBKlus klusconn = new ConnectDBKlus();
			int autoid = Integer.parseInt(req.getParameter("gekozenauto"));
			ArrayList<Klus> klussen = klusconn.getKlussenVoorAuto(autoid);
			req.setAttribute("klussen", klussen);
		}
		
		else if(knop.equals("status")){
			//roep de klus op uit de database
			ConnectDBKlus klusconn = new ConnectDBKlus();
			int klusid = Integer.parseInt(req.getParameter("gekozenklus"));
			Klus deKlus = klusconn.zoekKlus(klusid);
			req.setAttribute("deKlus", deKlus);
		}
		
		else if(knop.equals("getstatus")){
			//haal de status van de klus uit de database
			ConnectDBKlus klusconn = new ConnectDBKlus();
			int klusid = Integer.parseInt(req.getParameter("gekozenklus"));
			Klus deKlus = klusconn.zoekKlus(klusid);
			String klusstatus = req.getParameter("deKlus.getStatus()");	
			req.setAttribute("klusstatus", klusstatus);
		}
		
		else if(knop.equals("setstatus")){
			// sla de nieuwe status op in de klus
			ConnectDBKlus kluscon = new ConnectDBKlus();
			String dat = req.getParameter("datum");
			String beschrijving = req.getParameter("beschrijving");
			String status = req.getParameter("status");
			int klusid = Integer.parseInt(req.getParameter("gekozenklus"));
			
			boolean allesIngevuld = (dat!=null) && (beschrijving!=null)&& (status!=null);
			boolean gemaakt = false;
			if(allesIngevuld){
				//check voor geldige datum
				try{
					ConnectDBKlus klusconn = new ConnectDBKlus();
					
					SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					Date datum = df.parse(dat);
					Klus deKlus = klusconn.zoekKlus(klusid);
					//verander hier de klusstatus
					deKlus.setStatus(status);
					if(klusconn.updateKlus(deKlus) != false){
						String terug = "Status gewijzigd";
						req.setAttribute("msg", terug);
						gemaakt = true;
					}
					else{
						req.setAttribute("msg", "Status kon niet worden gewijzigd");
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
		RequestDispatcher rd = req.getRequestDispatcher("kluswijzigen.jsp");
		rd.forward(req, resp);	
	}
}
