package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBAuto;
import database.ConnectDBKlant;
import database.ConnectDBKlus;
import domeinklassen.Auto;
import domeinklassen.Klant;
import domeinklassen.Klus;

public class KlusWijzigenServlet extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String knop = req.getParameter("knop");
		
		//roep alle klanten van het bedrijf op
		if(knop.equals("klanten")){
			ConnectDBKlant klantconn = new ConnectDBKlant();	
			ArrayList<Klant> klanten = klantconn.getKlanten();
			req.setAttribute("klanten", klanten);
		}
		//roep alle autos van de geselecteerde klant op
		else if(knop.equals("autos")){
			ConnectDBAuto autoconn = new ConnectDBAuto();
			String knr = req.getParameter("gekozenklant");
			int klantnummer = Integer.parseInt(knr);
			ArrayList<Auto> autos = autoconn.getAutosVan(klantnummer);
			req.setAttribute("autos", autos);
		}
		//kies auto
		else if(knop.equals("kiesauto")){
			ConnectDBAuto autoconn = new ConnectDBAuto();
			String autoid = req.getParameter("gekozenauto");
			Auto deAuto = autoconn.zoekAuto(Integer.parseInt(autoid));
			req.setAttribute("deAuto", deAuto);
		}
		//roep de klus op
		else if(knop.equals("kiesklus")){
			ConnectDBKlus klusconn = new ConnectDBKlus();
			String klusid = req.getParameter("gekozenklus");
			Klus deKlus = klusconn.zoekKlus(Integer.parseInt(klusid));
		}
		//getstatus van de klus
		else if(knop.equals("getstatus")){
			ConnectDBKlus klusconn = new ConnectDBKlus();
			String klusid = req.getParameter("gekozenklus");
			Klus deKlus = klusconn.zoekKlus(Integer.parseInt(klusid));
			String klusstatus = req.getParameter("deKlus.getStatus()");			
		}
		// sla de nieuwe status op in de klus
		else if(knop.equals("setstatus")){
			ConnectDBKlus kluscon = new ConnectDBKlus();
			Klus deKlus = kluscon.zoekKlus(Integer.parseInt(req.getParameter("klusid")));
			Date datum = new Date();
			deKlus.setStatus(req.getParameter("status"));
			kluscon.updateKlus(deKlus);
		}
		RequestDispatcher rd = req.getRequestDispatcher("kluswijzigen.jsp");
		rd.forward(req, resp);	
	}
}
