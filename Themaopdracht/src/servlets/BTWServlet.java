package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBFactuur;
import domeinklassen.Factuur;

public class BTWServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection con = (Connection)req.getSession().getAttribute("verbinding");
		String knop = req.getParameter("knop");
		//stuur antwoord bij default terug naar klus.jsp
		RequestDispatcher rd = req.getRequestDispatcher("btwoverzicht.jsp");
		
		if(knop.equals("overzicht")){
			String bjr = req.getParameter("beginjaar");
			String ejr = req.getParameter("eindjaar");
			//check dat er (geldige) jaren in zijn gevuld
			if(bjr == null || ejr == null || bjr.length() != 4 || ejr.length() != 4){
				req.setAttribute("error", "Vul een geldig begin- en eindjaar in!");
			}
			else if(!bjr.matches("[0-9]+") || !ejr.matches("[0-9]+")){
				req.setAttribute("error", "Vul een geldig begin- en eindjaar in!");
			}
			else if(Integer.parseInt(ejr) < 1950 || Integer.parseInt(bjr) < 1950){
				req.setAttribute("error", "Dit programma gaat niet verder terug dan 1950!");
			}
			else if(Integer.parseInt(ejr) < Integer.parseInt(bjr)){
				req.setAttribute("error", "Eindjaar komt NA het beginjaar!");
			}
			else{
				//check dat er een begin- en eindkwartaal is gekozen
				String beginkwartaal = req.getParameter("beginkwartaal");
				String eindkwartaal = req.getParameter("eindkwartaal");
				if(beginkwartaal == null || eindkwartaal == null){
					req.setAttribute("error", "Kies een begin- en eindkwartaal!");
				}
				else{
					try{
						//stel data in aan de hand van gekozen kwartalen
						if(beginkwartaal.equals("1")){ bjr = "01-01-" + bjr; }
						else if(beginkwartaal.equals("2")){ bjr = "01-04-" + bjr; }
						else if(beginkwartaal.equals("3")){ bjr = "01-07-" + bjr; }
						else if(beginkwartaal.equals("4")){ bjr = "01-10-" + bjr; }
						if(eindkwartaal.equals("1")){ ejr = "31-03-" + ejr; }
						else if(eindkwartaal.equals("2")){ ejr = "30-06-" + ejr; }
						else if(eindkwartaal.equals("3")){ ejr = "30-09-" + ejr; }
						else if(eindkwartaal.equals("4")){ ejr = "31-12-" + ejr; }
						SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
						Date start = df.parse(bjr);
						Date eind = df.parse(ejr);
						//haal betaalde facturen op tussen gekozen kwartalen
						ConnectDBFactuur fcon = new ConnectDBFactuur(con);
						ArrayList<Factuur> facturen = fcon.getBetaaldeFacturenTussen(start, eind);
						if(facturen.size() == 0){
							req.setAttribute("msg", "Geen facturen beschikbaar tussen deze data!");
						}
						else{
							req.setAttribute("facturen", facturen);
						}
					}
					catch (ParseException e) {
						req.setAttribute("error", "Ongeldige data!");
						e.printStackTrace();
					}
				}
			}
		}	
		rd.forward(req, resp);
	}
}