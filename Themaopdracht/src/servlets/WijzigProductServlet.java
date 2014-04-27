package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domeinklassen.Product;

public class WijzigProductServlet extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ArrayList<Product> deVoorraad = new ArrayList<Product>();
		Product p1 = new Product("Band, Type1", 111, 10, "stuk", 50.0);
		Product p2 = new Product("Moertje", 123, 100, "stuk", 0.50);
		Product p3 = new Product("Diesel", 001, 50, "liter", 3.50);
		deVoorraad.add(p1); deVoorraad.add(p2); deVoorraad.add(p3);
		
		String knop = req.getParameter("knop");
		if(knop.equals("wijzig")){
			String p = req.getParameter("product");
			Product hetProduct = null;
			for(Product zoek : deVoorraad){
				if(zoek.getArtikelNr() == Integer.parseInt(p)){
					hetProduct = zoek;
				}
			}
			String naam = req.getParameter("naam");
			String at = req.getParameter("aantal");
			String eenheid = req.getParameter("eenheid");
			String ma = req.getParameter("minaantal");
			String pps = req.getParameter("pps");
			
			boolean nieuweNaam = !naam.equals("");
			boolean nieuwAantal = !at.equals("");
			boolean nieuweEenheid = !eenheid.equals("");
			boolean nieuwMinimum = !ma.equals("");
			boolean nieuwePrijs = !pps.equals("");
			
			boolean wijzigen = nieuweNaam || nieuwAantal || nieuweEenheid || nieuwMinimum || nieuwePrijs;
					
			if(nieuweNaam){
				hetProduct.setNaam(naam);
			}
			else if(nieuwAantal){
				if(!at.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
					req.setAttribute("error", "Vul een geldig aantal in!");
				}	
				else{
					int aantal = Integer.parseInt(at);
					hetProduct.setAantal(aantal);
				}
			}
			else if(nieuweEenheid){
				hetProduct.setEenheid(eenheid);
			}
			else if(nieuwMinimum){
				if(!ma.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
					req.setAttribute("error", "Vul een geldig minimumaantal in!");
				}	
				else{
					int min = Integer.parseInt(ma);
					hetProduct.setMinimumAanwezig(min);
				}
			}
			else if(nieuwePrijs){
				if(!pps.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
					req.setAttribute("error", "Vul een geldige prijs in!");
				}
				else{
					double prijsPerStuk = Double.parseDouble(pps);
					hetProduct.setPrijsPerStuk(prijsPerStuk);
				}
			}
			if(wijzigen){
				req.setAttribute("msg", "Het product is gewijzigd.");
			}
			else{
				req.setAttribute("msg", "Geen wijzigingen aangebracht.");
			}
			req.setAttribute("product", hetProduct);
			RequestDispatcher rd = req.getRequestDispatcher("wijzigproduct.jsp");
			rd.forward(req, resp);
		}
	}
}
