package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBAuto;
import database.ConnectDBFactuur;
import database.ConnectDBKlant;
import database.ConnectDBKlus;
import domeinklassen.Auto;
import domeinklassen.Factuur;
import domeinklassen.Klant;
import domeinklassen.Klus;

public class NieuweFactuurServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection con = (Connection)req.getSession().getAttribute("verbinding");
		String knop = req.getParameter("knop");
		RequestDispatcher rd = req.getRequestDispatcher("nieuwefactuur.jsp");
		req.setAttribute("stap1", "done");
		if(knop.equals("Done")){
			resp.sendRedirect("index.html");
		}
		// haal auto's uit de database
		else if(knop.equals("autos")){
			ConnectDBAuto autoconn = new ConnectDBAuto(con);
			String knr = req.getParameter("gekozenklant");
			int klantnummer = Integer.parseInt(knr);
			ArrayList<Auto> autos = autoconn.getAutosVan(klantnummer);
			req.setAttribute("autos", autos);
		}
		//haal klussen uit de database
		else if(knop.equals("klus")){
			ConnectDBKlus klusconn = new ConnectDBKlus(con);
			int autoid = Integer.parseInt(req.getParameter("gekozenauto"));
			ArrayList<Klus> klussen = klusconn.getKlussenVoorAuto(autoid);
			req.setAttribute("klussen", klussen);
		}
		//gebruiker heeft een klus geselecteerd en wil een factuur maken
		else if(knop.equals("nieuw")){
			String klus = req.getParameter("gekozenklus");
			boolean gemaakt = false;
			if(!klus.equals("")){
				int klusid = Integer.parseInt(klus);
				ConnectDBKlus klusconn = new ConnectDBKlus(con);
				Klus deKlus = klusconn.zoekKlus(klusid);
				if(deKlus.getStatus().equals("voltooid")){	//is de status van de klus wel "voltooid?"
					ConnectDBFactuur fconn = new ConnectDBFactuur(con);
					if(fconn.getFactuurVanKlus(klusid) == null){	//check of deze klus al een factuur heeft
						Factuur nieuw = fconn.nieuweFactuur(deKlus);
						if(nieuw != null){		//kijk of de factuur met succes in de database is gezet
							req.setAttribute("msg", "Factuur aangemaakt");
							req.setAttribute("deFactuur", nieuw);
							gemaakt = true;
						}
						else{
							req.setAttribute("error", "Factuur kan niet aangemaakt worden");
						}
					}
					else{
						req.setAttribute("error", "De factuur van deze klus bestaat al!");
					}
				}
				else{
					req.setAttribute("error", "Deze klus is nog niet voltooid!");
				}
			}
			else{
				req.setAttribute("error", "Er zijn geen klussen beschikbaar!");
			}
			if(!gemaakt){	//als de factuur niet aan is gemaakt, geef de klanten terug voor een tweede poging
				ConnectDBKlant kcon = new ConnectDBKlant(con);
				ArrayList<Klant> klanten = kcon.getKlanten();
				req.setAttribute("klanten", klanten);
			}
		}
		//stel het kortingspercentage in
		else if(knop.equals("bevestig")){
			String factuurid = req.getParameter("factuurvoorkorting");
			String korting = req.getParameter("korting");
			try{	//check of het een geldig nummer is
				int dekorting = Integer.parseInt(korting);
				ConnectDBFactuur factuurcon = new ConnectDBFactuur(con);
				Factuur deFactuur = factuurcon.zoekFactuur(Integer.parseInt(factuurid));
				deFactuur.setKortingsPercentage(dekorting);
				factuurcon.updateFactuur(deFactuur);
				req.setAttribute("msg", "De korting is ingesteld");
				rd = req.getRequestDispatcher("factuur.jsp");
				//mail factuur naar klant
				Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.port", 465);
				props.put("mail.smtp.ssl.enable", true);
				Session mailSession = Session.getInstance(props);
				try {
					MimeMessage msg = new MimeMessage(mailSession);
					msg.setFrom(new InternetAddress("testvoorwebapps@gmail.com", "test"));
					msg.setRecipients(Message.RecipientType.TO, deFactuur.getDeKlus().getAuto().getEigenaar().getAccount().getEmail());
					msg.setSubject("Uw factuur");
					msg.setSentDate(Calendar.getInstance().getTime());
					msg.setText("Beste " + deFactuur.getDeKlus().getAuto().getEigenaar().getNaam() + 
							", \n\nU ontvangt deze factuur met betrekking tot:\n" +  deFactuur.getDeKlus().toString() + 
							"\nKosten: " + deFactuur.getTotaal() + "\nGelieve dit bedrag binnen 30 dagen over te maken." + 
							"\nMet vriendelijke groet,\n\nAutoTotaalBeheer");
					Transport.send(msg, "testvoorwebapps@gmail.com", "Wachtwoord0");
					req.setAttribute("msg", "De factuur is verstuurd");
				} 
				catch (Exception e) {
					e.printStackTrace();
					req.setAttribute("error", "Kon factuur niet versturen");
				}
			}
			catch(Exception e){
				req.setAttribute("error", "Geen geldige waarde");
				req.setAttribute("deFactuur", factuurid);
			}
		}
		rd.forward(req, resp);
	}
}
