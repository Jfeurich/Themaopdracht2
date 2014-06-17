package servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDB;
import database.ConnectDBKlant;
import database.ConnectDBUser;

public class RegistreerServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		String gebruikersnaam = req.getParameter("gebrnaam").toLowerCase();
		String wachtwoord1 = req.getParameter("ww1");
		String wachtwoord2 = req.getParameter("ww2");
		String email1 = req.getParameter("mail1");
		String email2 = req.getParameter("mail2");
		String naam = req.getParameter("nm");
		String adres = req.getParameter("adr");
		String woonplaats = req.getParameter("wp");
		String rekeningnr = req.getParameter("rnr");
		String telefoonnr = req.getParameter("telnr");
		String knopje = req.getParameter("knop");
		
		RequestDispatcher rd = req.getRequestDispatcher("registreer.jsp");
		//public Klant nieuweKlant(String nm, String adr, String wp, String rnr, int nr)
		if(knopje != null){
			//check of de velden zijn ingevuld
			if(!gebruikersnaam.equals("") && !wachtwoord1.equals("") && !wachtwoord2.equals("") && !email1.equals("") && !email2.equals("")){
				//check of wachtwoorden etc. aan elkaar gelijk is
				if(wachtwoord1.equals(wachtwoord2) && email1.equals(email2)){
					//check of telnr wel een int getal is
					if(telefoonnr.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
						try{
							//maak tijdelijk verbinding met database
							ConnectDB database = new ConnectDB();
							Connection con = database.maakVerbinding();
							ConnectDBUser usercon = new ConnectDBUser(con);
							//kijk of gebruikersnaam bezet is
							if(usercon.checkUser(gebruikersnaam.toLowerCase(), email1) == null){
								//user aanmaken en in database stoppen
								//user die al klant zijn
								//Klant object aanmaken, dan met dat klant object een user object aanmaken
								ConnectDBKlant klantcon = new ConnectDBKlant(con);
								usercon.nieuweUserIsKlant(klantcon.nieuweKlant(naam, adres, woonplaats, rekeningnr, Integer.parseInt(telefoonnr)), gebruikersnaam, wachtwoord1, email1);
								req.setAttribute("msg", "Succesvol geregistreerd!");
								rd = req.getRequestDispatcher("loginpage.jsp"); 
								//sluit verbinding met database (wordt voor de sessie gemaakt als de klant in logt)
								database.sluitVerbinding(con);
							}
							else{
								req.setAttribute("error", "Deze gebruikersnaam is bezet!");
							}
						}
						catch(Exception ex){
							req.setAttribute("error", "Er is geen gebruiker geregistreerd!"); 
						}
					}
					else{
						req.setAttribute("error", "Telefoonnummer is geen getal!");
					}
				}
				//Error bericht als iets niet aan elkaar gelijk is
				else{
					req.setAttribute("error", "Wachtwoorden en/of email adressen komen niet overeen!"); 
				}
			}
			//Error bericht voor niet ingevulde velden
			else{
				String s = "De volgende velden zijn niet ingevuld:";
				if(gebruikersnaam.equals("")){
					s+= "\nGebruikersnaam";
				}
				if(wachtwoord1.equals("")){
					s+= "\nWachtwoord";
				}
				if(wachtwoord2.equals("")){
					s+= "\nWachtwoord controle";
				}
				if(email1.equals("")){
					s+= "\nEmail";
				}
				if(email2.equals("")){
					s+= "\nEmail controle";
				}
				if(naam.equals("")){
					s+= "\nEmail controle";
				}
				if(adres.equals("")){
					s+= "\nEmail controle";
				}
				if(woonplaats.equals("")){
					s+= "\nEmail controle";
				}
				if(rekeningnr.equals("")){
					s+= "\nEmail controle";
				}
				if(telefoonnr.equals("")){
					s+= "\nEmail controle";
				}
				req.setAttribute("error", s); 
			}
		}
		rd.forward(req, resp); 
	}
}
