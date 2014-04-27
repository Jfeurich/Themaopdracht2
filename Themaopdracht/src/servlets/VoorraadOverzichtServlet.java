package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domeinklassen.Product;

public class VoorraadOverzichtServlet extends HttpServlet{
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String knop = req.getParameter("knop");
		
		ArrayList<Product> deVoorraad = new ArrayList<Product>();
		Product p1 = new Product("Band, Type1", 111, 10, "stuk", 50.0);
		Product p2 = new Product("Moertje", 123, 100, "stuk", 0.50);
		Product p3 = new Product("Diesel", 001, 50, "liter", 3.50);
		deVoorraad.add(p1); deVoorraad.add(p2); deVoorraad.add(p3);
		
		//forward voorraadlijst naar de overzicht-pagina.
		if(knop.equals("overzicht")){
			req.setAttribute("voorraadlijst", deVoorraad);
			RequestDispatcher rd = req.getRequestDispatcher("voorraadoverzicht.jsp");
			rd.forward(req, resp);
		}
		
		//maak een nieuw product aan
		else if(knop.equals("nieuw")){
			String nm = req.getParameter("naam");
			String ma = req.getParameter("minaantal");
			String eh = req.getParameter("eenheid");
			String pps = req.getParameter("pps");
			ArrayList<String> velden = new ArrayList<String>();
			velden.add(nm); velden.add(ma); velden.add(eh);
			
			//check of nodige velden in zijn gevuld (artikelnummer bestaat niet meer omdat de database die straks gaat aanmaken)
			boolean allesIngevuld = true;
			for(String s : velden){
				if(s.equals("")){
					allesIngevuld = false;
					req.setAttribute("nieuwmsg", "Vul alle niet-optionele velden in!");
					break;
				}
			}
			
			//als gegevens ingevuld
			if(allesIngevuld){
				
				//check voor geldig minimumaantal (int)
				if(!ma.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
					req.setAttribute("nieuwmsg", "Minimum aantal is geen geldig nummer!");
				}
				else{
					//check aanwezigheid prijs per stuk
					if(pps.equals("")){
						Product p = new Product(nm, 1, Integer.parseInt(ma), eh);
						deVoorraad.add(p);
					}
					else{
						//check voor geldige prijs per stuk (boolean)
						if(!pps.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
							req.setAttribute("nieuwmsg", "Prijs per stuk is geen geldig nummer!");
						}
						else{
							double prijs = Double.parseDouble(pps);
							Product p = new Product(nm, 1, Integer.parseInt(ma), eh, prijs);
							deVoorraad.add(p);
						}
					}
				}
			}
			RequestDispatcher rd = req.getRequestDispatcher("voorraad.jsp");
			rd.forward(req, resp);
		}
		
		//zoek product op naam of artikelnummer
		else if(knop.equals("zoek")){
			String nm = req.getParameter("zoeknaam");
			String anr = req.getParameter("zoeknummer");
			
			boolean zoekNaam = !nm.equals("");
			boolean zoekNummer = !anr.equals("");
			Product gezochte = null;
			
			//check welke zoekterm er in is gevoerd
			if(zoekNummer){
				//check voor geldig artikelnummer (int)
				if(!anr.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")){
					req.setAttribute("zoekmsg", "Vul een geldig artikelnummer in!");
				}
				else{
					int nummer = Integer.parseInt(anr);
					for(Product p: deVoorraad){
						if(p.getArtikelNr() == nummer){
							gezochte = p;
							break;
						}
					}
				}
			}
			else if(zoekNaam){
				for(Product p: deVoorraad){
					if(p.getNaam().equals(nm)){
						gezochte = p;
						break;
					}
				}				
			}
			else{
				req.setAttribute("zoekmsg", "Vul een zoekcriterium in!");
			}
			
			//check of het product is gevonden
			if(gezochte != null){
				req.setAttribute("productgevonden", gezochte);				
			}
			else if(zoekNaam || zoekNummer){
				req.setAttribute("zoekmsg", "Dit product staat niet op de voorraadlijst.");	
			}
			RequestDispatcher rd = req.getRequestDispatcher("voorraad.jsp");
			rd.forward(req, resp);
		}
		
		//wijzig gezochte product
		else if(knop.equals("wijzig")){
			String hetProduct = req.getParameter("product");
			Product p = null;
			for(Product zoek : deVoorraad){
				if(zoek.getArtikelNr() == Integer.parseInt(hetProduct)){
					p = zoek;
				}
			}
			req.setAttribute("product", p);
			RequestDispatcher rd = req.getRequestDispatcher("wijzigproduct.jsp");
			rd.forward(req, resp);
		}
	}
}