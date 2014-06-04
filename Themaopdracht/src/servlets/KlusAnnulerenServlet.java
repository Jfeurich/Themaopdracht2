package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBKlus;
import domeinklassen.Klus;

public class KlusAnnulerenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection con = (Connection)req.getSession().getAttribute("verbinding");		
		String knop = req.getParameter("knop");
		String viaID = req.getParameter("zoekviaID");
		
		//Haalt de klussen op via ingevoerde ID
		if (knop.equals("zoek")){
			if(!viaID.equals("")){
				try{
					ConnectDBKlus klusconn = new ConnectDBKlus(con);
					int autoid = Integer.parseInt(req.getParameter("zoekviaID"));
					ArrayList<Klus> klussen = klusconn.getKlussenVoorAuto(autoid);
					if(klussen.size() > 0){		
						req.setAttribute("klussen", klussen);
					}
					else{
						req.setAttribute("error", "Er zijn nog geen klussen voor deze auto!");
					}
				}
				catch(Exception ex){
					req.setAttribute("error","Er zijn bestaan geen klussen van het ingevoerde ID");
				}
			}
			else{
				req.setAttribute("error", "Voer een ID in!");
			}
		}
		//haalt de gekozen klus op uit de database en verwijderd hem vervolgens uit de database
		else if(knop.equals("annuleer")){
			String klus = req.getParameter("gekozenklus");
			if(klus != null){
				int klusid = Integer.parseInt(klus);
				ConnectDBKlus klusconn = new ConnectDBKlus(con);
				klusconn.verwijderKlus(klusid);
				req.setAttribute("msg", "Klus is succesvol geannuleerd");
			}
			else{
				req.setAttribute("error", "Er is geen klus geselecteerd!");
			}
		}
		
		req.getRequestDispatcher("klusannuleren.jsp").forward(req, resp);
	}
}
