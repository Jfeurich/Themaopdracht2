package servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDB;
import database.ConnectDBKlant;
import database.ConnectDBUser;
import domeinklassen.Klant;
import domeinklassen.User;

public class RegistreerServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		String gebruikersnaam = req.getParameter("gebrnaam");
		String wachtwoord1 = req.getParameter("ww1");
		String wachtwoord2 = req.getParameter("ww2");
		String email1 = req.getParameter("mail1");
		String email2 = req.getParameter("mail2");
		String naam = req.getParameter("nm");
		String adres = req.getParameter("adr");
		String woonplaats = req.getParameter("wp");
		String rekeningnr = req.getParameter("rnr");
		String knopje = req.getParameter("knop");
		
		
		ConnectDB database = new ConnectDB();
		Connection con = database.maakVerbinding();
		//public Klant nieuweKlant(String nm, String adr, String wp, String rnr, int nr)
		if(knopje != null){
			//check of de velden zijn ingevuld
			if(!gebruikersnaam.equals("") && !wachtwoord1.equals("") && !wachtwoord2.equals("") && !email1.equals("") && !email2.equals("")){
				//check of wachtwoorden etc. aan elkaar gelijk is
				if(wachtwoord1.equals(wachtwoord2) && email1.equals(email2)){
					try{
						//user aanmaken en in database stoppen
						//user die al klant zijn
						//Klant object aanmaken, dan met dat klant object een user object aanmaken
						ConnectDBUser usercon = new ConnectDBUser(con);
						ConnectDBKlant klantcon = new ConnectDBKlant(con);
						Klant k = new Klant(naam, adres, woonplaats, rekeningnr, nr);
						klantcon.nieuweKlant(naam, adres, woonplaats, rekeningnr, nr);
						usercon.nieuweUserIsKlant(k, gebruikersnaam, wachtwoord1, email1);
					}
					catch(Exception ex){
						req.setAttribute("error", "Er is geen gebruiker geregistreerd!"); 
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
				req.setAttribute("error", s); 
				
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
			}
		}
		
	}
}
