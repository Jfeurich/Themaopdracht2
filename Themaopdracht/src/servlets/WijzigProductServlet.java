package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ConnectDBProduct;
import domeinklassen.Product;

public class WijzigProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Connection con = (Connection)req.getSession().getAttribute("verbinding");
		String knop = req.getParameter("knop");
		RequestDispatcher rd = null;	
		ConnectDBProduct conn = new ConnectDBProduct(con);
		
		if(knop.equals("Wijzigingen opslaan")){
			String p = req.getParameter("product");
			Product hetProduct = conn.zoekProduct(Integer.parseInt(p));
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
			
			//is er een nieuwe waarde ingevoerd?
			boolean wijzigen = nieuweNaam || nieuwAantal || nieuweEenheid || nieuwMinimum || nieuwePrijs;
					
			//probeera alle velden waar iets in iets gevoerd te wijzigen
			if(nieuweNaam){
				hetProduct.setNaam(naam);
			}
			else if(nieuwAantal){
				try{
					int aantal = Integer.parseInt(at);
					hetProduct.setAantal(aantal);
				}
				catch(Exception ex){
					System.out.println(ex);
					req.setAttribute("error", "Vul een geldig aantal in!");
				}
			}
			else if(nieuweEenheid){
				hetProduct.setEenheid(eenheid);
			}
			else if(nieuwMinimum){
				try{
					int min = Integer.parseInt(ma);
					hetProduct.setMinimumAanwezig(min);
				}
				catch(Exception ex){
					System.out.println(ex);
					req.setAttribute("error", "Vul een geldig minimumaantal in!");
				}
			}
			else if(nieuwePrijs){
				try{
					double prijsPerStuk = Double.parseDouble(pps);
					hetProduct.setPrijsPerStuk(prijsPerStuk);
				}
				catch(Exception ex){
					System.out.println(ex);
					req.setAttribute("error", "Vul een geldige prijs in!");
				}
			}
			if(wijzigen){
				if(conn.updateProduct(hetProduct)){
					req.setAttribute("msg", "Het product is gewijzigd.");
				}
				else{
					req.setAttribute("msg", "Het is niet gelukt om dit product te wijzigen.");
				}
			}
			else{
				req.setAttribute("msg", "Geen wijzigingen aangebracht.");
			}
			req.setAttribute("product", hetProduct);
			rd = req.getRequestDispatcher("wijzigproduct.jsp");
		}
		//stuur bij succesvollle wijziging terug naar overzicht producten
		else if(knop.equals("Deactiveren") || knop.equals("Activeren")){
			String p = req.getParameter("product");
			if(knop.equals("Deactiveren")){
				conn.verwijderProduct(Integer.parseInt(p));
				req.setAttribute("msg", "Product met succes op non-actief gezet.");
			}
			else if(knop.equals("Activeren")){
				conn.activeerProduct(Integer.parseInt(p));
				req.setAttribute("msg", "Product met succes op geactiveerd.");
			}
			ArrayList<Product> deVoorraad = conn.getProducten();
			req.setAttribute("voorraadlijst", deVoorraad);
			rd = req.getRequestDispatcher("productenoverzicht.jsp");	
		}
		rd.forward(req, resp);
	}
}
