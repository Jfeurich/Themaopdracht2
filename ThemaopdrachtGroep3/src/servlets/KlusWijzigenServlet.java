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
import database.ConnectDBGebruiktProduct;
import database.ConnectDBKlant;
import database.ConnectDBKlus;
import database.ConnectDBProduct;
import domeinklassen.Auto;
import domeinklassen.Klant;
import domeinklassen.Klus;
import domeinklassen.Product;

public class KlusWijzigenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ConnectDB database = new ConnectDB();
		Connection con = database.maakVerbinding();
		
		String knop = req.getParameter("knop");
		RequestDispatcher rd = req.getRequestDispatcher("kluswijzigen.jsp");
		
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
		
		else if(knop.equals("wijzig")){
			//roep de klus op uit de database
			ConnectDBKlus klusconn = new ConnectDBKlus(con);
			int klusid = Integer.parseInt(req.getParameter("gekozenklus"));
			Klus deKlus = klusconn.zoekKlus(klusid);
			req.setAttribute("deKlus", deKlus);
		}
		
		else if(knop.equals("bevestig")){
			String dat = req.getParameter("datum");
			String beschrijving = req.getParameter("beschrijving");
			String status = req.getParameter("status");
			String mu = req.getParameter("manuren");
			int klusid = Integer.parseInt(req.getParameter("gekozenklus"));
			ConnectDBKlus klusconn = new ConnectDBKlus(con);
			Klus deKlus = klusconn.zoekKlus(klusid);
			
			boolean magMaken = true;
			boolean gemaakt = false;
			if(!dat.equals("")){	//als er een nieuwe datum in is gevuld, check of deze geldig is
				try{				
					SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					Date datum = df.parse(dat);
					deKlus.setDatum(datum);
				}
				catch(Exception ex){
					System.out.println(ex);
					req.setAttribute("error", "Geen geldige datum! Gebruik format dd-mm-jjjj");
					magMaken = false;
				}
			}
			if(!mu.equals("")){		//als er uren in zijn gevuld, check of dit een geldig nummer is
				try{				
					int uren = Integer.parseInt(mu);
					deKlus.addManuren(uren);
				}
				catch(Exception ex){
					System.out.println(ex);
					req.setAttribute("error", "Vul een geldig nummer in bij manuren!");
					magMaken = false;
				}
			}
			if(!beschrijving.equals("")){	//als er een beschijving in is gevuld, geef deze door
				deKlus.setBeschrijving(beschrijving);
			}
			deKlus.setStatus(status);		//status wordt altijd gewijzigd
			if(magMaken){
				if(klusconn.updateKlus(deKlus)){
					req.setAttribute("msg", "Klus gewijzigd");
					gemaakt = true;
				}
				else{
					req.setAttribute("error", "Klus kon niet worden gewijzigd");
				}
			}
			if(!gemaakt){
				req.setAttribute("deKlus",deKlus);
			}

		}
		else if (knop.equals("nieuwArtikel")){
			int klusid = Integer.parseInt(req.getParameter("gekozenklus"));
			rd = req.getRequestDispatcher("artikeltoevoegenaanklus.jsp");
			ConnectDBKlus klusconn = new ConnectDBKlus(con);
			Klus deKlus = klusconn.zoekKlus(klusid);
			req.setAttribute("deKlus",deKlus);
			ConnectDBProduct conn = new ConnectDBProduct(con);	
			ArrayList<Product> deVoorraad = conn.getProducten();
			req.setAttribute("voorraadlijst", deVoorraad);		
		}
		else if(knop.equals("VoegArtikelToe")){
			int klusid = Integer.parseInt(req.getParameter("gekozenklus"));
			ConnectDBKlus klusconn = new ConnectDBKlus(con);
			Klus deKlus = klusconn.zoekKlus(klusid);
			req.setAttribute("deKlus",deKlus);
			try{
				int aantal = Integer.parseInt(req.getParameter("aantal"));
				int productid = Integer.parseInt(req.getParameter("product"));
				ConnectDBGebruiktProduct gpconn = new ConnectDBGebruiktProduct(con);
				gpconn.nieuwGebruiktProduct(klusid, productid, aantal);
				req.setAttribute("msg", "Product toegevoegd!");
			}
			catch(Exception ex){
				System.out.println(ex);
				req.setAttribute("error", "Voer een geldig aantal in!");
				rd = req.getRequestDispatcher("artikeltoevoegenaanklus.jsp");
				ConnectDBProduct conn = new ConnectDBProduct(con);	
				ArrayList<Product> deVoorraad = conn.getProducten();
				req.setAttribute("voorraadlijst", deVoorraad);	
			}
		}
		database.sluitVerbinding(con);
		rd.forward(req, resp);
	}
}
