package servlets;


import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDB;
import database.ConnectDBKlus;
import domeinklassen.Klus;

public class OverzichtWerkplaatsPlanningServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Date dat = new Date();
		
		ConnectDB database = new ConnectDB();
		Connection con = database.maakVerbinding();
	
		ConnectDBKlus klus = new ConnectDBKlus(con);
		ArrayList<Klus> deKlussen = klus.getKlussenbydatum();
		ArrayList<Klus> deKlussenvanafvandaag = new ArrayList<Klus>();
		for(Klus k: deKlussen){
			if(k.getDatum().after(dat)){
				deKlussenvanafvandaag.add(k);
			}	
		}
		//alle gevonden klussen
		req.getSession().setAttribute("gevondenKlussen", deKlussenvanafvandaag);
		RequestDispatcher rd = req.getRequestDispatcher("overzichtwerkplaatsplanning.jsp");
		rd.forward(req, resp);	
		database.sluitVerbinding(con);
	}

}
