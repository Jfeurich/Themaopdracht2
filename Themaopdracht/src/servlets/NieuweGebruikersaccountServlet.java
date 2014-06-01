package servlets;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDB;
import database.ConnectDBUser;
import database.ConnectDBKlant;
import domeinklassen.Klant;
import domeinklassen.User;

public class NieuweGebruikersaccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String gebruikersnaam = req.getParameter("username");
		String wachtwoord1 = req.getParameter("password");
		String wachtwoord2 = req.getParameter("password2");
		String type = req.getParameter("type");
		String email1 = req.getParameter("email");
		String email2 = req.getParameter("email2");
		String klantnummer = req.getParameter("klantnummer");
		String naam = req.getParameter("naam");
		String adres = req.getParameter("adres");
		String woonplaats = req.getParameter("plaats");
		String rekeningnr = req.getParameter("rekeningnummer");
		String telefoonnr = req.getParameter("telefoonnummer");
		Klant k = null;
		User u = null;
		
		ConnectDB database = new ConnectDB();
		Connection con = database.maakVerbinding();
		
		String knop = req.getParameter("knop");
		
		if(knop.equals("Haal klant")){
			//Haal de klantgegevens uit de database, of maak klant k als de klant nog niet bestaat.
			//Maak geen typfouten mevrouw van de administratie.
			ConnectDBKlant klantconn = new ConnectDBKlant(con);
			try{
				k = klantconn.zoekKlant(Integer.parseInt(klantnummer));
				req.setAttribute("klantnummer", k.getKlantnummer());
				req.setAttribute("username",k.getNaam());
				req.setAttribute("adres", k.getAdres());
				req.setAttribute("plaats", k.getPlaats());
				req.setAttribute("rekeningnummer", k.getRekeningnummer());
				req.setAttribute("telefoonnummer",k.getTelefoonnummer());
				req.setAttribute("msg", k.toString());
			}
			catch(Exception e){
				
				req.setAttribute("msg", "Nieuwe Klant moet eerst aangemaakt worden");
				//zorg ervoor dat een dialoogvenster geopend wordt waar gevraagd word om een nieuwe klant aan te maken
				//dit heeft de usecase nieuwe klant eerst nodig.
				/*
				 ConnectDBKlant klantcon = new ConnectDBKlant(con);
				 k = klantcon.nieuweKlant(naam, adres, woonplaats, rekeningnr, Integer.parseInt(telefoonnr));
				 */
			}
		}
		else if(knop.equals("Maak user")){
			//check of de velden zijn ingevuld
			if(!telefoonnr.equals("") && !rekeningnr.equals("") && !woonplaats.equals("") && !adres.equals("") && !naam.equals("") && !gebruikersnaam.equals("") && !wachtwoord1.equals("") && !wachtwoord2.equals("") && !email1.equals("") && !email2.equals("")){
				//check of wachtwoorden etc. aan elkaar gelijk is
				if(wachtwoord1.equals(wachtwoord2) && email1.equals(email2)){
					//check of telnr wel een int getal is
					if(telefoonnr.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
						//Hier is het stuk waar gecontroleerd wordt of een nieuwe user een klant is(type=3)
						if(type.equals("3")){
							try{
								//user aanmaken en in database stoppen
								//user die al klant zijn
								//Klant object aanmaken, dan met dat klant object een user object aanmaken
								ConnectDBUser usercon = new ConnectDBUser(con);
								u = usercon.nieuweUserIsKlant(k, gebruikersnaam, wachtwoord1, email1);
								req.setAttribute("msg", "Gebruiker "+ u.getGebruikersnaam()  +" met Klantnummer "+ u.getDeKlant().getKlantnummer() + " succesvol geregistreerd!");
							}
							catch(Exception ex){
								req.setAttribute("error", "De gegevens van de gebruiker staan nog niet in ons klantsysteem."); 
							}
						}
						//Als geen klant
						else if(!type.equals("3")){
							try{
								ConnectDBUser usercon = new ConnectDBUser(con);
								u = usercon.nieuweUserNietKlant(Integer.parseInt(type), gebruikersnaam, wachtwoord1, email1, naam);
								req.setAttribute("msg", "Gebruiker "+ u.getGebruikersnaam()  +" Van type "+ u.getType() + " succesvol geregistreerd!");
							}
							catch(Exception e){
								req.setAttribute("error", "Er kon geen gebruikersaccount aangemaakt worden");
							}
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
					s+= "\nnaam";
				}
				if(adres.equals("")){
					s+= "\nadres";
				}
				if(woonplaats.equals("")){
					s+= "\nwoonplaats";
				}
				if(rekeningnr.equals("")){
					s+= "\nrekeningnummer";
				}
				if(telefoonnr.equals("")){
					s+= "\ntelefoonnummer";
				}
				req.setAttribute("error", s); 
			}
		}
		RequestDispatcher rd = req.getRequestDispatcher("nieuwegebruikersaccount.jsp");
		rd.forward(req, resp);	
		database.sluitVerbinding(con);
	}
}
