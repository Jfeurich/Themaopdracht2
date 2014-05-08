package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBFactuur;
import database.ConnectDB;
import domeinklassen.Factuur;

public class OverzichtFacturenNietBetaaldServlet extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String knop = req.getParameter("knop");
		
		ConnectDB database = new ConnectDB();
		Connection con = database.maakVerbinding();
		ConnectDBFactuur conn = new ConnectDBFactuur(con);	
		ArrayList<Factuur> terug = conn.getFacturenNietBetaald();
		RequestDispatcher rd = req.getRequestDispatcher("onbetaaldefactuuroverzicht.jsp");;
		if(knop.equals("overzicht")){
			req.setAttribute("OverzichtFacturenNietBetaald", terug);
		}
		else if(knop.equals("betaal")){
			req.setAttribute(req.getParameter("factuurid"), "factuurid");
			rd = req.getRequestDispatcher("StatusWijzigenFactuurServlet.do");
		}
		rd.forward(req, resp);
		database.sluitVerbinding(con);
	}
}
