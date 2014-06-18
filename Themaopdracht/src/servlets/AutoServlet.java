package servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBAuto;
import domeinklassen.Auto;

public class AutoServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection con = (Connection)req.getSession().getAttribute("verbinding");
		String knop = req.getParameter("knop");
		
		if(knop.equals("Zet actief/non-actief")){
			String geb = req.getParameter("kiesauto");
			ConnectDBAuto acon = new ConnectDBAuto(con);
			Auto a = acon.zoekAuto(Integer.parseInt(geb));
			String actief = req.getParameter("actief");
			if(actief.equals("ja")){
				acon.verwijderAuto(a.getID());
			}
			else{
				acon.activeerAuto(a.getID());
			}
		}
		req.getRequestDispatcher("overzichtautos.jsp").forward(req, resp);
	}
}
