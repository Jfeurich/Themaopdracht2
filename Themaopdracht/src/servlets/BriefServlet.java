package servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBHerinneringsbrief;

public class BriefServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection con = (Connection)req.getSession().getAttribute("verbinding");
		String knop = req.getParameter("knop");
	
		//haal alle autos van de gekozen klant uit de database
		if(knop.equals("KiesKlant")){	
			String klantnr = req.getParameter("gekozenklant");
			req.setAttribute("deKlant", klantnr);
			req.setAttribute("msg", "Vul de reden voor de brief in en verstuur deze naar de gekozen klant");
		}
		//als de gebruiker een auto wil toevoegen...
		else if(knop.equals("NieuweBrief")){
			String reden = req.getParameter("reden");
			String klantnr = req.getParameter("klantnummer");
			int klantid = Integer.parseInt(klantnr);
			if(reden == null || reden.equals("")){ 
				req.setAttribute("error", "Geef een reden aan!");
				req.setAttribute("deKlant", klantnr);
			}
			else{
				ConnectDBHerinneringsbrief hcon = new ConnectDBHerinneringsbrief(con);
				hcon.nieuweBrief(klantid, reden);
				req.setAttribute("msg", "Brief met succes aangemaakt!");
			}		
		}
		req.getRequestDispatcher("nieuwebrief.jsp").forward(req, resp);
	}
}
