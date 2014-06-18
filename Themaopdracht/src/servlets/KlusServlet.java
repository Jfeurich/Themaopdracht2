package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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

public class KlusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection con = (Connection)req.getSession().getAttribute("verbinding");
		String knop = req.getParameter("knop");
		//stuur antwoord bij default naar nieuweklus.jsp
		RequestDispatcher rd = req.getRequestDispatcher("klus.jsp");
		
		//geselecteerde klus annuleren
		if(knop.equals("nieuw")){
			ConnectDBKlant klantconn = new ConnectDBKlant(con);	
			ArrayList<Klant> klanten = klantconn.getKlanten();
			if(klanten.size() > 0){
				req.setAttribute("klanten", klanten);
				req.setAttribute("stap1", "done");
				rd = req.getRequestDispatcher("nieuweklus.jsp");
			}
			else{
				req.setAttribute("error", "Er staan nog geen klanten in het systeem!");
			} 
		}
		else if(knop.equals("Zoek")){
			String s = "Er is gezocht aan de hand van de volgende zoektermen:\n";
			Date dat1 = null;
			Date dat2 = null;
			SimpleDateFormat df = null;
			ConnectDBKlus kcon = new ConnectDBKlus(con);
			ArrayList<Klus> klussen = new ArrayList<Klus>();
			Map<String, String> zoekenop = new HashMap<String, String>();
			String id = req.getParameter("zoekid");
			if(!id.equals("")){
				zoekenop.put("id", id);
			}
			String autoid = req.getParameter("zoekautoid");
			if(!autoid.equals("")){
				zoekenop.put("autoid", autoid);
			}
			String ken = req.getParameter("zoekautoken");
			if(!ken.equals("")){
				zoekenop.put("ken", ken);
			}
			String mk = req.getParameter("zoekautomerk");
			if(!mk.equals("")){
				zoekenop.put("mk", mk);
			}
			String tp = req.getParameter("zoekautotype");
			if(!tp.equals("")){
				zoekenop.put("tp", tp);
			}
			String status = req.getParameter("zoekstatus");
			if(!status.equals("")){
				zoekenop.put("status", status);
			}
			String nadat = req.getParameter("nadatum");
			String voordat = req.getParameter("voordatum");
			if(!voordat.equals("") && !nadat.equals("")){
				zoekenop.put("nadat", nadat);
				zoekenop.put("voordat", voordat);
				df = new SimpleDateFormat("dd-MM-yyyy");
			}
			else if(!voordat.equals("") || !nadat.equals("")){
				req.setAttribute("error", "Als u op datum wilt zoeken, vul dan beide velden in!");
			}
			String beschrijving = req.getParameter("zoekbeschrijving");
			if(!beschrijving.equals("")){
				zoekenop.put("beschrijving", beschrijving);
			}
			for (Entry<String, String> entry : zoekenop.entrySet()) {
			    String key = entry.getKey();
			    String value = entry.getValue();
				if(key.equals("id")){
					try{
						Klus k = kcon.zoekKlus(Integer.parseInt(value));
						if(k != null){
							klussen.add(k);
							s += "ID = " + value;
						}
					}catch(NumberFormatException ex){
						req.setAttribute("error", "Voer een geldig id in!");
					}
				}
				else if(key.equals("autoid")){
					try{
						ArrayList<Klus> lijst = kcon.getKlussenVoorAuto(Integer.parseInt(value));
						if(lijst.size() != 0){
							for(Klus k : lijst){
								klussen.add(k);
							}
							s += " AutoID = " + value;
						}
					}catch(NumberFormatException ex){
						req.setAttribute("error", "Voer een geldig id in!");
					}				
				}
				else if(key.equals("ken")){
					ConnectDBAuto acon = new ConnectDBAuto(con);
					ArrayList<Auto> lijst = acon.zoekAutoKenteken(value);
					if(lijst != null){
						for(Auto a : lijst){
							for(Klus k : a.getKlussen()){
								klussen.add(k);
							}
							s += " kenteken = " + value;
						}
					}
				}
				else if(key.equals("mk")){
					ConnectDBAuto acon = new ConnectDBAuto(con);
					ArrayList<Auto> lijst = acon.zoekAutoMerk(value);
					if(lijst != null){
						for(Auto a : lijst){
							for(Klus k : a.getKlussen()){
								klussen.add(k);
							}
							s += " merk = " + value;
						}
					}
				}
				else if(key.equals("tp")){
					ConnectDBAuto acon = new ConnectDBAuto(con);
					ArrayList<Auto> lijst = acon.zoekAutoType(value);
					if(lijst != null){
						for(Auto a : lijst){
							for(Klus k : a.getKlussen()){
								klussen.add(k);
							}
							s += " type = " + value;
						}
					}
				}
				else if(key.equals("status")){
					ArrayList<Klus> lijst = kcon.getKlussenMetStatus(value);
					if(lijst.size() != 0){
						for(Klus k : lijst){
							klussen.add(k);
						}
						s += " status = " + value;
					}			
				}
				else if(key.equals("nadat")){
					try {
						dat1 = df.parse(value);
					} catch (ParseException e) {
						req.setAttribute("error", "Vul een geldige datum in!");
					}
				}
				else if(key.equals("voordat")){
					try {
						dat2 = df.parse(value);
					} catch (ParseException e) {
						req.setAttribute("error", "Vul een geldige datum in!");
					}				
				}
				else if(key.equals("beschrijving")){
					ArrayList<Klus> lijst = kcon.getKlussenMetBeschrijving(value);
					if(lijst.size() != 0){
						for(Klus k : lijst){
							klussen.add(k);
						}
						s += " beschrijving = " + value;
					}			
				}
			}
			if(dat1 != null && dat2 != null){
				ArrayList<Klus> lijst = kcon.getKlussenTussenData(dat1, dat2);;
				if(lijst.size() != 0){
					for(Klus k : lijst){
						klussen.add(k);
					}
					s += " datum tussen = " + df.format(dat1) + " en " + df.format(dat2);
				}			
			}
			if(klussen.size() == 0){
				req.setAttribute("msg", "Geen klussen gevonden met ingevoerde zoekterm(en)");
			}
			else{
				req.setAttribute("gezochtop", s);
				req.setAttribute("gevondenklussen", klussen);
			}
		}
		else if(knop.equals("Nieuwe zoektermen")){
			req.setAttribute("gevondenklussen", null);
		}
		//klanten ophalen en doorsturen bij nieuwe klus of klus wijzigen
		else{
			String klus = req.getParameter("gekozenklus");
			if(klus != null){
				int klusid = Integer.parseInt(klus);
				ConnectDBKlus klusconn = new ConnectDBKlus(con);
				Klus deKlus = klusconn.zoekKlus(klusid);
				if(knop.equals("annuleren")){
					klusconn.verwijderKlus(deKlus.getID());
					req.setAttribute("msg", "Klus is succesvol geannuleerd");
				}
				else if(knop.equals("wijzig")){
					req.setAttribute("deKlus", deKlus);
					rd = req.getRequestDispatcher("kluswijzigen.jsp");
				}
			}
			else{
				req.setAttribute("error", "Er is geen klus geselecteerd!");
			}
		}
		rd.forward(req, resp);
	}
}
		