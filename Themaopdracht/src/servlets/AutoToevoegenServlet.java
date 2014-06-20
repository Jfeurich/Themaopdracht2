package servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBAuto;
import database.ConnectDBKlant;
import domeinklassen.Auto;
import domeinklassen.Klant;
import domeinklassen.User;

public class AutoToevoegenServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection con = (Connection)req.getSession().getAttribute("verbinding");
		String knop = req.getParameter("knop");
		//haal alle autos van de gekozen klant uit de database
		if(knop.equals("KiesKlant")){	
			String klantnr = req.getParameter("autovanklant");
			req.setAttribute("deKlant", klantnr);
		}
		//als de gebruiker een auto wil toevoegen...
		else if(knop.equals("VoegAutoToe")){		
			ConnectDBKlant klantcon = new ConnectDBKlant(con);
			String ken = req.getParameter("kenteken");
			String mk = req.getParameter("merk");
			String tp = req.getParameter("type");
			boolean allesIngevuld = !ken.equals("") && !mk.equals("") && !tp.equals("");	//kijk of alles in is gevuld
			if(!allesIngevuld){ 
				req.setAttribute("error", "Vul alle velden in!");
			}
			else{
				ken = ken.toUpperCase();
				String klantnr = req.getParameter("klantnummer");
				int klantid = Integer.parseInt(klantnr);
				Klant deKlant = klantcon.zoekKlant(klantid);
				boolean magToevoegen = true;
				for(Auto a : deKlant.getDeAutos()){	//check of deze klant al een auto met dit kenteken heeft
					if(ken.equals(a.getKenteken())){
						magToevoegen = false;
						break;
					}
				}
				if(magToevoegen){
					ConnectDBAuto autocon = new ConnectDBAuto(con);	
					Auto a = autocon.nieuweAuto(ken, mk, tp, deKlant);
					User u = (User)req.getSession().getAttribute("gebruiker");
					if(u.getType() == 3){
						//voeg de auto toe aan de arraylist van auto's van de ingelogde klant
						Klant k = u.getDeKlant();
						k.voegAutoToe(a);
						req.getSession().setAttribute("gebruiker", u);
					}
					req.setAttribute("msg", "Auto met succes toegevoegd!");
				}
				else{
					req.setAttribute("error", "Deze klant heeft al een auto met dit kenteken!");
				}
			}		
		}
		RequestDispatcher rd = req.getRequestDispatcher("autotoevoegen.jsp");
		rd.forward(req, resp);
	}
}
