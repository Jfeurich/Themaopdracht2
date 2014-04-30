package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBAuto;
import database.ConnectDBProduct;
import domeinklassen.Auto;
import domeinklassen.BesteldProduct;
import domeinklassen.Bestelling;
import domeinklassen.Product;

public class NieuweBestellingServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String knop = req.getParameter("knop");
		
		if(knop.equals("maakBestelling")){
			ConnectDBProduct productcon = new ConnectDBProduct();	
			ArrayList<Product> producten = productcon.getProducten();
			req.setAttribute("producten", producten);
			Bestelling deBestelling = new Bestelling();
			req.setAttribute("deBestelling", deBestelling);
		}
		else if(knop.equals("kiesProducten")){
			ConnectDBProduct productcon = new ConnectDBProduct();
			String[] gekozenProducten = req.getParameterValues("gekozenProduct");
			ArrayList<Product> teBestellenProducten = new ArrayList<Product>();
			for(int i = 0; i < gekozenProducten.length; i++){
				teBestellenProducten.add(productcon.zoekProduct(Integer.parseInt(gekozenProducten[i])));
			}
			req.setAttribute("teBestellenProducten", teBestellenProducten);
		}
		else if(knop.equals("bestel")){
			//input van html naar BesteldProduct(en) omzetten
			ArrayList<BesteldProduct> deBesteldeProducten= new ArrayList<BesteldProduct>();
			Bestelling deBestelling = (Bestelling) req.getAttribute("deBestelling");
			deBestelling.setBesteldeProducten(deBesteldeProducten);
			
		}
		RequestDispatcher rd = req.getRequestDispatcher("nieuweklus.jsp");
		rd.forward(req, resp);	
	}
}
