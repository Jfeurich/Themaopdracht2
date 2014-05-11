package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDB;
import database.ConnectDBAuto;
import database.ConnectDBKlant;
import domeinklassen.Auto;
import domeinklassen.Klant;

public class AutoToevoegenServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ConnectDB database = new ConnectDB();
		Connection con = database.maakVerbinding();
		String knop = req.getParameter("knop");
	
		ConnectDBKlant klantcon = new ConnectDBKlant(con);
		//haal alle klanten uit de database
		if(knop.equals("ZoekKlanten")){
			ArrayList<Klant> klanten = klantcon.getKlanten();
			req.setAttribute("klanten", klanten);	
		}
		//haal alle autos van de gekozen klant uit de database
		else if(knop.equals("KiesKlant")){	
			String klantnr = req.getParameter("autovanklant");
			req.setAttribute("deKlant", klantnr);
		}
		//als de gebruiker een auto wil toevoegen...
		else if(knop.equals("VoegAutoToe")){
			String ken = req.getParameter("kenteken");
			String mk = req.getParameter("merk");
			String tp = req.getParameter("type");
			boolean allesIngevuld = (ken!="") && (mk!="") && (tp!="");	//kijk of alles in is gevuld
			if(!allesIngevuld){ 
				req.setAttribute("error", "Vul alle velden in!");
			}
			else{
				ken = ken.toUpperCase();
				String klantnr = req.getParameter("klantnummer");
				int klantid = Integer.parseInt(klantnr);
				Klant deKlant = klantcon.zoekKlant(klantid);
				boolean magToevoegen = true;
				for(Auto a : deKlant.getAutos()){	//check of deze klant al een auto met dit kenteken heeft
					if(ken.equals(a.getKenteken())){
						magToevoegen = false;
						break;
					}
				}
				if(magToevoegen){
					ConnectDBAuto autocon = new ConnectDBAuto(con);	
					autocon.nieuweAuto(ken, mk, tp, deKlant);
					req.setAttribute("msg", "Auto met succes toegevoegd!");
				}
				else{
					req.setAttribute("error", "Deze klant heeft al een auto met dit kenteken!");
				}
			}		
		}
		database.sluitVerbinding(con);
		RequestDispatcher rd = req.getRequestDispatcher("autotoevoegen.jsp");
		rd.forward(req, resp);
	}
}
