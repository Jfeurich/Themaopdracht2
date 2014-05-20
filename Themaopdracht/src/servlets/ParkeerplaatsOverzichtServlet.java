package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDB;
import database.ConnectDBReservering;
import domeinklassen.Reservering;

public class ParkeerplaatsOverzichtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ConnectDB database = new ConnectDB();
		Connection con = database.maakVerbinding();
		
		String knop = req.getParameter("knop");
		RequestDispatcher rd = req.getRequestDispatcher("parkeerplaatsoverzicht.jsp");
		
		if(knop.equals("Checkdatum")){
			req.getSession().setAttribute("gevondenReserveringen", null);
			req.getSession().setAttribute("beginDat", null);
			req.getSession().setAttribute("eindDat", null);
			String bD = req.getParameter("begindat");
			String eD = req.getParameter("einddat");
			//check of beide datums zijn ingevuld
			if(!bD.equals("") && !eD.equals("")){
				//check of beide datums geldige datums zijn
				try{
					DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					Date beginDat =  df.parse(bD);  
					Date eindDat = df.parse(eD);
					//de begin- en einddatum opslaan in de sessie
					req.getSession().setAttribute("beginDat",beginDat);
					req.getSession().setAttribute("eindDat", eindDat);
					//alle reserveringen tussen deze twee data zoeken
					ConnectDBReservering conres = new ConnectDBReservering(con);
					ArrayList<Reservering> deReserveringen = conres.getReserveringenTussen(beginDat, eindDat);
					//alle gevonden reserveringen als attribute setten
					req.getSession().setAttribute("gevondenReserveringen", deReserveringen);
				}
				catch(Exception e){
					req.setAttribute("error", "Geen geldige datum!");
				}
			}
			else{
				req.setAttribute("error", "Vul alle velden in!");
			}
		}
		else{
			int parkeerplekken = ( (int) getServletContext().getAttribute("parkeerplaatsRij")) * ( (int) getServletContext().getAttribute("parkeerplaatsKolom"));
			for(int i = 1; i <= parkeerplekken; i++){
				if(knop.equals(i)){
					//De parkeerplek id meegeven aan de sessie
					req.getSession().setAttribute("parkeerplek", i);
					//De gevonden parkeerplekken uit de sessie halen
					req.getSession().setAttribute("gevondenReserveringen", null);
					rd = req.getRequestDispatcher("nieuwereservering.jsp");
				}
			}
		}
		rd.forward(req, resp);	
		database.sluitVerbinding(con);
	}
}
