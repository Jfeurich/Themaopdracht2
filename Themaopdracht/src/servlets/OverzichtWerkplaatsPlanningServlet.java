package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBKlus;
import domeinklassen.Klus;

public class OverzichtWerkplaatsPlanningServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Connection con = (Connection)req.getSession().getAttribute("verbinding");
		String knop = req.getParameter("knop");
		ConnectDBKlus kcon = new ConnectDBKlus(con);
		
		if(knop.equals("Komende maand")){
			ArrayList<Klus> deKlussen = kcon.getKlussenbydatum();
			//alle gevonden klussen
			req.setAttribute("gevondenklussen", deKlussen);
		}
		else if(knop.equals("Zoek")){
			String s = "";
			Date dat1 = null;
			Date dat2 = null;
			SimpleDateFormat df = null;
			ArrayList<Klus> klussen = new ArrayList<Klus>();String nadat = req.getParameter("nadatum");
			String voordat = req.getParameter("voordatum");
			if(!voordat.equals("") && !nadat.equals("")){
				df = new SimpleDateFormat("dd-MM-yyyy");
				try{
					dat1 = df.parse(nadat);
					dat2 = df.parse(voordat);
				}catch(ParseException e) {
					req.setAttribute("error", "Vul geldige data in!");
				}
			}
			else if(!voordat.equals("") || !nadat.equals("")){
				req.setAttribute("error", "Als u op datum wilt zoeken, vul dan beide velden in!");
			}
			if(dat1 != null && dat2 != null){
				ArrayList<Klus> lijst = kcon.getKlussenTussenData(dat1, dat2);;
				if(lijst.size() != 0){
					for(Klus k : lijst){
						klussen.add(k);
					}
					s = df.format(dat1) + " en " + df.format(dat2);
				}			
			}
			if(klussen.size() == 0){
				req.setAttribute("msg", "Geen klussen gevonden tussen ingevoerde data");
			}
			else{
				req.setAttribute("gezochtop", s);
				req.setAttribute("gevondenklussen", klussen);
			}
		}
		req.getRequestDispatcher("overzichtwerkplaatsplanning.jsp").forward(req, resp);	
	}
}
