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
import domeinklassen.Klant;



public class NieuweKlantServlet extends HttpServlet {
	private static final long serialVersionUID = 1l;
	private String naam;
	private String adres;
	private String woonplaats;
	private String rekeningnummer;
	private int telefoonnummer;
	
	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String knop = req.getParameter("knop");
		ConnectDB database = new ConnectDB();
		Connection con = database.maakVerbinding();
			if(knop.equals("maak")){
				ConnectDBKlant klantconn = new ConnectDBKlant(con);
				 String naam = req.getParameter("naam");
				 String adres = req.getParameter("adres");
				 String woonplaats = req.getParameter("woonplaats");
				 String rekeningnummer = req.getParameter("rekeningnummer");
				 String tnr = req.getParameter("telefoonnummer");
				 int telefoonnummer = Integer.parseInt(tnr);
				 Klant nieuw = klantconn.nieuweKlant(naam, adres, woonplaats, rekeningnummer, telefoonnummer);
			}
	}

}
