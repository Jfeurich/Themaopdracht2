package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBKlant;
import database.ConnectDBReservering;
import domeinklassen.Auto;
import domeinklassen.Reservering;
import domeinklassen.User;

public class ReserveringAnnulerenServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection con = (Connection)req.getSession().getAttribute("verbinding");	
		String knop = req.getParameter("knop");
		RequestDispatcher rd = req.getRequestDispatcher("reserveringannuleren.jsp");
		
		if(knop.equals("Haal op")){
			ConnectDBKlant klantconn = new ConnectDBKlant(con);
			req.setAttribute("klanten", klantconn.getKlanten());
		}
		else if(knop.equals("Kies klant")){
			String knr = req.getParameter("gekozenklant");
			int klantnummer = Integer.parseInt(knr);
			ConnectDBKlant klantconn = new ConnectDBKlant(con);
			ArrayList<Auto> autosVanKlant = klantconn.zoekKlant(klantnummer).getDeAutos();
			ArrayList<Reservering> deReserveringen = new ArrayList<Reservering>();
			for(Auto a: autosVanKlant){
				ConnectDBReservering resconn = new ConnectDBReservering(con);
				for(Reservering r: resconn.zoekReserveringenVanAuto(a.getID())){
					deReserveringen.add(r);
				}
			}
			if(deReserveringen.size() > 0){		
				req.setAttribute("deReserveringen", deReserveringen);
			}
			else{
				req.setAttribute("error", "Er zijn geen reserveringen voor deze auto!");
			}
		}
		else if(knop.equals("Haal reserveringen op")){
			User gebruiker = (User) req.getSession().getAttribute("gebruiker");
			ArrayList<Auto> autosVanKlant = gebruiker.getDeKlant().getDeAutos();
			ArrayList<Reservering> deReserveringen = new ArrayList<Reservering>();
			for(Auto a: autosVanKlant){
				ConnectDBReservering resconn = new ConnectDBReservering(con);
				for(Reservering r: resconn.zoekReserveringenVanAuto(a.getID())){
					deReserveringen.add(r);
				}
			}
			if(deReserveringen.size() > 0){		
				req.setAttribute("deReserveringen", deReserveringen);
			}
			else{
				req.setAttribute("error", "Er zijn geen reserveringen voor deze auto!");
			}
		}
		else if(knop.equals("Kies reservering")){
			ConnectDBReservering resconn = new ConnectDBReservering(con);
			Reservering r = resconn.zoekReservering(Integer.parseInt(req.getParameter("gekozenReservering")));
			req.setAttribute("reservering", r);
			req.setAttribute("reserveringID", r.getID());
		}
		else if(knop.equals("Bevestig")){
			ConnectDBReservering resconn = new ConnectDBReservering(con);
			Reservering r = resconn.zoekReservering(Integer.parseInt(req.getParameter("gekozenReservering")));
			resconn.verwijderReservering(r.getID());
			req.setAttribute("msg", "De reservering is succesvol geannuleerd");
			req.getRequestDispatcher("index.jsp");
		}
		rd.forward(req, resp);
	}
}
