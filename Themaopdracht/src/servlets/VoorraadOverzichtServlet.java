package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domeinklassen.Product;

public class VoorraadOverzichtServlet extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String knop = req.getParameter("knop");
		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");
		
		ArrayList<Product> deVoorraad = new ArrayList<Product>();
		Product p1 = new Product("Band, Type1", 111, 10, "stuk", 50.0);
		Product p2 = new Product("Moertje", 123, 100, "stuk", 0.50);
		Product p3 = new Product("Diesel", 001, 50, "liter", 3.50);
		deVoorraad.add(p1); deVoorraad.add(p2); deVoorraad.add(p3);
		
		if(knop.equals("overzicht")){
			req.setAttribute("voorraadlijst", deVoorraad);
			RequestDispatcher rd = req.getRequestDispatcher("voorraadoverzicht.jsp");
			rd.forward(req, resp);
		}
		
		else if(knop.equals("nieuw")){
			String nm = req.getParameter("naam");
			String anr = req.getParameter("artikelnr");
			String ma = req.getParameter("minaantal");
			String eh = req.getParameter("eenheid");
			String pps = req.getParameter("pps");
			if((nm != null) && (anr != null) && (ma != null) && (eh != null)){
				if(!anr.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
					//stuur errorbericht "Geen geldig artikelnummer"
				}
				else if(!ma.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
					//stuur errorbericht "Geen geldig minimumaantal"
				}
				else{
					int artikelnr = Integer.parseInt(anr);
					boolean beschikbaarnummer = true;
					for(Product p : deVoorraad){
						if(p.getArtikelNr() == artikelnr){
							beschikbaarnummer = false;
							//geef errorbericht "Artikelnummer is al in gebruik"
							break;
						}
					}
					if(beschikbaarnummer && (pps == null)){
						Product p = new Product(nm, artikelnr, Integer.parseInt(ma), eh);
						deVoorraad.add(p);
					}
					else if(beschikbaarnummer){
						if(!pps.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
							//geef errorbericht "Geen geldige prijs"
						}
						else{
							double prijs = Double.parseDouble(pps);
							Product p = new Product(nm, artikelnr, Integer.parseInt(ma), eh, prijs);
							deVoorraad.add(p);
						}
					}
				}
			}
		}
		
		else if(knop.equals("zoek")){
			
		}
	}
}