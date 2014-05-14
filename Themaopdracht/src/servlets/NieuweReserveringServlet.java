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

public class NieuweReserveringServlet extends HttpServlet{
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ConnectDB database = new ConnectDB();
		Connection con = database.maakVerbinding();
		String knop = req.getParameter("knop");
		
		if(knop.equals("klanten")){
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
		else if(knop.equals("kiesAuto")){
			ConnectDBAuto autoconn = new ConnectDBAuto(con);
			String autoid = req.getParameter("gekozenauto");
			Auto deAuto = autoconn.zoekAuto(Integer.parseInt(autoid));
			req.setAttribute("deAuto", deAuto);
		}
		else if(knop.equals("maakReservering")){
			String auto = req.getParameter("deAuto");
			String begindat = req.getParameter("begindatum");
			String einddat = req.getParameter("einddatum");
			String gewenstePlaats = req.getParameter("plaats");
			int autoid = Integer.parseInt(auto);
			ConnectDBAuto autoconn = new ConnectDBAuto(con);
			Auto deAuto = autoconn.zoekAuto(autoid);
			boolean magMaken = false;
			if(!begindat.equals("") && !einddat.equals("")){
				//check voor geldige datum
				try{
					int dP = Integer.parseInt(gewenstePlaats);
					SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					Date bD = df.parse(begindat);
					Date eD = df.parse(einddat);
					if(eD.before(bD)){	//check of de einddatum wel na de begindatum is
						req.setAttribute("error", "De eindddatum komt NA de begindatum!");
					}
					else{
						ConnectDBReservering rconn = new ConnectDBReservering(con);
						ArrayList<Reservering> reserveringen = rconn.getReserveringenTussen(bD, eD);
						if(reserveringen.size() > 40){	//kijk of er nog plek is op de gewenste data
							req.setAttribute("error", "Helaas! Er is in deze periode geen plek meer in de parkeergarage!");
						}
						else{
							for(Reservering r : rconn.zoekReserveringenVanAuto(autoid)){	//kijk of de reservering nog niet bestaat
								if(r.getBegDat().before(eD) || r.getEindDat().before(bD)){		
									req.setAttribute("error", "Er bestaat al een reservering voor deze auto in deze periode!");
									magMaken = false;
									break;
								}
								else if(r.getDeParkeerplek() == dP){	//kijk of de gewenste parkeerplek bezet is	
									req.setAttribute("error", "Deze parkeerplaats is al gereserveerd in deze periode!");
									magMaken = false;
									break;
								}
								else{
									magMaken = true;
								}
							}
							if(magMaken){	//maak de reservering en geef een succesbericht terug
								rconn.nieuweReservering(deAuto, dP, bD, eD);
								req.setAttribute("msg", "Reservering met succes aangemaakt");
							}
						}
					}
				}
				catch(Exception ex){
					System.out.println(ex);
					req.setAttribute("error", "Vul geldige data en een geldige parkeerplek in!");
				}
				if(!magMaken){	//als de reservering niet aan is gemaakt, geef de auto terug voor een tweede poging
					req.setAttribute("deAuto", deAuto);
				}
			}
			else{
				req.setAttribute("error", "Vul een begin- en einddatum in!");
			}
		}
		RequestDispatcher rd = req.getRequestDispatcher("nieuwereservering.jsp");
		rd.forward(req, resp);
		database.sluitVerbinding(con);
	}
}
