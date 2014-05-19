package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDB;
import database.ConnectDBAuto;
import database.ConnectDBHerinneringsbrief;
import database.ConnectDBKlant;
import domeinklassen.Klant;

public class BriefServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ConnectDB database = new ConnectDB();
		Connection con = database.maakVerbinding();
		String knop = req.getParameter("knop");
	
		//haal alle klanten uit de database
		if(knop.equals("ZoekKlanten")){
			ConnectDBKlant klantcon = new ConnectDBKlant(con);
			ArrayList<Klant> klanten = klantcon.getKlanten();
			req.setAttribute("klanten", klanten);	
		}
		//haal alle autos van de gekozen klant uit de database
		else if(knop.equals("KiesKlant")){	
			String klantnr = req.getParameter("gekozenklant");
			req.setAttribute("deKlant", klantnr);
			req.setAttribute("msg", "Vul de reden voor de brief in en verstuur deze naar de gekozen klant");
		}
		//als de gebruiker een auto wil toevoegen...
		else if(knop.equals("NieuweBrief")){
			String reden = req.getParameter("reden");
			String klantnr = req.getParameter("klantnummer");
			int klantid = Integer.parseInt(klantnr);
			if(reden == null || reden.equals("")){ 
				req.setAttribute("error", "Geef een reden aan!");
				req.setAttribute("deKlant", klantnr);
			}
			else{
				ConnectDBHerinneringsbrief hcon = new ConnectDBHerinneringsbrief(con);
				hcon.nieuweBrief(klantid, reden);
				req.setAttribute("msg", "Brief met succes aangemaakt!");
			}		
		}
		database.sluitVerbinding(con);
		req.getRequestDispatcher("nieuwebrief.jsp").forward(req, resp);
	}
}
