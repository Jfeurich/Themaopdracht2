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

import database.ConnectDB;
import database.ConnectDBAuto;
import database.ConnectDBKlant;
import database.ConnectDBReservering;
import domeinklassen.Auto;
import domeinklassen.Klant;
import domeinklassen.Reservering;
import domeinklassen.User;

public class NieuweReserveringServlet extends HttpServlet{
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher("nieuwereservering.jsp");
		ConnectDB database = new ConnectDB();
		Connection con = database.maakVerbinding();
		String knop = req.getParameter("knop");
		
		if(knop.equals("klanten")){
			Object g = req.getSession().getAttribute("gebruiker");
			if(g != null){
				User deGebruiker = (User)g;
				if(deGebruiker.getType() == 3){
					req.setAttribute("msg", "Leuk geprobeerd. U kunt alleen reserveren voor uw eigen auto's.");
				}
			}
			else{			
				ConnectDBKlant klantconn = new ConnectDBKlant(con);
				ArrayList<Klant> klanten = klantconn.getKlanten();
				req.setAttribute("klanten", klanten);
			}
		}
		// haal auto's uit de database
		else if(knop.equals("autos")){
			Object g = req.getSession().getAttribute("gebruiker");
			if(g != null){
				User deGebruiker = (User)g;
				if(deGebruiker.getType() == 3){
					req.setAttribute("msg", "Leuk geprobeerd. U kunt alleen reserveren voor uw eigen auto's.");
				}
			}
			else{	
				ConnectDBAuto autoconn = new ConnectDBAuto(con);
				String knr = req.getParameter("gekozenklant");
				int klantnummer = Integer.parseInt(knr);
				ArrayList<Auto> autos = autoconn.getAutosVan(klantnummer);
				req.setAttribute("autos", autos);
			}
		}
		else if(knop.equals("kiesAuto")){
			ConnectDBAuto autoconn = new ConnectDBAuto(con);
			String autoid = req.getParameter("gekozenauto");
			Auto deAuto = autoconn.zoekAuto(Integer.parseInt(autoid));
			req.setAttribute("deAuto", deAuto);
		}
		else if(knop.equals("maakReservering")){
			String auto = req.getParameter("deAuto");
			String begindat = (String) req.getSession().getAttribute("beginDat");
			String einddat = (String) req.getSession().getAttribute("eindDat");
			int autoid = Integer.parseInt(auto);
			ConnectDBAuto autoconn = new ConnectDBAuto(con);
			Auto deAuto = autoconn.zoekAuto(autoid);
			boolean magMaken = false;
			//check voor geldige datum
			try{
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				Date bD = df.parse(begindat);
				Date eD = df.parse(einddat);
				int parkeerplek = (int) req.getSession().getAttribute("parkeerplek");
				int dP = 1;
				if(eD.before(bD)){	//check of de einddatum wel na de begindatum is
					req.setAttribute("error", "De einddatum komt NA de begindatum!");
				}
				else{
					ConnectDBReservering rconn = new ConnectDBReservering(con);
					ArrayList<Reservering> reserveringen = rconn.getReserveringenTussen(bD, eD);
					if(reserveringen.size() > 40){	//kijk of er nog plek is op de gewenste data
						req.setAttribute("error", "Helaas! Er is in deze periode geen plek meer in de parkeergarage!");
					}
					else if(reserveringen.size() == 0){
						magMaken = true;
					}
					else{ //als er plek is, kies de eerste beschikbaar
						boolean heeftPlek = false;
						while(dP <= 40){	//checkt voor alle 40 plekken of ze beschikbaar zijn
							for(Reservering r : reserveringen){
								//kijk of de auto een andere reservering heeft die begint of eindigt tussen de gekozen data
								if(r.getAuto().getID() == autoid){	
									req.setAttribute("error", "Er bestaat al een reservering voor deze auto in deze periode!");
									magMaken = false;
									break;
								}	//kijk of de parkeerplek bezet is
								else if(r.getDeParkeerplek() == dP){		
									heeftPlek = false;
									dP++;
									break;
								}
								else{
									heeftPlek = true;
									magMaken = true;
								}
							}
							if(!magMaken || heeftPlek){
								break;
							}
						}
					}
					if(magMaken){	//maak de reservering en geef een succesbericht terug
						rconn.nieuweReservering(deAuto, parkeerplek, bD, eD);
						String terug = "Reservering met succes aangemaakt voor parkeerplaats: " + parkeerplek;
						req.setAttribute("msg", terug);
						req.getRequestDispatcher("index.jsp");
					}
					else if(dP == 41){
						req.setAttribute("error", "Er zijn geen parkeerplaatsen beschikbaar in deze periode!");
					}
				}
			}
			catch(Exception ex){
				System.out.println(ex);
				req.setAttribute("error", "Kon de reservering niet toevoegen!");
			}
			if(!magMaken){	//als de reservering niet aan is gemaakt, geef de auto terug voor een tweede poging
				req.setAttribute("deAuto", deAuto);
			}
		}
		database.sluitVerbinding(con);
		rd.forward(req, resp);
	}
}
