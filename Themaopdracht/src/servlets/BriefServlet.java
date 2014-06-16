package servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBHerinneringsbrief;
import database.ConnectDBKlant;
import domeinklassen.Klant;

public class BriefServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection con = (Connection)req.getSession().getAttribute("verbinding");
		String knop = req.getParameter("knop");
	
		//zoek de gekozen klant op in de database en geef terug aan de jsp
		if(knop.equals("KiesKlant")){	
			String klantnr = req.getParameter("gekozenklant");
			ConnectDBKlant kcon = new ConnectDBKlant(con);
			Klant deKlant = kcon.zoekKlant(Integer.parseInt(klantnr));
			req.setAttribute("deKlant", deKlant);
		}
		//als de gebruiker een brief wil toevoegen...
		else if(knop.equals("NieuweBrief")){
			String reden = req.getParameter("reden");
			String klantnr = req.getParameter("klantnummer");
			int klantid = Integer.parseInt(klantnr);
			ConnectDBHerinneringsbrief hcon = new ConnectDBHerinneringsbrief(con);
			hcon.nieuweBrief(klantid, reden);
			req.setAttribute("msg", "Brief met succes verstuurd!");
		}
		req.getRequestDispatcher("nieuwebrief.jsp").forward(req, resp);
	}
}
