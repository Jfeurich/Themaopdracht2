package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBReservering;
import domeinklassen.Reservering;

public class ReserveringAnnulerenServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection con = (Connection)req.getSession().getAttribute("verbinding");	
		String knop = req.getParameter("knop");
		
		//Haalt de reserveringen op via ingevoerde ID
		if (knop.equals("zoek")){
			String viaID = req.getParameter("zoekviaID");
			if(!viaID.equals("")){
				try{
					ConnectDBReservering reserveringconn = new ConnectDBReservering(con);
					int autoid = Integer.parseInt(req.getParameter("zoekviaID"));
					ArrayList<Reservering> reserveringen = reserveringconn.zoekReserveringenVanAuto(autoid);
					if(reserveringen.size() > 0){		
						req.setAttribute("reserveringen", reserveringen);
					}
					else{
						req.setAttribute("error", "Er zijn geen reserveringen voor deze auto!");
					}
				}
				catch(Exception ex){
					req.setAttribute("error","Voer een geldig ID in!");
				}
			}
			else{
				req.setAttribute("error", "Voer een auto ID in!");
			}
		}		
		//haalt de gekozen reservering op uit de database en verwijderd hem vervolgens uit de database
		else if(knop.equals("annuleer")){
			String reservering = req.getParameter("gekozenreservering");
			if(reservering != null){
				ConnectDBReservering reserveringconn = new ConnectDBReservering(con);
				reserveringconn.verwijderReservering(Integer.parseInt(reservering));
				req.setAttribute("msg", "De reservering is succesvol geannuleerd");
			}
			else{
				req.setAttribute("error", "Er is geen reservering geselecteerd!");
			}
		}
		req.getRequestDispatcher("reserveringannuleren.jsp").forward(req, resp);
	}
}
