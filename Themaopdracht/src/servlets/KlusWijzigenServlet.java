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
import domeinklassen.GebruiktProduct;
import domeinklassen.Klant;
import domeinklassen.Klus;
import domeinklassen.Product;

public class KlusWijzigenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ConnectDB database = new ConnectDB();
		Connection con = database.maakVerbinding();
		String knop = req.getParameter("knop");
		
		//stuur antwoord bij default terug naar kluswijzigen.jsp
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
		//haal de klussen van de gekozen auto uit de database
		else if(knop.equals("klus")){
			ConnectDBKlus klusconn = new ConnectDBKlus(con);
			int autoid = Integer.parseInt(req.getParameter("gekozenauto"));
			ArrayList<Klus> klussen = klusconn.getKlussenVoorAuto(autoid);
			req.setAttribute("klussen", klussen);
		}
		//roep de gekozen klus op uit de database
		else if(knop.equals("wijzig")){
			ConnectDBKlus klusconn = new ConnectDBKlus(con);
			int klusid = Integer.parseInt(req.getParameter("gekozenklus"));
			Klus deKlus = klusconn.zoekKlus(klusid);
			req.setAttribute("deKlus", deKlus);
		}
		//probeer de gegeven waardes te wijzigen bij de gekozen klus
		else if(knop.equals("bevestig")){
			String dat = req.getParameter("datum");
			String beschrijving = req.getParameter("beschrijving");
			String status = req.getParameter("status");
			String mu = req.getParameter("manuren");
			int klusid = Integer.parseInt(req.getParameter("gekozenklus"));
			ConnectDBKlus klusconn = new ConnectDBKlus(con);
			Klus deKlus = klusconn.zoekKlus(klusid);
			//check voor zowel of ie gemaakt mag worden (geldige getallen ingevoerd) en of ie daadwerkelijk is gemaakt
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
		//bij nieuw artikel toevoegen, stuur door naar artikeltoevoegenaanklus.jsp
		//geef de klus mee en alle producten die momenteel op voorraad zijn
		else if (knop.equals("nieuwArtikel")){
			int klusid = Integer.parseInt(req.getParameter("gekozenklus"));
			rd = req.getRequestDispatcher("artikeltoevoegenaanklus.jsp");
			ConnectDBKlus klusconn = new ConnectDBKlus(con);
			Klus deKlus = klusconn.zoekKlus(klusid);
			req.setAttribute("deKlus",deKlus);
			ConnectDBProduct conn = new ConnectDBProduct(con);	
			ArrayList<Product> deVoorraad = conn.getProductenOpVoorraad();
			req.setAttribute("voorraadlijst", deVoorraad);		
		}
		//vanuit artikeltoevoegenaanklus.jsp, als de gebruiker artikelen toe wil voegen:
		else if(knop.equals("VoegToe")){
			int klusid = Integer.parseInt(req.getParameter("gekozenklus"));
			ConnectDBKlus klusconn = new ConnectDBKlus(con);
			Klus deKlus = klusconn.zoekKlus(klusid);
			req.setAttribute("deKlus",deKlus);	//geef de klus hoe dan ook mee terug (de server kan deze tenslotte niet onthouden
			String[] alleProducten = req.getParameterValues("alleProducten");	//alle mogelijke producten op volgorde
			String[] producten = req.getParameterValues("product");		//alle geselecteerde producten
			String[] aantallen =  req.getParameterValues("aantal");		//alle mogelijke aantallen op volgorde
			String[] opVoorraad =  req.getParameterValues("voorraad");	//alle mogelijk voorraad op volgorde
			/*
			Sla producten tijdelijk op, deze arraylist wordt pas in de database gezet als alle gewenste producten
			met correcte informatie in zijn gevuld.
			*/
			ArrayList<GebruiktProduct> toegevoegdeProducten = new ArrayList<GebruiktProduct>();
			boolean allesToegevoegd = true;
			/*
			Dit stuk is nogal lastig omdat checkboxes met product idalleen door worden gegeven als ze zijn geselecteerd, 
			tewijl zowel de aantallen als de voorraad hoe dan ook door worden gegeven.
			De lijst index van het gewijzigde product komt dus niet overeen met de index voor het aantal en de voorraad
			van dat product. 
			Ik heb dit opgelost door een array te maken met alle mogelijke producten, en bij alle gewijzigde producten de
			index te zoeken van dat product in het totale array. De totaal-index wordt dan gebruikt om de bijpassende
			vooraad en aantal te pakken.
			 */
			if(producten.length != 0){	//is er uberhaupt een product geselecteerd?
				for(int i = 0; i < alleProducten.length; i++){		//dit is de index in de totaallijst
					int optieID = Integer.parseInt(alleProducten[i]);
					for(int j = 0; j < producten.length; j++){		//en de index in de gewijzigde productenIDs
						int productid = Integer.parseInt(producten[j]);	
						if(productid == optieID){	//kijk hier of het product gewijzigd moet worden
							int aantal = 0;
							int voorraad = 5;
							try{	//zo ja, haal de bijpassende aantal en voorraad op en kijk of dit geldige getallen zijn
								aantal = Integer.parseInt(aantallen[i]);
								voorraad = Integer.parseInt(opVoorraad[i]);
							}
							catch(Exception ex){
								System.out.println(ex);
								req.setAttribute("error", "Voer een geldig aantal in!");
								allesToegevoegd = false;
								break;
							}
							if(aantal < voorraad){	//en of het gewenste aantal niet hoger is dan de voorraad
								toegevoegdeProducten.add(new GebruiktProduct(productid, aantal));
							}
							else{
								req.setAttribute("error", "Ingevoerd aantal is hoger dan de voorraad van dit product!");
								allesToegevoegd = false;
								break;
							}
						}
					}
				}
			}
			//als alle geselecteerde artikelen met succes zijn toegevoegd, maak ze aan in de database en stuur
			//de gebruikt terug naar kluswijzigen
			if(allesToegevoegd){
				req.setAttribute("msg", "Product(en) toegevoegd!");
				for(GebruiktProduct gp : toegevoegdeProducten){
					ConnectDBGebruiktProduct gpconn = new ConnectDBGebruiktProduct(con);
					gpconn.nieuwGebruiktProduct(klusid, gp.getID(), gp.getAantal());
				}
			}
			//als ie niet toe kan voegen, stuur weer terug naar artikeltoevoegenaanklus en geef producten op voorraad
			//opnieuw mee
			else{
				rd = req.getRequestDispatcher("artikeltoevoegenaanklus.jsp");
				ConnectDBProduct conn = new ConnectDBProduct(con);	
				ArrayList<Product> deVoorraad = conn.getProductenOpVoorraad();
				req.setAttribute("voorraadlijst", deVoorraad);	
			}
		}
		database.sluitVerbinding(con);
		rd.forward(req, resp);
	}
}
