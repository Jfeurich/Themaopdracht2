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
import database.ConnectDBFactuur;
import domeinklassen.Factuur;

public class OnbetaaldeFacturenOverzichtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String knop = req.getParameter("knop");
		ConnectDB database = new ConnectDB();
		Connection con = database.maakVerbinding();
		RequestDispatcher rd = null;
		if(knop.equals("overzicht")){
			ConnectDBFactuur conn = new ConnectDBFactuur(con);	
			ArrayList<Factuur> terug = conn.getFacturenNietBetaald();
			rd = req.getRequestDispatcher("onbetaaldefacturenoverzicht.jsp");
			req.setAttribute("OverzichtFacturenNietBetaald", terug);
		}
		else if(knop.equals("zoek")){
			req.setAttribute("factuurid", req.getParameter("factuurid"));
			rd = req.getRequestDispatcher("onbetaaldefacturenoverzicht.jsp");
		}
		else if(knop.equals("betaal")){
			ConnectDBFactuur factuurcon = new ConnectDBFactuur(con);
			Factuur deFactuur = factuurcon.zoekFactuur(Integer.parseInt(req.getParameter("factuurid")));
			Date datum = new Date();
			deFactuur.betaal(req.getParameter("betaalmiddel"), datum);
			factuurcon.updateFactuur(deFactuur);
			rd = req.getRequestDispatcher("onbetaaldefacturenoverzicht.jsp");
			req.setAttribute("stap1", "done");
		}
		rd.forward(req, resp);
		database.sluitVerbinding(con);
	}
}
